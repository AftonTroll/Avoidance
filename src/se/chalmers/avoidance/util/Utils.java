/* 
 * Copyright (c) 2012 Florian Minges, Filip Brynfors
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

import java.util.List;

/**
 * Contains a set of static helper-methods.
 * 
 * @author Florian Minges, Filip Brynfors
 * 
 */
public final class Utils {

	private Utils() {
	}

	/**
	 * Simplifies the angle so that it fits in the interval 0 <= angle < (2 *
	 * PI).
	 * 
	 * @param angle
	 *            The angle you want to simplify.
	 * @return The simplified angle.
	 */
	public static float simplifyAngle(float angle) {
		float simplifiedAngle = angle % (float) (2 * Math.PI);
		return simplifiedAngle < 0 ? (simplifiedAngle + (float) (2 * Math.PI))
				: simplifiedAngle; // make angle positive
	}

	/**
	 * Calculates the speed of the horizontal part of the velocity with the
	 * given angle and speed.
	 * 
	 * @param speed
	 *            the speed of the velocity
	 * @param angle
	 *            the angle of the velocity
	 * @return the horizontal part of the velocity
	 */
	public static float getHorizontalSpeed(float speed, float angle) {
		return (float) (speed * Math.cos(angle));
	}

	/**
	 * Calculates the speed of the vertical part of the velocity with the given
	 * angle and speed.
	 * 
	 * @param speed
	 *            the speed of the velocity
	 * @param angle
	 *            the angle of the velocity
	 * @return the vertical part of the velocity
	 */
	public static float getVerticalSpeed(float speed, float angle) {
		return (float) (speed * Math.sin(angle));
	}

	/**
	 * Returns an angle that points in the opposite direction. This is equal to
	 * adding/subracting 180 degrees, or PI radians. If you are looking for a
	 * way to simplify your angle, see {@link #simplifyAngle(float)
	 * simplifyAngle(float)}.
	 * 
	 * @param angle
	 *            the angle to reverse
	 * @return the reversed angle.
	 */
	public static float reverseAngle(float angle) {
		float rotation = (angle < (float) Math.PI) ? 1f : -1f;
		return angle + (float) Math.PI * rotation;
	}

	/**
	 * Trims the list size of the supplied <code>List</code> to contain a
	 * maximum of <code>maxElements</code> elements, as specified by the
	 * argument.
	 * <p>
	 * 
	 * If <code>maxElements</code> is larger than the lists size, then the last
	 * elements in the list are removed.
	 * 
	 * @param list
	 *            the list to trim
	 * @param maxElements
	 *            the maximum number of allowed elements in the list
	 */
	public static void trimList(List<?> list, int maxElements) {
		int size = list.size();
		int diff = size - maxElements;
		for (int i = 1; i <= diff; i++) {
			list.remove(size - i);
		}
	}

}
