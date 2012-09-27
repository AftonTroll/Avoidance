package se.chalmers.avoidance.core.states;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import org.andengine.engine.Engine;

/**
 * A manager for the game's states.
 * 
 * @author Markus Ekström
 */
public class StateManager implements PropertyChangeListener{
	private Engine engine;
	private Map<StateID, IState> stateMap = new HashMap<StateID, IState>();
	private IState currentState;

	/**
	 * Constructs a <code>StateManager</code> using the <code>Engine</code>
	 * provided.
	 * @param engine The engine from AndEngine.
	 */
	public StateManager(Engine engine) {
		this.engine = engine;
	}
	
	/**
	 * Adds a state.
	 * @param stateID The desired ID of the new state.
	 * @param state The new state.
	 */
	public void addState(StateID stateID, IState state) {
		stateMap.put(stateID, state);
	}
	
	/**
	 * Removes a state specified by the ID.
	 * @param stateID The ID of the state to be removed.
	 */
	public void removeState(StateID stateID) {
		stateMap.remove(stateID);
	}
	
	/**
	 * Sets the current state to the passed state.
	 * @param stateID The ID of the state to be set to current.
	 */
	public void setState(StateID stateID) {
		currentState = stateMap.get(stateID);
		engine.setScene(currentState.getScene());
	}
	
	/**
	 * Updates the current state.
	 * @param tpf Time since last frame.
	 */
	public void update(float tpf) {
		currentState.update(tpf);
	}
	
	/**
	 * Reacts if there is to be a state change.
	 */
	public void propertyChange(PropertyChangeEvent pce) {
		
	}
}
