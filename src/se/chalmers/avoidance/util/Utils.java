package se.chalmers.avoidance.util;

import se.chalmers.avoidance.components.Velocity;

public class Utils {
	
	/**
	 * Simplifies the angle so that it fits in the interval
	 * 0 <= angle < (2 * PI)
	 * @param angle The angle you want to simplify.
	 * @return The simplified angle.
	 */
	public static float simplifyAngle(float angle) {
		return angle % (float)(2.0 * Math.PI);
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

}
