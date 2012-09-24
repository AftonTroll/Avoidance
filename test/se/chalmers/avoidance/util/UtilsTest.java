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

import static org.junit.Assert.assertTrue;

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

}
