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

package se.chalmers.avoidance.core.components;

import static org.junit.Assert.assertTrue;

public abstract class FloatTest {
	public static final float TOLERANCE = 0.0001f;

	/**
	 * Asserts that the two floats in the parameter are "equal" in that manner
	 * that the difference between them is less than 0.0001.
	 * 
	 * @param f1
	 *            a float
	 * @param f2
	 *            another float
	 */
	protected static void assertFloatEquals(float f1, float f2) {
		assertFloatEquals(f1 - f2);
	}

	/**
	 * Asserts that the float in the parameter is closer than 0.0001 to 0.
	 * 
	 * @param f1
	 *            a float
	 * @throws AssertionError
	 *             if the float is not that close to 0.
	 */
	protected static void assertFloatEquals(float diff) {
		assertTrue(Math.abs(diff) <= TOLERANCE);
	}
}
