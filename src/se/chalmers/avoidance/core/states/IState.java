package se.chalmers.avoidance.core.states;

import org.andengine.entity.scene.Scene;
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
	
}
