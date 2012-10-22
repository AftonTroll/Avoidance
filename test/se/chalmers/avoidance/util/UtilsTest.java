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

import org.junit.Test;

import se.chalmers.avoidance.core.components.FloatTest;

public class UtilsTest extends FloatTest {

	private static final float F1 = 0f;
	private static final float F2 = 2f;
	private static final float F3 = 8f;
	private static final float F4 = -2f;
	private static final float F5 = -(float) Math.PI;
	private static final float F6 = -7f;

	private static final float S1 = 5f;
	private static final float A1 = 0f;
	private static final float S2 = 0f;
	private static final float A2 = 0f;
	private static final float S3 = 10f;
	private static final float A3 = (float) Math.PI / 2;
	private static final float S4 = 15f;
	private static final float A4 = (float) -Math.PI;
	private static final float S5 = (float) Math.sqrt(8);
	private static final float A5 = (float) (Math.PI / 4);

	@Test
	public void testSimplifyAngle() {
		assertFloatEquals(Utils.simplifyAngle(F1) - F1);
		assertFloatEquals(Utils.simplifyAngle(F2) - F2);
		assertFloatEquals(Utils.simplifyAngle(F3)
				- (F3 - (float) (2 * Math.PI)));
		assertFloatEquals(Utils.simplifyAngle(F4)
				- ((float) (2 * Math.PI) + F4));
		assertFloatEquals(Utils.simplifyAngle(F5) - ((float) Math.PI));
		assertFloatEquals(Utils.simplifyAngle(F6)
				- (((float) (4 * Math.PI)) + F6));
	}

	@Test
	public void testReverseAngle() {
		assertFloatEquals(Utils.reverseAngle(F1) - (float) Math.PI);
		assertFloatEquals(Utils.reverseAngle(F2) - ((float) Math.PI + F2));
		assertFloatEquals(Utils.reverseAngle(F3) - (F3 - (float) Math.PI));
		assertFloatEquals(Utils.reverseAngle(F4) - ((float) Math.PI + F4));
		assertFloatEquals(Utils.reverseAngle(F5) - (F5 + (float) Math.PI));
		assertFloatEquals(Utils.reverseAngle(F6) - ((float) Math.PI + F6));
	}

	@Test
	public void testGetHorizontalSpeed() {
		assertFloatEquals(Math.abs(Utils.getHorizontalSpeed(S1, A1) - 5));
		assertFloatEquals(Math.abs(Utils.getHorizontalSpeed(S2, A2)));
		assertFloatEquals(Math.abs(Utils.getHorizontalSpeed(S3, A3)));
		assertFloatEquals(Math.abs(Utils.getHorizontalSpeed(S4, A4) + 15));
		assertFloatEquals(Math.abs(Utils.getHorizontalSpeed(S5, A5) - 2));
	}

	@Test
	public void testGetVerticalSpeed() {
		assertFloatEquals(Math.abs(Utils.getVerticalSpeed(S1, A1)));
		assertFloatEquals(Math.abs(Utils.getVerticalSpeed(S2, A2)));
		assertFloatEquals(Math.abs(Utils.getVerticalSpeed(S3, A3) - 10));
		assertFloatEquals(Math.abs(Utils.getVerticalSpeed(S4, A4)));
		assertFloatEquals(Math.abs(Utils.getVerticalSpeed(S5, A5) - 2));
	}

	@Test
	public void testTrimList() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 30; i++) {
			list.add(i);
		}

		// decrease the lists size continously
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

		// can't increase the size, only decrease
		Utils.trimList(list, 6);
		assertTrue(list.size() == 4);
		assertTrue(!list.contains(4));
		assertTrue(list.contains(3));
	}

}
