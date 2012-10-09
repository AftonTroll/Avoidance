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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Avoidance.  If not, see <http://www.gnu.org/licenses/>. 
 *  
 */

package se.chalmers.avoidance.core.components;

import com.artemis.Component;
/**
 * Component holding the current time
 * 
 * @author Jakob Svensson
 *
 */
public class Time extends Component {
	
	private float currentTime=0;
	
	/**
	 * Returns the current time
	 * 
	 * @return the current time in seconds 
	 */
	public float getTime(){
		return currentTime;
	}
	
	/**
	 * Updates the time by adding the current time/frame
	 * 
	 * @param tpf the current time/frame
	 */
	public void updateTime(float tpf){
		currentTime+=tpf;
	}
}
