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

import se.chalmers.avoidance.core.components.Buff.BuffType;

public class BuffTest {
	private static BuffType type;
	private static int strength;

	@BeforeClass
	public static void beforeClass() {
		type = Buff.BuffType.Speed;
		strength = 2;
	}

	@Test
	public void testBuff() {
		Buff buff = new Buff(type, strength);
		assertTrue(buff != null);
		assertTrue(buff.getStrength() == strength);
		assertTrue(buff.getType() == type);
	}
}
