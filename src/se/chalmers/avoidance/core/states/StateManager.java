/* 
 * Copyright (c) 2012 Markus Ekström
 * 
 * This file is part of Avoidance.
 * 
 * Avoidance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Avoidance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Avoidance.  If not, see <http://www.gnu.org/licenses/>. 
 *  
 */
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
