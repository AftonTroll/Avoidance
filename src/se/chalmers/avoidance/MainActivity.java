package se.chalmers.avoidance;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.BaseGameActivity;

import se.chalmers.avoidance.states.GameState;
import se.chalmers.avoidance.states.State;
import se.chalmers.avoidance.states.StateManager;

import com.artemis.World;

public class MainActivity extends BaseGameActivity {

    private final int CAMERA_WIDTH = 720;
    private final int CAMERA_HEIGHT = 480;
   
    private Camera camera;
    private Scene splashScene;
    private Scene gameScene;
    private StateManager stateManager;
   

	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
        return engineOptions;

	}
	public void onCreateResources(OnCreateResourcesCallback onCreateResourcesCallback)
			throws Exception {
		
		onCreateResourcesCallback.onCreateResourcesFinished();
	}
	
	public void onCreateScene(OnCreateSceneCallback onCreateSceneCallback) throws Exception {
		initSplashScene();
		onCreateSceneCallback.onCreateSceneFinished(this.splashScene);
		
	}
	public void onPopulateScene(Scene scene, OnPopulateSceneCallback onPopulateSceneCallback)
			throws Exception {

		mEngine.registerUpdateHandler(new TimerHandler(3f, new ITimerCallback() 
		{
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                loadResources();
                initializeGame();         
                splashScene.detachSelf();
//                stateManager.setState(States.gameState)
                mEngine.setScene(gameScene);
            }
		}));
		
		mEngine.registerUpdateHandler(new TimerHandler(3f, new ITimerCallback() 
		{
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
//                stateManager.update();
            }
		}));
		  
		onPopulateSceneCallback.onPopulateSceneFinished();

		
	}
	
	private void loadResources() {
		
	}
	
	private void initializeGame() {
		stateManager = new StateManager();
		gameScene = new Scene();
		gameScene.setBackground(new Background(1f, 0f, 0f));
		
//		GameState gameState = new GameState();
//		stateManager.addState(gameState);
//		stateManager.setState(State.Game);
		
	}
	
	public void derp() {
		World world = new World();

//		world.setSystem(new MovementSystem());
//		world.setSystem(new RotationSystem());
//		world.setSystem(new RenderingSystem());
		
		world.initialize();
		
		while(true) {
//		    world.setDelta(MyGameTimer.getDelta());
			world.process();
		}
	}
	


    private void initSplashScene() {
	    splashScene = new Scene();
	    splashScene.setBackground(new Background(0.3f, 0.3f, 0.3f));
    }      
}
