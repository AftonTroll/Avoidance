package se.chalmers.avoidance.states;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;

import android.hardware.SensorManager;

import com.artemis.World;

public class GameState implements IState{

	private Scene scene;
	private World world;
	private PropertyChangeSupport pcs;
	
	public GameState(SensorManager sensorManager) {
		initialize(sensorManager);
	}
	
	private void initialize(SensorManager sensorManager) {
		scene = new Scene();
		scene.setBackground(new Background(1f, 0f, 0f));
		world = new World();
		world.initialize();
		pcs = new PropertyChangeSupport(this);
		//Create and set systems here

	}
	
	public void update(float tpf) {
		world.setDelta(tpf);
		world.process();
	}

	public Scene getScene() {
		return scene;
	}
	
	/**
	 * Adds a listener to this state.
	 * @param pcl the listener to add
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}

	/**
	 * Removes a listener from this state.
	 * @param pcl the listener to remove
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
	}
}
