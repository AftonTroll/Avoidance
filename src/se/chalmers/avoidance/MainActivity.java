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

package se.chalmers.avoidance;

import java.util.HashMap;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import se.chalmers.avoidance.core.states.GameState;
import se.chalmers.avoidance.core.states.StateID;
import se.chalmers.avoidance.core.states.StateManager;
import android.hardware.SensorManager;

/**
 * The starting point of the application.
 * 
 * Loads resources and starts the game loop.
 * 
 * @author Markus Ekström
 */
public class MainActivity extends BaseGameActivity {

    private final int CAMERA_WIDTH = 720;
    private final int CAMERA_HEIGHT = 480;
   
    private Camera camera;
    private Scene splashScene;
    private StateManager stateManager;
    private HashMap<String, TextureRegion> regions;
   
    /**
     * Sets the engine options (camera, screen rotation, ...) 
     */
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
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
		
        // Set the asset path of the images
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(
                getTextureManager(), 720, 480,
                TextureOptions.BILINEAR);
//        Create TextureRegions like this for every image:
//        regions.put("file_name.png", BitmapTextureAtlasTextureRegionFactory
//               .createFromAsset( bitmapTextureAtlas, this, "file_name.png", x_position, y_position));
        
        regions.put("ball.png", BitmapTextureAtlasTextureRegionFactory
		.createFromAsset( bitmapTextureAtlas, this, "ball.png", 0, 0));
       
        bitmapTextureAtlas.load();
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
		
        initializeGame();         
        splashScene.detachSelf();
		stateManager.setState(StateID.Game);
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
				regions, this.getVertexBufferObjectManager());
		stateManager.addState(StateID.Game, gameState);
	}

	/**
	 * Initializes the splash screen.
	 */
    private void initSplashScene() {
	    splashScene = new Scene();
	    splashScene.setBackground(new Background(0.0f, 0.0f, 0.0f));
    }      
}
