package se.chalmers.avoidance.states;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.BaseGameActivity;

import android.hardware.SensorManager;

import com.artemis.World;
/**
 * The game state.
 * 
 * @author Markus Ekström
 */
public class GameState implements IState{

	private PropertyChangeSupport pcs;
	private Scene scene;
	private World world;
	
	public GameState(SensorManager sensorManager) {
		this.pcs = new PropertyChangeSupport(this);
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
	 * {@inheritDoc}
	 */
	public void initializeState(Engine engine) {
		// TODO Auto-generated method stub
		
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

	/**
	 * Load all resources.
	 */
	public void onLoadResources(BaseGameActivity activity) {
		// TODO Auto-generated method stub
		
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
		
	}

}
