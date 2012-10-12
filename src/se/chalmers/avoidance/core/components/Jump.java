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
	private float jumpCooldownLeft = 0;
	
	/**
	 * Sets if the entity is in the air.
	 * @param inTheAir Decides if the entity is in the air.
	 */
	public void setInTheAir(boolean inTheAir) {
		this.inTheAir = inTheAir;
		if(inTheAir) {
			inTheAirDurationLeft = IN_THE_AIR_MAX_DURATION;
			jumpCooldownLeft = JUMP_COOLDOWN;
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
	 * Subtracts from the duration left before the entity touches the ground. If the passed
	 * variable will set the duration left to less than zero, the duration left will be set to zero.
	 * @param subtractionAmount The amount to subtract from the duration left.
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
	
	/**
	 * Sets how much is left of the jump cooldown.
	 * @param cooldownLeft How long is left of the cooldown. 
	 */
	public void setJumpCooldownLeft(float cooldownLeft) {
		this.jumpCooldownLeft = cooldownLeft;
	}
	
	/**
	 * Subtracts from the duration left before the entity is able to jump again.
	 * @param subtractionAmount The amount to subtract from the duration left.
	 */
	public void subtractJumpCooldownLeft(float subtractionAmount) {
		jumpCooldownLeft = Math.max(0, (jumpCooldownLeft - subtractionAmount));
	}
	
	/**
	 * Returns the cooldown left on jump.
	 * @return The cooldown left.
	 */
	public float getJumpCooldownLeft() {
		return jumpCooldownLeft;
	}
}
