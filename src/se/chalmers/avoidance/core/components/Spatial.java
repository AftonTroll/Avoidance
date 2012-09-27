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

import org.andengine.entity.sprite.Sprite;

import com.artemis.Component;
/**
 * Component containing information about name and sprite.
 * 
 * @author Markus Ekström
 */
public class Spatial extends Component {
	private String name;
	private Sprite sprite;
	
	/**
	 * Constructs a <code>Spatial</code> component with a name as
	 * specified in the parameter.
	 *  
	 * @param name The name.
	 */
	public Spatial(String name) {
		setName(name);
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
	 * Sets the sprite.
	 * @param sprite The sprite.
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Checks if the sprite is set.
	 * @return True if the sprite is set.
	 */
	public boolean hasSprite() {
		return sprite != null;
	}
	
	/**
	 * Returns the sprite if it has been set, null otherwise.
	 * @return The spatial's sprite if it's been set.
	 */
	public Sprite getSprite() {
		return sprite;
	}
	
}
