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

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
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
import android.hardware.SensorManager;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

/**
 * The game state.
 * 
 * @author Markus Ekström
 */
public class GameState implements IState {

	private Scene scene;
	private World world;
	private PropertyChangeSupport pcs;
	private GameOverScene gameOverScene;
	
	public GameState(SensorManager sensorManager, HashMap<String, TextureRegion> regions, HashMap<String, Font> fonts, VertexBufferObjectManager vbom) {
		initialize(sensorManager, regions, fonts, vbom);
		this.pcs = new PropertyChangeSupport(this);
		this.gameOverScene = new GameOverScene(vbom, regions, fonts);
		this.gameOverScene.setButtonSpriteOnClickListener(getButtonSpriteOnClickListener());
	}
	
	private void initialize(SensorManager sensorManager, HashMap<String, TextureRegion> regions, HashMap<String, Font> fonts, VertexBufferObjectManager vbom) {
		scene = new Scene();
		scene.setBackground(new Background(1f, 0f, 0f));
		world = new World();
		
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
	}
	
	/**
	 * Updates the state.
	 * Invoked by the StateManager, do not call manually.
	 * @param tpf Time since last frame.
	 */
	public void update(float tpf) {
		world.setDelta(tpf);
		world.process();
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
	
	public void gameOver(int score) {
		this.gameOverScene.setScore(score);
		this.gameOverScene.addTo(scene);
	}
	
	private ButtonSprite.OnClickListener getButtonSpriteOnClickListener() {
		return new ButtonSprite.OnClickListener() {
			public void onClick( ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				//do this on the ui update thread or not?
				scene.clearChildScene();
				pcs.firePropertyChange(EventMessageConstants.CHANGE_STATE, StateID.Game, StateID.Highscore);
		    } 
		};
	}
	
}
