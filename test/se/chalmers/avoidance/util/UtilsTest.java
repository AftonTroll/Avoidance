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

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class UtilsTest {
	
	private static float f1, f2, f3, f4, f5, f6;
	private static float s1, s2, s3, s4, s5;
	private static float a1, a2, a3, a4, a5;
	private final float TOLERANCE = 0.0001f;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		f1 = 0f;
		f2 = 2f;
		f3 = 8f;
		f4 = -2f;
		f5 = -(float)Math.PI;
		f6 = -7f;
		
		s1 = 5f;
		a1 = 0f;
		s2 = 0f;
		a2 = 0f;
		s3 = 10f;
		a3 = (float) Math.PI/2;
		s4 = 15f;
		a4 = (float) -Math.PI;
		s5 = (float) Math.sqrt(8);
		a5 = (float) (Math.PI/4);
	}

	@Test
	public void testSimplifyAngle() {
		assertTrue(Utils.simplifyAngle(f1) - f1  <= TOLERANCE);
		assertTrue(Utils.simplifyAngle(f2) - f2  <= TOLERANCE);
		assertTrue(Utils.simplifyAngle(f3) - (f3 - (float)(2 * Math.PI)) <= TOLERANCE);
		assertTrue(Utils.simplifyAngle(f4) - ((float)(2 * Math.PI) + f4) <= TOLERANCE);
		assertTrue(Utils.simplifyAngle(f5) - ((float) Math.PI) <= TOLERANCE);
		assertTrue(Utils.simplifyAngle(f6) - (((float)(4 * Math.PI)) + f6) <= TOLERANCE);
	}

	@Test
	public void testReverseAngle() {
		assertTrue(Utils.reverseAngle(f1) - (float) Math.PI <= TOLERANCE);
		assertTrue(Utils.reverseAngle(f2) - ((float) Math.PI + f2) <= TOLERANCE);
		assertTrue(Utils.reverseAngle(f3) - (f3 - (float) Math.PI) <= TOLERANCE);
		assertTrue(Utils.reverseAngle(f4) - ((float) Math.PI + f4) <= TOLERANCE);
		assertTrue(Utils.reverseAngle(f5) - (f5 + (float) Math.PI) <= TOLERANCE);
		assertTrue(Utils.reverseAngle(f6) - ((float) Math.PI + f6) <= TOLERANCE);
	}
	
	@Test
	public void testGetHorizontalSpeed(){
		assertTrue(Math.abs(Utils.getHorizontalSpeed(s1, a1) - 5) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getHorizontalSpeed(s2, a2)) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getHorizontalSpeed(s3, a3)) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getHorizontalSpeed(s4, a4) + 15) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getHorizontalSpeed(s5, a5) - 2) <= TOLERANCE);
	}
	
	@Test
	public void testGetVerticalSpeed(){
		assertTrue(Math.abs(Utils.getVerticalSpeed(s1, a1)) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getVerticalSpeed(s2, a2)) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getVerticalSpeed(s3, a3) - 10) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getVerticalSpeed(s4, a4)) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getVerticalSpeed(s5, a5) -2 ) <= TOLERANCE);
	}
	
}
