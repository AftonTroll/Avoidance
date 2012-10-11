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

public class StatusTest {
	private Jump status;
	
	@Before
	public void setUp() throws Exception {
		status = new Jump();
	}
	
	@Test
	public void testStatus() {
		assertTrue(status != null);
	}

	@Test
	public void testInTheAir() {
		assertTrue(!status.isInTheAir());
		
		status.setInTheAir(true);
		assertTrue(status.isInTheAir());
		assertTrue(status.getInTheAirDurationLeft() == 2);
		status.subtractInTheAirDurationLeft(1);
		assertTrue(status.getInTheAirDurationLeft() == 1);
		status.subtractInTheAirDurationLeft(2);
		assertTrue(status.getInTheAirDurationLeft() == 0);
		status.setInTheAir(false);
		assertTrue(!status.isInTheAir());
	}
}
