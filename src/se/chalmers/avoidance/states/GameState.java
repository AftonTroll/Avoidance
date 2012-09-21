package se.chalmers.avoidance.states;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;

import com.artemis.World;

import android.hardware.SensorManager;

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
	
	public void update(float tpf) {
		world.setDelta(tpf);
		world.process();
	}

	public Scene getScene() {
		return scene;
	}
}
