package se.chalmers.avoidance.core.components;

import static org.junit.Assert.assertTrue;

public abstract class FloatTest {
	public static final float TOLERANCE = 0.0001f;
	
	/**
	 * Asserts that the two floats in the parameter are "equal" in that
	 * manner that the difference between them is less than 0.0001.
	 * @param f1 a float
	 * @param f2 another float
	 * @throws AssertionError if the floats are not "equal"
	 */
	protected static void assertFloatEquals(float f1, float f2) throws AssertionError {
		assertFloatEquals(f1 - f2);
	}
	
	/**
	 * Asserts that the float in the parameter is closer than 0.0001 to 0.
	 * @param f1 a float
	 * @throws AssertionError if the float is not that close to 0.
	 */
	protected static void assertFloatEquals(float diff) throws AssertionError {
		assertTrue(Math.abs(diff) <= TOLERANCE);
	}
}
