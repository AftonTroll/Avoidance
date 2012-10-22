/* 
 * Copyright (c) 2012 Markus Ekström
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

import org.junit.BeforeClass;
import org.junit.Test;

public class ImmortalTest {
	private static int duration;

	@BeforeClass
	public static void beforeClass() {
		duration = 2;
	}

	@Test
	public void testImmortal() {
		Immortal immortal = new Immortal();
		assertTrue(immortal != null);

		assertTrue(immortal.getDurationLeft() == 0);
		immortal.setDuration(duration);
		immortal.setImmortal(true);
		assertTrue(immortal.getDurationLeft() == duration);
		immortal.subtractImmortalDurationLeft(1);
		assertTrue(immortal.getDurationLeft() == duration - 1);
	}
}
