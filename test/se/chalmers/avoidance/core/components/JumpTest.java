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

import org.junit.Before;
import org.junit.Test;

public class JumpTest {
	private Jump jump;
	
	@Before
	public void setUp() throws Exception {
		jump = new Jump();
	}
	
	@Test
	public void testStatus() {
		assertTrue(jump != null);
	}

	@Test
	public void testInTheAir() {
		assertTrue(!jump.isInTheAir());
		
		jump.setInTheAir(true);
		assertTrue(jump.isInTheAir());
		assertTrue(jump.getInTheAirDurationLeft() == 2);
		jump.subtractInTheAirDurationLeft(1);
		assertTrue(jump.getInTheAirDurationLeft() == 1);
		jump.subtractInTheAirDurationLeft(2);
		assertTrue(jump.getInTheAirDurationLeft() == 0);
		jump.setInTheAir(false);
		assertTrue(!jump.isInTheAir());
	}
	
	@Test
	public void testJump() {
		jump.setJumpCooldownLeft(Jump.JUMP_COOLDOWN);
		assertTrue(jump.getJumpCooldownLeft() == Jump.JUMP_COOLDOWN);
		jump.subtractJumpCooldownLeft(1);
		assertTrue(jump.getJumpCooldownLeft() == Math.max(0, (Jump.JUMP_COOLDOWN - 1)));
		jump.subtractJumpCooldownLeft(1000);
		assertTrue(jump.getJumpCooldownLeft() == 0);
		
	}
}
