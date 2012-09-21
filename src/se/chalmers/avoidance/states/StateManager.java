package se.chalmers.avoidance.states;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import org.andengine.engine.Engine;

public class StateManager implements PropertyChangeListener{
	private Engine engine;
	private Map<StateID, IState> stateMap = new HashMap<StateID, IState>();
	private IState currentState;

	public StateManager(Engine engine) {
		this.engine = engine;
	}
	
	public void addState(StateID stateID, IState state) {
		stateMap.put(stateID, state);
	}
	
	public void removeState(StateID stateID) {
		stateMap.remove(stateID);
	}
	
	public void setState(StateID stateID) {
		currentState = stateMap.get(stateID);
		engine.setScene(currentState.getScene());
	}
	
	public void update(float tpf) {
		currentState.update(tpf);
	}

	public void propertyChange(PropertyChangeEvent pce) {
		
	}
}
