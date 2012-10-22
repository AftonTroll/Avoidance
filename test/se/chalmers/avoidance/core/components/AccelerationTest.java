/*
 * Copyright (c) 2012 Filip Brynfors
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Avoidance. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package se.chalmers.avoidance.core.components;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class AccelerationTest {
	private static Acceleration a1, a2;

	@BeforeClass
	public static void setUpBeforeClass() {
		a1 = new Acceleration(5);
		a2 = new Acceleration(30.5f);
	}

	@Test
	public void testConstructor() {
		assertTrue(a1.getAcceleration() == 5);
		assertTrue(a2.getAcceleration() == 30.5f);
	}

	@Test
	public void testSetGetAcceleration() {
		a1.setAcceleration(15.3f);
		assertTrue(a1.getAcceleration() == 15.3f);
		a2.setAcceleration(-3);
		assertTrue(a2.getAcceleration() == -3);
	}

}
