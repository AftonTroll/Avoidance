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
 * Component containing information about the sound.
 * 
 * @author Filip Brynfors
 *
 */
public class Sound extends Component {
	private String name;
	private boolean playing = false;
	
	/**
	 * Constructs a sound with the specified name.
	 * @param name the name
	 */
	public Sound(String name){
		this.name = name;
	}
	
	/**
	 * Sets the name.
	 * @param name The name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name.
	 * @return The name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets if the sound should be playing or not.
	 * @param playing true if the sound should be playing
	 */
	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
	
	/**
	 * Returns whether the sound should be playing or not.
	 * @return true if the sound should be playing, false otherwise
	 */
	public boolean isPlaying() {
		return playing;
	}
	

}
