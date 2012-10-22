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
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import org.andengine.engine.Engine;

import se.chalmers.avoidance.constants.EventMessageConstants;

/**
 * A manager for the game's states.
 * 
 * @author Markus Ekström, modified by Florian Minges
 */
public class StateManager implements PropertyChangeListener {
	private Engine engine;
	private Map<StateID, IState> stateMap = new HashMap<StateID, IState>();
	private IState currentState;
	private PropertyChangeSupport pcs;

	/**
	 * Constructs a <code>StateManager</code> using the <code>Engine</code>
	 * provided.
	 * @param engine The engine from AndEngine.
	 */
	public StateManager(Engine engine) {
		this.engine = engine;
		pcs = new PropertyChangeSupport(this);
	}
	
	/**
	* Adds a state.
	* @param stateID The desired ID of the new state.
	* @param state The new state.
	*/
	public void addState(StateID stateID, IState state) {
		if (stateMap.put(stateID, state) == null) {
			state.addPropertyChangeListener(this);
		}
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
	
	/**
	 * Returns the state associated with the <code>StateID</code> in the parameter.
	 * 
	 * @param stateID the states ID
	 * @return the state associated with the given <code>StateID</code>
	 */
	public IState getState(StateID stateID) {
		return stateMap.get(stateID);
	}
	
	/**
	 * Returns the <code>StateID</code> of the current active state.
	 * @return the <code>StateID</code> of the current active state
	 */
	public StateID getActiveStateID() {
		for (StateID id : stateMap.keySet()) {
			if (stateMap.get(id).equals(currentState)) {
				return id;
			}
		}
		return null;
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
	public void propertyChange(PropertyChangeEvent event) {
		if (event != null && event.getNewValue() != null) {
			if (EventMessageConstants.QUIT_GAME.equals(event.getPropertyName())) {
				pcs.firePropertyChange(event);
			} else if (EventMessageConstants.CHANGE_STATE.equals(event.getPropertyName())) {
				setState((StateID) event.getNewValue());
				if(event.getOldValue() == StateID.Game && event.getNewValue() == StateID.Highscore) {
					pcs.firePropertyChange(EventMessageConstants.RESTART_GAME, null, null);
				}
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
