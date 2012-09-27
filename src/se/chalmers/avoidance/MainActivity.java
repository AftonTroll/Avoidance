package se.chalmers.avoidance;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import se.chalmers.avoidance.states.GameState;
import se.chalmers.avoidance.states.MenuState;
import se.chalmers.avoidance.states.StateID;
import se.chalmers.avoidance.states.StateManager;
import android.hardware.SensorManager;
import android.util.Log;

public class MainActivity extends BaseGameActivity implements PropertyChangeListener {

    private final int CAMERA_WIDTH = 720;
    private final int CAMERA_HEIGHT = 480;
   
    private Camera camera;
    private Scene splashScene;
    private StateManager stateManager;
    private BitmapTextureAtlas splashAtlas;
    private TextureRegion splashImage;

	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
        return engineOptions;

	}
	
	public void onCreateResources(OnCreateResourcesCallback onCreateResourcesCallback)
			throws Exception {
        // Set the asset path of the images
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        this.splashAtlas = new BitmapTextureAtlas(
                mEngine.getTextureManager(), 512, 256,
                TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.splashImage = BitmapTextureAtlasTextureRegionFactory.createFromAsset( 
        		this.splashAtlas, this, "splash.png", 0, 0);
//        this.splashAtlas.load();
        mEngine.getTextureManager().loadTexture(splashAtlas);
		onCreateResourcesCallback.onCreateResourcesFinished();
	}
	
	public void onCreateScene(OnCreateSceneCallback onCreateSceneCallback) throws Exception {
		initSplashScene();
		onCreateSceneCallback.onCreateSceneFinished(this.splashScene);
		
	}
	public void onPopulateScene(Scene scene, OnPopulateSceneCallback onPopulateSceneCallback)
			throws Exception {
		createStates();
		stateManager.loadResources(this);
		stateManager.initializeStates(mEngine);
        
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
	 * Creates the state manager and the first reference-points to the states.
	 */
	private void createStates() {
		stateManager = new StateManager(mEngine);
		stateManager.addPropertyChangeListener(this);
		GameState gameState = new GameState((SensorManager)this.getSystemService(SENSOR_SERVICE));
		MenuState menuState = new MenuState();
		stateManager.addState(StateID.Game, gameState);
		stateManager.addState(StateID.Menu, menuState);
		
	}

    private void initSplashScene() {
    	this.mEngine.registerUpdateHandler(new FPSLogger());

	    splashScene = new Scene();
	    splashScene.setBackground(new Background(0.0f, 0.0f, 1.0f));
	    
	    final float centerX = (CAMERA_WIDTH - this.splashImage.getWidth()) / 2;
        final float centerY = (CAMERA_HEIGHT - this.splashImage.getHeight()) / 2;

		final Sprite sprite = new Sprite(centerX, centerY, splashImage, mEngine.getVertexBufferObjectManager());
	    splashScene.attachChild(sprite);

    }
	public void propertyChange(PropertyChangeEvent event) {
		if(event != null && event.getNewValue() != null) {
			if ("SYSTEM.EXIT".equals(event.getPropertyName())) {
				this.finish();
			}
			
		}
		
	}      
}
