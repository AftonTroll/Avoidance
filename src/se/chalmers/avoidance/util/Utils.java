package se.chalmers.avoidance.util;

public class Utils {
	
	/**
	 * Simplifies the angle so that it fits in the interval
	 * 0 <= angle < (2 * PI)
	 * 
	 * @param angle The angle you want to simplify.
	 * @return The simplified angle.
	 */
	public static float simplifyAngle(float angle) {
		angle = angle % (float)(2.0 * Math.PI);
		return angle < 0 ? (angle + (2 * (float)Math.PI)) : angle; //make angle positive
		//TODO Change to a better name?
	}
	
	/**
	 * Returns an angle that points in the opposite direction. This
	 * is equal to adding/subtracting 180 degrees, or PI radians.
	 * 
	 * @param angle the angle to reverse
	 * @return the reversed angle.
	 */
	public static float reverseAngle(float angle) {
		return angle + (float)Math.PI;
	}

}
