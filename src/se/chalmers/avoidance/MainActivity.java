/* 
 * Copyright (c) 2012 Markus Ekstr�m
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

package se.chalmers.avoidance;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import se.chalmers.avoidance.constants.EventMessageConstants;
import se.chalmers.avoidance.constants.FontConstants;
import se.chalmers.avoidance.core.states.GameState;
import se.chalmers.avoidance.core.states.HighscoreState;
import se.chalmers.avoidance.core.states.MenuState;
import se.chalmers.avoidance.core.states.StateID;
import se.chalmers.avoidance.core.states.StateManager;
import se.chalmers.avoidance.util.FileUtils;
import se.chalmers.avoidance.util.ScreenResolution;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.view.MotionEvent;

/**
 * The starting point of the application.
 * 
 * Loads resources and starts the game loop.
 * 
 * @author Markus Ekstr�m
 */
public class MainActivity extends BaseGameActivity implements PropertyChangeListener {

    private Camera camera;
    private Scene splashScene;
    private StateManager stateManager;

    private HashMap<String, TextureRegion> regions;
    private HashMap<String, Font> fonts;
   
    /**
     * Sets the engine options (camera, screen rotation, ...) 
     */
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, ScreenResolution.getWidthResolution(), 
				ScreenResolution.getHeightResolution());
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
        		new FillResolutionPolicy(), camera);
        return engineOptions;

	}
	
	/**
	 * Loads resource files.
	 */
	public void onCreateResources(OnCreateResourcesCallback onCreateResourcesCallback)
			throws Exception {
		regions = new HashMap<String, TextureRegion>();
		fonts = new HashMap<String, Font>();
		
		//create fonts
		fonts.put(FontConstants.HUD_SCORE, FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 32));
		fonts.put(FontConstants.GAME_OVER_SCORE, FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR, Typeface.create(Typeface.MONOSPACE, Typeface.BOLD_ITALIC), 
				72, true, Color.WHITE));
		fonts.put(FontConstants.HIGH_SCORE, FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR, Typeface.create(Typeface.MONOSPACE, Typeface.BOLD_ITALIC), 
				48, true, Color.WHITE));

        // Set the asset path of the images
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(
                getTextureManager(), 2048, 1024,
                TextureOptions.BILINEAR);
//        Create TextureRegions like this for every image:
//        regions.put("file_name.png", BitmapTextureAtlasTextureRegionFactory
//               .createFromAsset( bitmapTextureAtlas, this, "file_name.png", x_position, y_position));
        regions.put("highscore.png", BitmapTextureAtlasTextureRegionFactory
		.createFromAsset( bitmapTextureAtlas, this, "highscore.png", 1748-445-552, 824-237-77));
        
        regions.put("gameOver.png", BitmapTextureAtlasTextureRegionFactory
		.createFromAsset( bitmapTextureAtlas, this, "gameOver.png", 1748-445, 824-237));
        
        regions.put("okButton.png", BitmapTextureAtlasTextureRegionFactory
		.createFromAsset( bitmapTextureAtlas, this, "okButton.png", 1748, 824));
        
        regions.put("ball.png", BitmapTextureAtlasTextureRegionFactory
		.createFromAsset( bitmapTextureAtlas, this, "ball.png", 0, 0));
        
        regions.put("wall_horisontal.png",  BitmapTextureAtlasTextureRegionFactory
		.createFromAsset( bitmapTextureAtlas, this, "wall_horisontal.png", 0, 68));
        
        regions.put("wall_vertical.png",  BitmapTextureAtlasTextureRegionFactory
        		.createFromAsset( bitmapTextureAtlas, this, "wall_vertical.png", 34,98));
        
        regions.put("obstacle.png",  BitmapTextureAtlasTextureRegionFactory
        		.createFromAsset( bitmapTextureAtlas, this, "obstacle.png", 64,98));

        regions.put("enemy.png",  BitmapTextureAtlasTextureRegionFactory
        		.createFromAsset( bitmapTextureAtlas, this, "enemy.png", 61,150));
        regions.put("powerup.png",  BitmapTextureAtlasTextureRegionFactory
        		.createFromAsset( bitmapTextureAtlas, this, "powerup.png", 120,200));
        regions.put("pitobstacle.png",  BitmapTextureAtlasTextureRegionFactory
        		.createFromAsset( bitmapTextureAtlas, this, "pitobstacle.png", 120,270));
        
        regions.put("killplayerobstacle.png",  BitmapTextureAtlasTextureRegionFactory
        		.createFromAsset( bitmapTextureAtlas, this, "killplayerobstacle.png", 120,340));
        regions.put("quickenemy.png", BitmapTextureAtlasTextureRegionFactory
        		.createFromAsset( bitmapTextureAtlas, this, "quickenemy.png", 130,150));
        
        regions.put("background.png", BitmapTextureAtlasTextureRegionFactory
        		.createFromAsset( bitmapTextureAtlas, this, "background.png", 200,190));
        
        
        bitmapTextureAtlas.load();
        for (Font font : fonts.values()) {
        	font.load();
        }
        
		onCreateResourcesCallback.onCreateResourcesFinished();
	}
	
	/**
	 * Creates and shows the splash screen.
	 */
	public void onCreateScene(OnCreateSceneCallback onCreateSceneCallback) throws Exception {
		initSplashScene();
		onCreateSceneCallback.onCreateSceneFinished(this.splashScene);
		
	}
	
	/**
	 * Starts the game.
	 */
	public void onPopulateScene(Scene scene, OnPopulateSceneCallback onPopulateSceneCallback)
			throws Exception {
		FileUtils.setContext(this);
        initializeGame();         
        splashScene.detachSelf();
		stateManager.setState(StateID.Menu);
		mEngine.registerUpdateHandler(new IUpdateHandler(){
			public void onUpdate(float tpf) {
				stateManager.update(tpf);
			}
	
			public void reset() {
	
			}
		});
		
		onPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	/**
	 * Initializes the states
	 */
	private void initializeGame() {
		stateManager = new StateManager(mEngine);

		GameState gameState = new GameState((SensorManager)this.getSystemService(SENSOR_SERVICE), 
				regions, fonts, this.getVertexBufferObjectManager());
		MenuState menuState = new MenuState(this);
		HighscoreState highscoreState = new HighscoreState(regions, fonts, 
				this.getVertexBufferObjectManager());
		stateManager.addState(StateID.Game, gameState);
		stateManager.addState(StateID.Menu, menuState);
		stateManager.addState(StateID.Highscore, highscoreState);
		
		stateManager.addPropertyChangeListener(this);
	}

	/**
	 * Initializes the splash screen.
	 */
    private void initSplashScene() {
	    splashScene = new Scene();
	    splashScene.setBackground(new Background(0.0f, 0.0f, 1.0f));
    }      

    /**
     * Handles events and takes the according action.
     */
	public void propertyChange(PropertyChangeEvent event) {
		if (event != null && event.getNewValue() != null) {
			if (EventMessageConstants.QUIT_GAME.equals(event.getPropertyName())) {
				this.finish();
			}
		}
	} 

}
