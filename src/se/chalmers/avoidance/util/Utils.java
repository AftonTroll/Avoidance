package se.chalmers.avoidance.util;

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

}
