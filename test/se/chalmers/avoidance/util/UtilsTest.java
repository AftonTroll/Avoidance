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

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import se.chalmers.avoidance.core.components.FloatTest;

public class UtilsTest extends FloatTest {
	
	private static float f1, f2, f3, f4, f5, f6;
	private static float s1, s2, s3, s4, s5;
	private static float a1, a2, a3, a4, a5;

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
		assertFloatEquals(Utils.simplifyAngle(f1) - f1 );
		assertFloatEquals(Utils.simplifyAngle(f2) - f2 );
		assertFloatEquals(Utils.simplifyAngle(f3) - (f3 - (float)(2 * Math.PI)));
		assertFloatEquals(Utils.simplifyAngle(f4) - ((float)(2 * Math.PI) + f4));
		assertFloatEquals(Utils.simplifyAngle(f5) - ((float) Math.PI));
		assertFloatEquals(Utils.simplifyAngle(f6) - (((float)(4 * Math.PI)) + f6));
	}

	@Test
	public void testReverseAngle() {
		assertFloatEquals(Utils.reverseAngle(f1) - (float) Math.PI);
		assertFloatEquals(Utils.reverseAngle(f2) - ((float) Math.PI + f2));
		assertFloatEquals(Utils.reverseAngle(f3) - (f3 - (float) Math.PI));
		assertFloatEquals(Utils.reverseAngle(f4) - ((float) Math.PI + f4));
		assertFloatEquals(Utils.reverseAngle(f5) - (f5 + (float) Math.PI));
		assertFloatEquals(Utils.reverseAngle(f6) - ((float) Math.PI + f6));
	}
	
	@Test
	public void testGetHorizontalSpeed(){
		assertFloatEquals(Math.abs(Utils.getHorizontalSpeed(s1, a1) - 5));
		assertFloatEquals(Math.abs(Utils.getHorizontalSpeed(s2, a2)));
		assertFloatEquals(Math.abs(Utils.getHorizontalSpeed(s3, a3)));
		assertFloatEquals(Math.abs(Utils.getHorizontalSpeed(s4, a4) + 15));
		assertFloatEquals(Math.abs(Utils.getHorizontalSpeed(s5, a5) - 2));
	}
	
	@Test
	public void testGetVerticalSpeed(){
		assertFloatEquals(Math.abs(Utils.getVerticalSpeed(s1, a1)));
		assertFloatEquals(Math.abs(Utils.getVerticalSpeed(s2, a2)));
		assertFloatEquals(Math.abs(Utils.getVerticalSpeed(s3, a3) - 10));
		assertFloatEquals(Math.abs(Utils.getVerticalSpeed(s4, a4)));
		assertFloatEquals(Math.abs(Utils.getVerticalSpeed(s5, a5) -2 ));
	}
	
	@Test
	public void testTrimList() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 30; i++) {
			list.add(i);
		}
		
		//decrease the lists size continously
		Utils.trimList(list, 45);
		assertTrue(list.size() == 30);
		
		Utils.trimList(list, 28);
		assertTrue(list.size() == 28);
		assertTrue(!list.contains(28));
		
		Utils.trimList(list, 17);
		assertTrue(list.size() == 17);
		assertTrue(list.contains(0));
		assertTrue(!list.contains(17));
		
		Utils.trimList(list, 4);
		assertTrue(list.size() == 4);
		assertTrue(!list.contains(4));
		assertTrue(list.contains(3));
		
		//can't increase the size, only decrease
		Utils.trimList(list, 6);
		assertTrue(list.size() == 4);
		assertTrue(!list.contains(4));
		assertTrue(list.contains(3));
	}
	
}
