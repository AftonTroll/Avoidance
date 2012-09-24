package se.chalmers.avoidance.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UtilsTest {
	
	private static float f1, f2, f3, f4, f5, f6;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		f1 = 0f;
		f2 = 2f;
		f3 = 8f;
		f4 = -2f;
		f5 = -(float)Math.PI;
		f6 = -7f;
	}

	@Test
	public void testSimplifyAngle() {
		assertTrue(Utils.simplifyAngle(f1) == f1);
		assertTrue(Utils.simplifyAngle(f2) == f2);
		assertTrue(Utils.simplifyAngle(f3) == (f3 - (float)(2 * Math.PI)));
		assertTrue(Utils.simplifyAngle(f4) == ((float)(2 * Math.PI)) + f4);
		assertTrue(Utils.simplifyAngle(f5) == (float) Math.PI);
		assertTrue(Utils.simplifyAngle(f6) == ((float)(4 * Math.PI)) + f6);
	}

	@Test
	public void testReverseAngle() {
		assertTrue(Utils.reverseAngle(f1) == (float) Math.PI);
		assertTrue(Utils.reverseAngle(f2) == (float) Math.PI + f2);
		assertTrue(Utils.reverseAngle(f3) == f3 - (float) Math.PI);
		assertTrue(Utils.reverseAngle(f4) == (float) Math.PI + f4);
		assertTrue(Utils.reverseAngle(f5) == f5 + (float) Math.PI);
		assertTrue(Utils.reverseAngle(f6) == (float) Math.PI + f6);
	}
	
	/**
	 * Returns true if the two arguments are closer than 1 / 100'000
	 * to each other.
	 * 
	 * @param f1 a float
	 * @param f2 another float
	 * @return <code>true</code> if the two arguments are close enough.
	 */
	private static boolean equals(float f1, float f2) {
		return Math.abs(f1 - f2) < (1f / 100000);
	}

}
