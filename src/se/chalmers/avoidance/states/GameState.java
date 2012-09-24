package se.chalmers.avoidance.states;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;

import com.artemis.World;

import android.hardware.SensorManager;
/**
 * The game state.
 * 
 * @author Markus Ekström
 */
public class GameState implements IState{

	private Scene scene;
	private World world;
	
	public GameState(SensorManager sensorManager) {
		initialize(sensorManager);
	}
	
	private void initialize(SensorManager sensorManager) {
		scene = new Scene();
		scene.setBackground(new Background(1f, 0f, 0f));
		world = new World();
		world.initialize();
		
		//Create and set systems here

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
}
