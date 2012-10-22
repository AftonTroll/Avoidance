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
package se.chalmers.avoidance.core.components;

import com.artemis.Component;

/**
 * Contains information about the status of mortality of an entity.
 * 
 * I.e. whether or not an entity is immortal.
 * 
 * @author Markus Ekström
 */
public class Immortal extends Component {
	private boolean immortal = false;
	private float immortalDurationLeft;
	private float duration = 0;

	/**
	 * Sets if the entity is immortal.
	 * 
	 * @param immortal
	 *            Decides if the entity is immortal.
	 */
	public void setImmortal(boolean immortal) {
		this.immortal = immortal;
		if (immortal) {
			immortalDurationLeft = duration;
		}
	}

	/**
	 * Sets the duration of the immortality period.
	 * 
	 * @param duration
	 *            The desired immortality period duration.
	 */
	public void setDuration(float duration) {
		this.duration = duration;
	}

	/**
	 * Queries if the entity is immortal.
	 * 
	 * @return True if it is, false if it isn't.
	 */
	public boolean isImmortal() {
		return immortal;
	}

	/**
	 * Subtracts from the duration left before the entity becomes mortal. If the
	 * passed variable will set the duration left to less than zero, the
	 * duration left will be set to zero.
	 * 
	 * @param subtractionAmount
	 *            The amount to subtract from the duration left.
	 */
	public void subtractImmortalDurationLeft(float subtractionAmount) {
		if (immortal) {
			immortalDurationLeft = Math.max(0,
					(immortalDurationLeft - subtractionAmount));
		}
	}

	/**
	 * Returns how much immortal time the entity has left.
	 * 
	 * @return The amount of immortal time the entity has left.
	 */
	public float getDurationLeft() {
		return immortalDurationLeft;
	}
}
