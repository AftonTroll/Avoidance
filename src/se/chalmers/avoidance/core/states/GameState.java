/* 
 * Copyright (c) 2012 Markus Ekström, Florian Minges
 * 
 * This file is part of Avoidance.
 * 
 * Avoidance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Avoidance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Avoidance.  If not, see <http://www.gnu.org/licenses/>. 
 *  
 */

package se.chalmers.avoidance.core.states;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import se.chalmers.avoidance.constants.EventMessageConstants;
import se.chalmers.avoidance.constants.FontConstants;
import se.chalmers.avoidance.core.collisionhandlers.GameOverNotifier;
import se.chalmers.avoidance.core.systems.CollisionSystem;
import se.chalmers.avoidance.core.systems.EnemyControlSystem;
import se.chalmers.avoidance.core.systems.HudRenderSystem;
import se.chalmers.avoidance.core.systems.PlayerControlSystem;
import se.chalmers.avoidance.core.systems.SoundSystem;
import se.chalmers.avoidance.core.systems.SpatialRenderSystem;
import se.chalmers.avoidance.core.systems.SpawnSystem;
import se.chalmers.avoidance.input.AccelerometerListener;
import se.chalmers.avoidance.input.TouchListener;
import android.hardware.SensorManager;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

/**
 * The game state.
 * 
 * @author Markus Ekström, Florian Minges
 */
public class GameState implements IState, PropertyChangeListener {

	private Scene scene;
	private World world;
	private PropertyChangeSupport pcs;
	private TouchListener touchListener;
	private GameOverScene gameOverScene;
	private boolean process;
	
	/**
	 * Constructs a new <code>GameState</code>.
	 * 
	 * @param sensorManager a <code>SensorManager</code>
	 * @param regions a <code>HashMap</code> containing loaded textures/regions
	 * @param fonts a <code>HashMap</code> containing loaded fonts
	 * @param vbom the game engines <code>VertexBufferObjectManager</code>
	 */
	public GameState(SensorManager sensorManager, Map<String, TextureRegion> regions, Map<String, Font> fonts, VertexBufferObjectManager vbom) {
		this.initialize(sensorManager, regions, fonts, vbom);
		this.pcs = new PropertyChangeSupport(this);
		this.gameOverScene = new GameOverScene(vbom, regions, fonts);
		this.gameOverScene.setButtonSpriteOnClickListener(getButtonSpriteOnClickListener());
	}
	
	/**
	 * Initializes the <code>GameState</code>.
	 * 
	 * @param sensorManager a <code>SensorManager</code>
	 * @param regions a <code>HashMap</code> containing loaded textures/regions
	 * @param fonts a <code>HashMap</code> containing loaded fonts
	 * @param vbom the game engines <code>VertexBufferObjectManager</code>
	 */
	private void initialize(SensorManager sensorManager, Map<String, TextureRegion> regions, Map<String, Font> fonts, VertexBufferObjectManager vbom) {
		scene = new Scene();
		
		Sprite backgroundSprite = new Sprite(0, 0, 1280, 800, regions.get("background.png"), vbom);
		scene.setBackground(new SpriteBackground(backgroundSprite));
		world = new World();
		world.setManager(new GroupManager());
		world.setManager(new TagManager());
		
		//Create and set systems here
		world.setSystem(new SpatialRenderSystem(regions, vbom, scene));
		world.setSystem(new CollisionSystem());
		world.setSystem(new PlayerControlSystem());
		world.setSystem(new EnemyControlSystem());
		world.setSystem(new SpawnSystem());
		world.setSystem(new SoundSystem());
		world.setSystem(new HudRenderSystem(scene, vbom, fonts.get(FontConstants.HUD_SCORE)));
		
		//Initialize world.
		world.initialize();
		enableProcess(true);
		
		//Add listeners
		AccelerometerListener aL = new AccelerometerListener(sensorManager);
		aL.addPropertyChangeListener(world.getSystem(PlayerControlSystem.class));
		aL.startListening();
		
		touchListener = new TouchListener();
		scene.setOnSceneTouchListener(touchListener);
		touchListener.addListener(world.getSystem(PlayerControlSystem.class));
		
		// listen to 'Game Over'-events
		GameOverNotifier.getInstance().addPropertyChangeListener(this);
		GameOverNotifier.getInstance().setWorld(world);
	}
	
	/**
	 * Updates the state.
	 * Invoked by the StateManager, do not call manually.
	 * @param tpf Time since last frame.
	 */
	public void update(float tpf) {
		if (process) {
			if (tpf > 1.0f) {
				tpf = 0; //ie, if tpf is too large, don't update
			}
			world.setDelta(tpf);
			world.process();
		}
	}
	
	/**
	 * Enables/Disables the ability to update the world/the game. <p>
	 * @param enable true if you want the game to update;
	 * false if you want to stop the updating.
	 */
	public void enableProcess(boolean enable) {
		process = enable;
	}

	/**
	 * Returns the scene connected to the state.
	 * @return The state's scene.
	 */
	public Scene getScene() {
		return scene;
	}
	
	/**
	 * Adds a listener to this state.
	 * @param pcl the listener to add
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}

	/**
	 * Removes a listener from this state.
	 * @param pcl the listener to remove
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
	}
	
	/**
	 * Shows the game over scene.
	 * 
	 * @param score the players score
	 * @param event the <code>PropertyChangeEvent</code> that triggered this method
	 */
	public synchronized void gameOver(int score, PropertyChangeEvent event) {
		if (process) {
			enableProcess(false);
			this.gameOverScene.setScore(score);
			this.gameOverScene.addTo(scene);
			pcs.firePropertyChange(event);
		} 
	}
	
	/**
	 * Returns a <code>ButtonSprite.OnClickListener</code>, that removes this scenes
	 * child scene, and that changes the applications state to the high score state.
	 * Should be used for the game over scene.
	 * 
	 * @return a <code>ButtonSprite.OnClickListener</code> for the game over scene
	 */
	private ButtonSprite.OnClickListener getButtonSpriteOnClickListener() {
		return new ButtonSprite.OnClickListener() {
			public void onClick( ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				scene.clearChildScene();
				pcs.firePropertyChange(EventMessageConstants.CHANGE_STATE, StateID.Game, StateID.Highscore);
		    } 
		};
	}

	/**
	 * Listens to <code>PropertyChangeEvents</code>.<p>
	 * Do NOT call manually.
	 * 
	 * @param event an event
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (event != null && event.getNewValue() != null) {
			if (EventMessageConstants.GAME_OVER.equals(event.getPropertyName())) {
				int score = 0;
				try {
					score = (Integer) event.getNewValue();
				} catch (ClassCastException cce) {
					cce.printStackTrace(); //score is 0 if error occurs
				}
				gameOver(score, event);
			}
		}
	}
	
}
