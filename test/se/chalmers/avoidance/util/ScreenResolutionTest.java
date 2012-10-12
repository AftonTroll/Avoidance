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

import se.chalmers.avoidance.core.components.FloatTest;

public class ScreenResolutionTest extends FloatTest {
	
	private static float [] widths = {0f, 20f, 200f, 300f, 500f, 1000f, 2000f};
	private static float [] heights = {0f, 20f, 200f, 300f, 500f, 1000f};
	
	@Test
	public void testScreenResolution() {
		assertTrue(ScreenResolution.getWidthResolution() == 1280);
		assertTrue(ScreenResolution.getHeightResolution() == 800);
	}

	/**
	 * Mock-method for getXPosHorizontalCentering(RectangularShape shape)
	 * Why? Because one needs shaders, vertexbufferobjectmanagers, texturemanagers,
	 * fonts etc to instantiate such objects.
	 * @param width the shapes width
	 */
	private float mockMethodGetXPosHorizontalCentering(float width) {
		return (ScreenResolution.getWidthResolution() - width) / 2;
	}
	
	/**
	 * Mock-method for getYPosVerticalCentering(RectangularShape shape)
	 * Why? Because one needs shaders, vertexbufferobjectmanagers, texturemanagers,
	 * fonts etc to instantiate such objects.
	 * @param height the shapes height
	 */
	private float mockMethodGetYPosVerticalCentering(float height) {
		return (ScreenResolution.getHeightResolution() - height) / 2;
	}
	
	@Test
	public void testGetXPosHorizontalCentering() {
//		return (ScreenResolution.getWidthResolution() - shape.getWidth()) / 2; <- the method
		assertFloatEquals(mockMethodGetXPosHorizontalCentering(widths[0]), 640);
		assertFloatEquals(mockMethodGetXPosHorizontalCentering(widths[1]), 630);
		assertFloatEquals(mockMethodGetXPosHorizontalCentering(widths[2]), 540);
		assertFloatEquals(mockMethodGetXPosHorizontalCentering(widths[3]), 490);
		assertFloatEquals(mockMethodGetXPosHorizontalCentering(widths[4]), 390);
		assertFloatEquals(mockMethodGetXPosHorizontalCentering(widths[5]), 140);
		assertFloatEquals(mockMethodGetXPosHorizontalCentering(widths[6]), -360);
	}
	
	@Test
	public void testGetYPosVerticalCentering() {
//		return (ScreenResolution.getHeightResolution() - shape.getHeight()) / 2; <- the method
		assertFloatEquals(mockMethodGetYPosVerticalCentering(heights[0]), 400);
		assertFloatEquals(mockMethodGetYPosVerticalCentering(heights[1]), 390);
		assertFloatEquals(mockMethodGetYPosVerticalCentering(heights[2]), 300);
		assertFloatEquals(mockMethodGetYPosVerticalCentering(heights[3]), 250);
		assertFloatEquals(mockMethodGetYPosVerticalCentering(heights[4]), 150);
		assertFloatEquals(mockMethodGetYPosVerticalCentering(heights[5]), -100);
	}
	
}
