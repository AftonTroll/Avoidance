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

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import se.chalmers.avoidance.core.EntityFactory;
import se.chalmers.avoidance.core.systems.CollisionSystem;
import se.chalmers.avoidance.core.systems.HudRenderSystem;
import se.chalmers.avoidance.core.systems.PlayerControlSystem;
import se.chalmers.avoidance.core.systems.SpatialRenderSystem;
import se.chalmers.avoidance.input.AccelerometerListener;
import se.chalmers.avoidance.util.ScreenResolution;
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
	
	public GameState(SensorManager sensorManager, HashMap<String, TextureRegion> regions, VertexBufferObjectManager vbom, Font scoreFont) {
		initialize(sensorManager, regions, vbom, scoreFont);
		pcs = new PropertyChangeSupport(this);
	}
	
	private void initialize(SensorManager sensorManager, HashMap<String, TextureRegion> regions, VertexBufferObjectManager vbom, Font scoreFont ) {
		scene = new Scene();
		scene.setBackground(new Background(1f, 0f, 0f));
		world = new World();
		
		world.setManager(new GroupManager());
		world.setManager(new TagManager());
		
		
		
		//Create and set systems here
		world.setSystem(new SpatialRenderSystem(regions, vbom, scene));
		world.setSystem(new CollisionSystem());
		world.setSystem(new PlayerControlSystem());
		world.setSystem(new HudRenderSystem(scene, vbom, scoreFont));
		
		//Initialize world.
		world.initialize();
		
		//Add listeners
		AccelerometerListener aL = new AccelerometerListener(sensorManager);
		aL.addPropertyChangeListener(world.getSystem(PlayerControlSystem.class));
		aL.startListening();
		
		//Initialize entities
		world.addEntity(EntityFactory.createPlayer(world));
		world.addEntity(EntityFactory.createWall(world,1200,25,0,0));
		world.addEntity(EntityFactory.createWall(world,1200,20,0,455));
		world.addEntity(EntityFactory.createWall(world,20,800,0,0));
		world.addEntity(EntityFactory.createWall(world,20,800,700,0));
		world.addEntity(EntityFactory.createObstacle(world,50,50,200,200));
		world.addEntity(EntityFactory.createScore(world));
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
	
	protected void gameOver() {
		MenuScene childScene = new MenuScene();
		childScene.setCamera(mEngine.getCamera());
		mEngine.getScene().setChildScene(childScene);
		
		childScene.attachChild(createTransparentBackground());
		int tempScore = 1337;
		showScore(childScene, tempScore);
	}

	private Rectangle createTransparentBackground() {
		Rectangle rect = new Rectangle(0, 0, ScreenResolution.getWidthResolution(), 
				ScreenResolution.getHeightResolution(), this.getVertexBufferObjectManager());
		rect.setColor(0.1f, 0.1f, 0.1f, 0.8f);
		return rect;
	}
	
	private void showScore(final Scene scene, int score) {
		VertexBufferObjectManager vbom = this.mEngine.getVertexBufferObjectManager();
		float xPos = ScreenResolution.getWidthResolution() / 2;
		float yPos = ScreenResolution.getHeightResolution() / 2;
		
		ButtonSprite okButton = new ButtonSprite(xPos - 128, yPos, regions.get("okButton.png"), vbom);
		okButton.setOnClickListener(new ButtonSprite.OnClickListener() {
			public void onClick( ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				//do this on the ui update thread or not?
				scene.getChildScene().unregisterTouchArea(pButtonSprite);
				scene.clearChildScene();
				pcs.firePropertyChange(EventMessageConstants.CHANGE_STATE, StateID.Game, StateID.Highscore);
		    } 
		});
		
		Text scoreText = new Text(0, 0, this.gameOverFont, "Score: " + score, vbom);
		xPos -= scoreText.getWidth() / 2;
		yPos -= (scoreText.getHeight() / 2) + 128;
		scoreText.setPosition(xPos, yPos);
		scoreText.setColor(1.0f, 0.9f, 0.1f, 1.0f);
		scene.getChildScene().attachChild(scoreText); 
		scene.getChildScene().attachChild(okButton);
				
		scene.getChildScene().registerTouchArea(okButton);
		scene.getChildScene().setTouchAreaBindingOnActionDownEnabled(true);
	}
}
