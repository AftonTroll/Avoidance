package se.chalmers.avoidance;

import java.util.HashMap;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import se.chalmers.avoidance.states.GameState;
import se.chalmers.avoidance.states.StateID;
import se.chalmers.avoidance.states.StateManager;
import android.hardware.SensorManager;

public class MainActivity extends BaseGameActivity {

    private final int CAMERA_WIDTH = 720;
    private final int CAMERA_HEIGHT = 480;
   
    private Camera camera;
    private Scene splashScene;
    private StateManager stateManager;
    private HashMap<String, TextureRegion> regions;
   

	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
        return engineOptions;

	}
	public void onCreateResources(OnCreateResourcesCallback onCreateResourcesCallback)
			throws Exception {
        // Set the asset path of the images
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(
                getTextureManager(), 720, 480,
                TextureOptions.BILINEAR);
//        Create TextureRegions like this for every image:
//        regions.put("file_name.png", BitmapTextureAtlasTextureRegionFactory
//               .createFromAsset( bitmapTextureAtlas, this, "file_name.png", x_position, y_position));
       
        bitmapTextureAtlas.load();
		onCreateResourcesCallback.onCreateResourcesFinished();
	}
	
	public void onCreateScene(OnCreateSceneCallback onCreateSceneCallback) throws Exception {
		initSplashScene();
		onCreateSceneCallback.onCreateSceneFinished(this.splashScene);
		
	}
	public void onPopulateScene(Scene scene, OnPopulateSceneCallback onPopulateSceneCallback)
			throws Exception {
		
        loadResources();
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
	
	private void loadResources() {
		
	}
	
	private void initializeGame() {
		stateManager = new StateManager(mEngine);
		GameState gameState = new GameState((SensorManager)this.getSystemService(SENSOR_SERVICE), regions);
		stateManager.addState(StateID.Game, gameState);
		
	}

    private void initSplashScene() {
	    splashScene = new Scene();
	    splashScene.setBackground(new Background(0.0f, 0.0f, 0.0f));
    }      
}
