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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import se.chalmers.avoidance.core.states.GameState;
import se.chalmers.avoidance.core.states.MenuState;
import se.chalmers.avoidance.core.states.StateID;
import se.chalmers.avoidance.core.states.StateManager;
import se.chalmers.avoidance.util.ScreenResolution;

import android.graphics.Typeface;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.hardware.SensorManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The starting point of the application.
 * 
 * Loads resources and starts the game loop.
 * 
 * @author Markus Ekström
 */
public class MainActivity extends BaseGameActivity implements PropertyChangeListener {

    private Camera camera;
    private Scene splashScene;
    private StateManager stateManager;
    private Dialog gameOverDialog;

    private HashMap<String, TextureRegion> regions;
    private Font scoreFont;
   
    /**
     * Sets the engine options (camera, screen rotation, ...) 
     */
	public EngineOptions onCreateEngineOptions() {
		ScreenResolution.fetchFromActivity(this);
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
		scoreFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 20);
				
        // Set the asset path of the images
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(
                getTextureManager(), 2048, 1024,
                TextureOptions.BILINEAR);
//        Create TextureRegions like this for every image:
//        regions.put("file_name.png", BitmapTextureAtlasTextureRegionFactory
//               .createFromAsset( bitmapTextureAtlas, this, "file_name.png", x_position, y_position));
        
        regions.put("ball.png", BitmapTextureAtlasTextureRegionFactory
		.createFromAsset( bitmapTextureAtlas, this, "ball.png", 0, 0));
        
        regions.put("wall_horisontal.png",  BitmapTextureAtlasTextureRegionFactory
		.createFromAsset( bitmapTextureAtlas, this, "wall_horisontal.png", 0, 34));
        
        regions.put("wall_vertical.png",  BitmapTextureAtlasTextureRegionFactory
        		.createFromAsset( bitmapTextureAtlas, this, "wall_vertical.png", 34,64));
        
        regions.put("obstacle.png",  BitmapTextureAtlasTextureRegionFactory
        		.createFromAsset( bitmapTextureAtlas, this, "obstacle.png", 64,64));
        
        bitmapTextureAtlas.load();
        scoreFont.load();
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
				regions, this.getVertexBufferObjectManager(),scoreFont);
		MenuState menuState = new MenuState(this);
		stateManager.addState(StateID.Game, gameState);
		stateManager.addState(StateID.Menu, menuState);
		
		stateManager.addPropertyChangeListener(this);
	}

	/**
	 * Initializes the splash screen.
	 */
    private void initSplashScene() {
	    splashScene = new Scene();
	    splashScene.setBackground(new Background(0.0f, 0.0f, 1.0f));
    }      

	public void propertyChange(PropertyChangeEvent event) {
		if (event != null && event.getNewValue() != null) {
			if ("SYSTEM.EXIT".equals(event.getPropertyName())) {
				createOverlay();
				MainActivity.this.runOnUiThread(new Runnable() {
				    public void run() {
				        showDialog(1);
				    }
				});

//				this.finish();
			}
		}
	} 

	private void createOverlay() {
		Rectangle rect = new Rectangle(0, 0, 720, 480,
				this.getVertexBufferObjectManager());
		rect.setColor(0.1f, 0.1f, 0.1f, 0.8f);
		mEngine.getScene().attachChild(rect);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 1:
			if (this.gameOverDialog != null)
				removeDialog(1);
			LayoutInflater layoutInflater = LayoutInflater.from(this);
			View submitTextView = layoutInflater.inflate(R.layout.submit_dialog, null);
			final EditText nameText = (EditText) submitTextView
					.findViewById(R.id.nameField);
			nameText.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					nameText.setHint(null);
				}
			});
			
			final TextView text = (TextView) submitTextView.findViewById(R.id.text);
			text.append("7'356"); //append score here
			return setGameOverDialog(new AlertDialog.Builder(MainActivity.this)
					.setTitle("GameOver")
					.setView(submitTextView)
					.setPositiveButton("Submit",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									mEngine.getScene().getLastChild().detachSelf();
								}
							})
							.create());

		default:
			break;
		}
		return null;
	}
	
	private Dialog setGameOverDialog(Dialog dialog) {
		this.gameOverDialog = dialog;
		return dialog;
	}
}
