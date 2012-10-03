package se.chalmers.avoidance;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.BaseGameActivity;

import se.chalmers.avoidance.states.GameState;
import se.chalmers.avoidance.states.MenuState;
import se.chalmers.avoidance.states.StateID;
import se.chalmers.avoidance.states.StateManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.hardware.SensorManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends BaseGameActivity implements PropertyChangeListener {

    private final int CAMERA_WIDTH = 720;
    private final int CAMERA_HEIGHT = 480;
   
    private Camera camera;
    private Scene splashScene;
    private StateManager stateManager;
    private Dialog gameOverDialog;

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
		
        loadResources();
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
	
	private void loadResources() {
		
	}
	
	private void initializeGame() {
		stateManager = new StateManager(mEngine);
		GameState gameState = new GameState((SensorManager)this.getSystemService(SENSOR_SERVICE));
		MenuState menuState = new MenuState(this);
		stateManager.addState(StateID.Game, gameState);
		stateManager.addState(StateID.Menu, menuState);
		
		stateManager.addPropertyChangeListener(this);
	}

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
