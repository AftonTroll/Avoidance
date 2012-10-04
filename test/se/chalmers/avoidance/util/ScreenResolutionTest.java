/* 
 * Copyright (c) 2012 Florian Minges
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

import org.junit.Test;

public class ScreenResolutionTest {
	
	/**
	 * Tests 3 (all) methods of the ScreenResolution-class.
	 * Why? Because they are linked to each other.
	 */
	@Test
	public void testAllMethods() {
		assertTrue(ScreenResolution.getWidthResolution() == 1280);
		assertTrue(ScreenResolution.getHeightResolution() == 800);
		
		//TODO Can't test yet, since we need to use android-code. Move to testproject.
//		Activity activity = new Activity();
//		ScreenResolution.fetchFromActivity(activity);
		
//		assertTrue(ScreenResolution.getWidthResolution() == 0);
//		assertTrue(ScreenResolution.getHeightResolution() == 0);
		
	}

}
