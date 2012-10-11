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
 * Contains information about the status of an entity.
 * 
 * E.g. whether or not the entity is in the air.
 * @author Markus Ekström
 */
public class Jump extends Component {
	private boolean inTheAir = false;
	public static final float IN_THE_AIR_MAX_DURATION = 2;
	private float inTheAirDurationLeft;
	public static final float JUMP_COOLDOWN = 5;
	private float jumpCooldownLeft;
	
	/**
	 * Sets if the entity is in the air.
	 * @param inTheAir Decides if the entity is in the air.
	 */
	public void setInTheAir(boolean inTheAir) {
		this.inTheAir = inTheAir;
		if(inTheAir) {
			inTheAirDurationLeft = IN_THE_AIR_MAX_DURATION;
		}
	}
	
	/**
	 * Queries if the entity is in the air.
	 * @return True if it is, false if it isn't.
	 */
	public boolean isInTheAir() {
		return inTheAir;
	}
	
	/**
	 * Sets the duration left before the entity touches the ground. If the passed
	 * variable will set the duration left to less than zero, the duration left will be set to zero.
	 * @param durationLeft How long until the entity should touch the ground.
	 */
	public void subtractInTheAirDurationLeft(float subtractionAmount) {
		if(inTheAir) {
			inTheAirDurationLeft = Math.max(0, (inTheAirDurationLeft - subtractionAmount));
		}
	}
	
	/**
	 * Returns how much air time the entity has left.
	 * @return The amount of air time the entity has left.
	 */
	public float getInTheAirDurationLeft() {
		return inTheAirDurationLeft;
	}
	
	public void setJumpCooldown(float cooldown) {
		this.jumpCooldownLeft = cooldown;
	}
	
	public void subtractJumpCooldownLeft(float subtractionAmount) {
		jumpCooldownLeft = Math.max(0, (inTheAirDurationLeft - subtractionAmount));
	}
	
	public float getJumpCooldownLeft() {
		return jumpCooldownLeft;
	}
	
}
