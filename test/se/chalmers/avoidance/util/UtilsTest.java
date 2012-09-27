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

import se.chalmers.avoidance.components.Velocity;

public class UtilsTest {
	
	private static float f1, f2, f3, f4, f5, f6;
	private static Velocity v1, v2, v3, v4, v5;
	private final float TOLERANCE = 0.0001f;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		f1 = 0f;
		f2 = 2f;
		f3 = 8f;
		f4 = -2f;
		f5 = -(float)Math.PI;
		f6 = -7f;
		
		v1 = new Velocity(5, 0);
		v2 = new Velocity(0, 0);
		v3 = new Velocity(10, (float) (Math.PI/2));
		v4 = new Velocity(15, (float) (-Math.PI));
		v5 = new Velocity((float) Math.sqrt(8), (float) (Math.PI/4));
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
	
	@Test
	public void testGetHorizontalSpeed(){
		assertTrue(Math.abs(Utils.getHorizontalSpeed(v1.getSpeed(), v1.getAngle()) - 5) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getHorizontalSpeed(v2.getSpeed(), v2.getAngle())) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getHorizontalSpeed(v3.getSpeed(), v3.getAngle())) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getHorizontalSpeed(v4.getSpeed(), v4.getAngle()) + 15) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getHorizontalSpeed(v5.getSpeed(), v5.getAngle()) - 2) <= TOLERANCE);
	}
	
	@Test
	public void testGetVerticalSpeed(){
		assertTrue(Math.abs(Utils.getVerticalSpeed(v1.getSpeed(), v1.getAngle())) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getVerticalSpeed(v2.getSpeed(), v2.getAngle())) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getVerticalSpeed(v3.getSpeed(), v3.getAngle()) - 10) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getVerticalSpeed(v4.getSpeed(), v4.getAngle())) <= TOLERANCE);
		assertTrue(Math.abs(Utils.getVerticalSpeed(v5.getSpeed(), v5.getAngle()) -2 ) <= TOLERANCE);
	}

}
