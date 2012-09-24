package se.chalmers.avoidance.util;

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
