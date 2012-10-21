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

import org.andengine.entity.shape.RectangularShape;

/**
 * Abstract class that acts as a content provider for 
 * the screen resolution. <p>
 * 
 * The resolution can only be initialized once, unless
 * one extends this abstract class and overrides some
 * of the methods.
 * 
 * 
 * @author Florian Minges
 *
 */
public abstract class ScreenResolution {
    
	private static final int CAMERA_WIDTH = 1280;
	private static final int CAMERA_HEIGHT = 800;
	
    /**
     * Returns the screens width.<p>
     * 
     * @return the screens width
     */
    public static int getWidthResolution() {
    	return CAMERA_WIDTH; 
    }
    
    /**
     * Returns the screens height.<p>
     * 
     * @return the screens height 
     */
    public static int getHeightResolution() {
    	return CAMERA_HEIGHT; 
    }
	
	/**
	 * Returns the x-position of the <code>RectangularShape</code> provided
	 * in the argument, in case it would be centered horizontally (along the
	 * x-axis).
	 * 
	 * @param shape a shape
	 * @return the x-position of the shape if centered on the x-axis
	 */
	public static float getXPosHorizontalCentering(RectangularShape shape) {
		return (ScreenResolution.getWidthResolution() - shape.getWidth()) / 2;
	}
	
	/**
	 * Returns the y-position of the <code>RectangularShape</code> provided
	 * in the argument, in case it would be centered vertically (along the
	 * y-axis).
	 * 
	 * @param shape a shape
	 * @return the y-position of the shape if centered on the y-axis
	 */
	public static float getYPosVerticalCentering(RectangularShape shape) {
		return (ScreenResolution.getHeightResolution() - shape.getHeight()) / 2;
	}
}
