/* 
 * Copyright (c) 2012 Florian Minges
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

package se.chalmers.avoidance.util;

import se.chalmers.avoidance.components.Velocity;

/**
 * Contains a set of static helper-methods.
 * @author Florian Minges
 *
 */
public class Utils {
	
	/**
	 * Simplifies the angle so that it fits in the interval
	 * 0 <= angle < (2 * PI)
	 * 
	 * @param angle The angle you want to simplify.
	 * @return The simplified angle.
	 */
	public static float simplifyAngle(float angle) {
		angle = angle % (float)(2 * Math.PI);
		return angle < 0 ? (angle + (float)(2 * Math.PI)) : angle; //make angle positive
		//TODO Change to a better name?
	}
	
	/**
	 * Calculates the speed of the horizontal part of the given velocity
	 * @param vel the total velocity
	 * @return the horizontal part of the velocity
	 */
	public static float getHorizontalSpeed(Velocity vel) {
		return (float) (vel.getSpeed() * Math.cos(vel.getAngle()));
	}
	
	/**
	 * Calculates the speed of the vertical part of the given velocity
	 * @param vel the total velocity
	 * @return the vertical part of the velocity
	 */
	public static float getVerticalSpeed(Velocity vel) {
		return (float) (vel.getSpeed() * Math.sin(vel.getAngle()));
	}
	
	/**
	 * Returns an angle that points in the opposite direction. This
	 * is equal to adding/subracting 180 degrees, or PI radians. If
	 * you are looking for a way to simplify your angle, see 
	 * {@link #simplifyAngle(float) simplifyAngle(float)}.
	 * 
	 * @param angle the angle to reverse
	 * @return the reversed angle.
	 */
	public static float reverseAngle(float angle) {
		float reverse = (angle < (float) Math.PI) ? 1f : -1f;
		return angle + (float)Math.PI * reverse;
	}

}
