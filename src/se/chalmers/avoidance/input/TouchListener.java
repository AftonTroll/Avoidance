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
package se.chalmers.avoidance.input;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import android.view.MotionEvent;

/**
 * A listener that listens on touch events and fires
 * an event for it's followers to act on.
 * 
 * @author Markus Ekström
 */
public class TouchListener implements IOnSceneTouchListener {
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
	/**
	 * Adds a listener to the TouchListener.
	 * @param pcl A class implementing PropertyChangeListener that
	 * 				wants to recieve an event on screen touch.
	 */
    public void addListener(PropertyChangeListener pcl) {
    	pcs.addPropertyChangeListener(pcl);
    }

    /**
     * Called when the screen is touched and fires an event.
     * @param scene The scene.
     * @param event The touch event.
     */
	public boolean onSceneTouchEvent(Scene scene, TouchEvent event) {
        int eventAction = event.getAction(); 
        if(eventAction == MotionEvent.ACTION_DOWN) {}
        	pcs.firePropertyChange("touch", null, null);
        return true;
	}
}
