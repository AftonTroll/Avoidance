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

import android.app.Activity;
import android.util.DisplayMetrics;

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
	private static boolean initialized;
	private static int CAMERA_WIDTH;
	private static int CAMERA_HEIGHT;
	
	public static void fetchFromActivity(Activity activity) {
		if (!initialized)
			initializeScreenResolution(activity);
	}
	
	/**
     * Initializes the screen resolution variables. <p>
     * The size is adjusted based on the current rotation of the display.
     * However, it may include system decor elements, and thus not represent
     * the actual raw size of the display.
     * @see DisplayMetrics
     */
    private static void initializeScreenResolution(Activity activity) {
    	//DisplayMetrics is compatible with all android versions, Display is not
    	DisplayMetrics metrics = activity.getBaseContext().getResources().getDisplayMetrics();
    	CAMERA_WIDTH = metrics.widthPixels;
    	CAMERA_HEIGHT = metrics.heightPixels;
    	initialized = true;
    }
    
    /**
     * Returns the screens width in pixels.
     * @return the screens width in pixels
     */
    public static int getWidthResolution() {
    	return 1280; //return fixed resolution for now
//    	return ScreenResolution.CAMERA_WIDTH;
    }
    
    /**
     * Returns the screens height in pixels.
     * @return the screens height in pixels.
     */
    public static int getHeightResolution() {
    	return 800; //return fixed resolution for now
//    	return ScreenResolution.CAMERA_HEIGHT;
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
