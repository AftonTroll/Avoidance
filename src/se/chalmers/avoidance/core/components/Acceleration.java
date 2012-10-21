/* 
 * Copyright (c) 2012 Filip Brynfors
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

package se.chalmers.avoidance.core.components;

import com.artemis.Component;

/**
 * Component containing the information about the speed of the acceleration.
 * 
 * @author Filip Brynfors
 *
 */
public class Acceleration extends Component {
	private float acceleration;
	
	/**
	 * Creates a new acceleration component with the specified acceleration value.
	 * @param acceleration the acceleration
	 */
	public Acceleration(float acceleration) {
		this.acceleration = acceleration;
	}
	
	/**
	 * Returns the acceleration.
	 * @return the acceleration
	 */
	public float getAcceleration() {
		return acceleration;
	}
	
	/**
	 * Sets a new value of acceleration.
	 * @param newAcceleration the new acceleration speed
	 */
	public void setAcceleration(float newAcceleration) {
		acceleration = newAcceleration;
	}

}
