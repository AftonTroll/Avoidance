/* 
 * Copyright (c) 2012 Markus Ekström
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


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import se.chalmers.avoidance.constants.EventMessageConstants;
import se.chalmers.avoidance.constants.FontConstants;
import se.chalmers.avoidance.core.systems.CollisionSystem;
import se.chalmers.avoidance.core.systems.EnemyControlSystem;
import se.chalmers.avoidance.core.systems.HudRenderSystem;
import se.chalmers.avoidance.core.systems.PlayerControlSystem;
import se.chalmers.avoidance.core.systems.SpatialRenderSystem;
import se.chalmers.avoidance.core.systems.SpawnSystem;
import se.chalmers.avoidance.input.AccelerometerListener;
import se.chalmers.avoidance.input.TouchListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

/**
 * The game state.
 * 
 * @author Markus Ekström
 */
public class GameState implements IState{

	private Scene scene;
	private World world;
	private PropertyChangeSupport pcs;
	private TouchListener touchListener;
	private GameOverScene gameOverScene;
	private SensorManager sensorManager;
	private Map<String, TextureRegion> regions;
	private Map<String, Font> fonts;
	private VertexBufferObjectManager vbom;
	
	
	/**
	 * Constructs a new <code>GameState</code>.
	 * 
	 * @param sensorManager a <code>SensorManager</code>
	 * @param regions a <code>HashMap</code> containing loaded textures/regions
	 * @param fonts a <code>HashMap</code> containing loaded fonts
	 * @param vbom the game engines <code>VertexBufferObjectManager</code>
	 */
	public GameState(SensorManager sensorManager, Map<String, TextureRegion> regions, Map<String, Font> fonts, VertexBufferObjectManager vbom) {
		this.sensorManager = sensorManager;
		this.regions = regions;
		this.fonts = fonts;
		this.vbom = vbom;
		this.initialize();
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
	private void initialize() {
		scene = new Scene();
		world = new World();
		scene.setBackground(new Background(1f, 0f, 0f));
		world.setManager(new GroupManager());
		world.setManager(new TagManager());
		
		
		
		//Create and set systems here
		world.setSystem(new SpatialRenderSystem(regions, vbom, scene));
		world.setSystem(new CollisionSystem());
		world.setSystem(new PlayerControlSystem());
		world.setSystem(new EnemyControlSystem());
		world.setSystem(new SpawnSystem());
		world.setSystem(new HudRenderSystem(scene, vbom, fonts.get(FontConstants.HUD_SCORE)));
		
		//Initialize world.
		world.initialize();
		
		//Add listeners
		AccelerometerListener aL = new AccelerometerListener(sensorManager);
		aL.addPropertyChangeListener(world.getSystem(PlayerControlSystem.class));
		aL.startListening();
		
		touchListener = new TouchListener();
		scene.setOnSceneTouchListener(touchListener);
		touchListener.addListener(world.getSystem(PlayerControlSystem.class));
	}
	
	/**
	 * Updates the state.
	 * Invoked by the StateManager, do not call manually.
	 * @param tpf Time since last frame.
	 */
	public void update(float tpf) {
		if(world != null) {
			world.setDelta(tpf);
			world.process();
		}
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
	 */
	public void gameOver(int score) {
		this.gameOverScene.setScore(score);
		this.gameOverScene.addTo(scene);
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
				//do this on the ui update thread or not?
				scene.clearChildScene();
				pcs.firePropertyChange(EventMessageConstants.CHANGE_STATE, StateID.Game, StateID.Highscore);
				pcs.firePropertyChange(EventMessageConstants.RESTART_GAME, null, null);
		    } 
		};
	}
	
}
