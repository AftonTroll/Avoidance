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

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
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
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import se.chalmers.avoidance.constants.EventMessageConstants;
import se.chalmers.avoidance.constants.FontConstants;
import se.chalmers.avoidance.core.states.GameState;
import se.chalmers.avoidance.core.states.HighscoreState;
import se.chalmers.avoidance.core.states.MenuState;
import se.chalmers.avoidance.core.states.StateID;
import se.chalmers.avoidance.core.states.StateManager;
import se.chalmers.avoidance.util.AudioManager;
import se.chalmers.avoidance.util.FileUtils;
import se.chalmers.avoidance.util.ScreenResolution;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.view.KeyEvent;

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
        engineOptions.getAudioOptions().setNeedsMusic(true);
        engineOptions.getAudioOptions().setNeedsSound(true);
        return engineOptions;

	}
	
	/**
	 * Loads resource files.
	 */
	public void onCreateResources(OnCreateResourcesCallback onCreateResourcesCallback)
			throws Exception {
		//Load sound
        MusicFactory.setAssetBasePath("audio/");
        SoundFactory.setAssetBasePath("audio/");
        Music music;
        Sound sound;
        
        music = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "heroism.ogg");
        music.setLooping(true);
        AudioManager.getInstance().addMusic("heroism.ogg", music);
        
        sound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "bounce.ogg");
        AudioManager.getInstance().addSound("bounce.ogg", sound);
        
        AudioManager.getInstance().playMusic("heroism.ogg");
        
        //Load textures
		regions = new HashMap<String, TextureRegion>();
		fonts = new HashMap<String, Font>();
		
		//create fonts
		fonts.put(FontConstants.HUD_SCORE, FontFactory.create(this.getFontManager(), 
				this.getTextureManager(), 256, 256, TextureOptions.BILINEAR, 
				Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 32));
		fonts.put(FontConstants.GAME_OVER_SCORE, FontFactory.create(this.getFontManager(), 
				this.getTextureManager(), 256, 256, TextureOptions.BILINEAR, 
				Typeface.create(Typeface.MONOSPACE, Typeface.BOLD_ITALIC), 72, true, Color.WHITE));
		fonts.put(FontConstants.HIGH_SCORE, FontFactory.create(this.getFontManager(), 
				this.getTextureManager(), 256, 256, TextureOptions.BILINEAR, 
				Typeface.create(Typeface.MONOSPACE, Typeface.BOLD_ITALIC), 48, true, Color.WHITE));

        // Set the asset path of the images
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 
        		2048, 1024, TextureOptions.BILINEAR);
        BitmapTextureAtlas menuTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 
        		512, 512, TextureOptions.BILINEAR);
        BitmapTextureAtlas backgroundTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 
        		1024, 1024, TextureOptions.BILINEAR);
    	
        
//        Create TextureRegions like this for every image:
//        regions.put("file_name.png", BitmapTextureAtlasTextureRegionFactory
//               .createFromAsset( bitmapTextureAtlas, this, "file_name.png", 
//       		 x_position, y_position));
        
        regions.put("newHighscore.png", BitmapTextureAtlasTextureRegionFactory
        		.createFromAsset( bitmapTextureAtlas, this, "newHighscore.png", 200, 
        				824-237-77-200));
        
        regions.put("highscore.png", BitmapTextureAtlasTextureRegionFactory
		.createFromAsset( bitmapTextureAtlas, this, "highscore.png", 200, 824-237-77));
        
        regions.put("gameOver.png", BitmapTextureAtlasTextureRegionFactory
		.createFromAsset( bitmapTextureAtlas, this, "gameOver.png", 200, 824-237));
        
        regions.put("okButton.png", BitmapTextureAtlasTextureRegionFactory
		.createFromAsset( bitmapTextureAtlas, this, "okButton.png", 200, 824));
        
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
        		.createFromAsset( bitmapTextureAtlas, this, "background.png", 2048-1280,190));
 
        
        //add menu textures
    	regions.put("menu_start.png", BitmapTextureAtlasTextureRegionFactory.createFromAsset(
    			menuTextureAtlas, this, "menu_start.png", 0, 0));
    	regions.put("menu_highscore.png", BitmapTextureAtlasTextureRegionFactory.createFromAsset(
    			menuTextureAtlas, this, "menu_highscore.png", 0, 100));
    	regions.put("menu_credits.png", BitmapTextureAtlasTextureRegionFactory.createFromAsset(
    			menuTextureAtlas, this, "menu_credits.png", 0, 200));
    	regions.put("menu_quit.png", BitmapTextureAtlasTextureRegionFactory.createFromAsset(
    			menuTextureAtlas, this, "menu_quit.png", 0, 300));
    	
    	//add menu background
    	regions.put("background.jpg", BitmapTextureAtlasTextureRegionFactory.createFromAsset(
    			backgroundTextureAtlas, this, "background.jpg", 0, 0));
 
        bitmapTextureAtlas.load();
        menuTextureAtlas.load();
        backgroundTextureAtlas.load();
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

		VertexBufferObjectManager vbom = this.getVertexBufferObjectManager();
		GameState gameState = new GameState((SensorManager)this.getSystemService(SENSOR_SERVICE), 
				regions, fonts, vbom);
		MenuState menuState = new MenuState(mEngine.getCamera(), regions, vbom);
		HighscoreState highscoreState = new HighscoreState(regions, fonts, vbom);
		stateManager.addState(StateID.Game, gameState);
		stateManager.addState(StateID.Menu, menuState);
		stateManager.addState(StateID.Highscore, highscoreState);
		gameState.addPropertyChangeListener(highscoreState);
		
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
	
	/**
	 * Used when the activity is resumed.<p>
	 * Resumes the Audio and the application.
	 */
	@Override
	public void onResume(){
		super.onResume();
		AudioManager.getInstance().resume();
	}
	
	/**
	 * Used when the activity is paused.<p>
	 * Pauses the Audio and the application.
	 */
	@Override
	public void onPause(){
		super.onPause();
		AudioManager.getInstance().pause();
	}
	
	/**
	 * Gets called when the backbutton is pressed.<p>
	 * Either sets the state to the menu, or exits the application.
	 */
	@Override
	public void onBackPressed() {
		if (stateManager.getActiveStateID() == StateID.Menu) {
			finish();
		} else {
			stateManager.setState(StateID.Menu);
		}
	   
	}
}
