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

import org.andengine.entity.shape.RectangularShape;


/**
 * Contains a set of static helper-methods.
 * @author Florian Minges, Filip Brynfors
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
		float simplifiedAngle = angle % (float)(2 * Math.PI);
		return simplifiedAngle < 0 ? (simplifiedAngle + (float)(2 * Math.PI)) : 
														simplifiedAngle; //make angle positive
		//TODO Change to a better name?
	}
	
	/**
	 * Calculates the speed of the horizontal part of the velocity with
	 * the given angle and speed
	 * @param speed the speed of the velocity
	 * @param angle the angle of the velocity
	 * @return the horizontal part of the velocity
	 */
	public static float getHorizontalSpeed(float speed, float angle) {
		return (float) (speed * Math.cos(angle));
	}
	
	/**
	 * Calculates the speed of the vertical part of the velocity with
	 * the given angle and speed
	 * @param speed the speed of the velocity
	 * @param angle the angle of the velocity
	 * @return the vertical part of the velocity
	 */
	public static float getVerticalSpeed(float speed, float angle) {
		return (float) (speed * Math.sin(angle));
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
		float rotation = (angle < (float) Math.PI) ? 1f : -1f;
		return angle + (float)Math.PI * rotation;
	}
	
	/**
	 * Returns the x-position of the <code>RectangularShape</code> provided
	 * in the argument, in case it would be centered horizontally (along the
	 * x-axis).
	 * 
	 * @param shape a shape
	 * @return the x-position of the shape if centered on the x-axis
	 */
	public static float getXPosHorizontalCentering(RectangularShape shape) {
		return (ScreenResolution.getWidthResolution() - shape.getWidth()) / 2;
	}
	
	/**
	 * Returns the y-position of the <code>RectangularShape</code> provided
	 * in the argument, in case it would be centered vertically (along the
	 * y-axis).
	 * 
	 * @param shape a shape
	 * @return the y-position of the shape if centered on the y-axis
	 */
	public static float getYPosVerticalCentering(RectangularShape shape) {
		return (ScreenResolution.getHeightResolution() - shape.getHeight()) / 2;
	}

}
