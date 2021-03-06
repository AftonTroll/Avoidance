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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SoundTest {
	private Sound sound;

	@Before
	public void setUp() {
		sound = new Sound("bounce");
	}

	@Test
	public void testConstructor() {
		assertTrue(sound.getName().equals("bounce"));
		assertFalse(sound.isPlaying());
	}

	@Test
	public void testName() {
		sound.setName("bang");
		assertTrue(sound.getName().equals("bang"));
	}

	@Test
	public void testPlaying() {
		sound.setPlaying(true);
		assertTrue(sound.isPlaying());
		sound.setPlaying(false);
		assertFalse(sound.isPlaying());
	}

}
