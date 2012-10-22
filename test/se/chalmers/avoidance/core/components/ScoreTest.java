/*
 * Copyright (c) 2012 Jakob Svensson
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

import org.junit.Before;
import org.junit.Test;

public class ScoreTest {

	private Score score;

	@Before
	public void setUp() {
		score = new Score();
	}

	@Test
	public void testGetScore() {
		assertTrue(score.getScore() == 0);
	}

	@Test
	public void testAddKillScore() {
		score.addKillScore(100);
		assertTrue(score.getScore() == 100);
	}

	@Test
	public void testAddPowerupScore() {
		score.addPowerupScore(200);
		assertTrue(score.getScore() == 200);
	}

	@Test
	public void testAddBothScore() {
		score.addKillScore(100);
		score.addPowerupScore(200);
		assertTrue(score.getScore() == 300);
	}

}
