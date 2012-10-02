package se.chalmers.avoidance.states;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import org.andengine.engine.Engine;

public class StateManager implements PropertyChangeListener {
	private Engine engine;
	private Map<StateID, IState> stateMap = new HashMap<StateID, IState>();
	private IState currentState;
	private PropertyChangeSupport pcs;

	public StateManager(Engine engine) {
		this.engine = engine;
		this.pcs = new PropertyChangeSupport(this);
	}

	/**
	* Adds a state.
	* @param stateID The desired ID of the new state.
	* @param state The new state.
	*/
	public void addState(StateID stateID, IState state) {
		if (stateMap.put(stateID, state) == null)
			state.addPropertyChangeListener(this);
	}

	/**
	* Removes a state specified by the ID.
	* @param stateID The ID of the state to be removed.
	*/
	public void removeState(StateID stateID) {
		IState state = stateMap.remove(stateID);
		if (state != null)
			state.removePropertyChangeListener(this);
	}

	/**
	* Sets the current state to the passed state.
	* @param stateID The ID of the state to be set to current.
	*/
	public void setState(StateID stateID) {
		if (stateMap.get(stateID) != null) {
			currentState = stateMap.get(stateID);
			engine.setScene(currentState.getScene());
		}
	}


	
	public void update(float tpf) {
		currentState.update(tpf);
	}

	/**
	 * Reacts if there is to be a state change.
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (event != null && event.getNewValue() != null) {
			if ("SYSTEM.EXIT".equals(event.getPropertyName())) {
				pcs.firePropertyChange(event);
			} else if ("CHANGE_STATE".equals(event.getPropertyName())) {
				setState((StateID) event.getNewValue());
			}
		}
	}
	
	/**
	 * Adds a listener to this state manager.
	 * @param pcl the listener to add
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}

	/**
	 * Removes a listener from this state manager.
	 * @param pcl the listener to remove
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
	}
}
