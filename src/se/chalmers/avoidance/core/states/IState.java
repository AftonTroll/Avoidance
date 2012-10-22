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

import java.beans.PropertyChangeListener;

import org.andengine.entity.scene.Scene;

/**
 * An interface for states.
 * 
 * @author Markus Ekström
 */
interface IState {
	
	/**
	 * Updates the state
	 * @param tpf Time since last frame.
	 */
	void update(float tpf);
	
	/**
	 * Returns the scene connected to the state.
	 * @return The state's scene.
	 */
	Scene getScene();
	
	/**
	 * Adds a <code>PropertyChangeListener</code> to the state.
	 * @param pcl the <code>PropertyChangeListener</code> to add
	 */
	void addPropertyChangeListener(PropertyChangeListener pcl);
	
	/**
	 * Removes a <code>PropertyChangeListener</code> from the state.
	 * @param pcl the <code>PropertyChangeListener</code> to remove
	 */
	void removePropertyChangeListener(PropertyChangeListener pcl);
	
}
