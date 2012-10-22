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

import com.artemis.Component;

/**
 * Component containing information about buffs.
 * 
 * @author Markus Ekström
 */
public class Buff extends Component{
	public static enum BuffType{Speed, Immortal}
	private int strength;
	private BuffType type;
	
	/**
	 * Constructs the Buff, giving it the type and strength specified.
	 * @param type
	 * @param amount
	 */
	public Buff(BuffType type, int strength) {
		this.type = type;
		this.strength = strength;
	}
	
	/**
	 * Returns the buff's type.
	 * @return The type of the buff.
	 */
	public BuffType getType() {
		return type;
	}
	
	/**
	 * Returns the buff's strength.
	 * @return The strength of the buff.
	 */
	public int getStrength() {
		return strength;
	}
}
