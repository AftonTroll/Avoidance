package se.chalmers.avoidance.states;

import java.beans.PropertyChangeListener;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;
/**
 * An interface for states.
 * 
 * @author Markus Ekström
 */
public interface IState {
	
	/**
	 * Updates the state
	 * @param tpf Time since last frame.
	 */
	public void update(float tpf);
	
	/**
	 * Returns the scene connected to the state.
	 * @return The state's scene.
	 */
	public Scene getScene();
	
	public void onLoadResources(BaseGameActivity activity);
	
	/**
	 * Initialization of components that require textures to be loaded.
	 * @param engine the andengine engine
	 */
	public void initializeState(Engine engine);
	public void addPropertyChangeListener(PropertyChangeListener pcl);
	public void removePropertyChangeListener(PropertyChangeListener pcl);
}
