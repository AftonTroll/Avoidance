/* 
 * Copyright (c) 2012 Markus Ekstr�m
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
package se.chalmers.avoidance.core.collisionhandlers;

import se.chalmers.avoidance.core.components.Buff;
import se.chalmers.avoidance.core.components.Buff.BuffType;
import se.chalmers.avoidance.core.components.Velocity;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

/**
 * Handles collisions between the player and a power-up.
 * @author Markus Ekstr�m
 *
 */
public class PowerUpCollisionHandler implements CollisionHandler{
    ComponentMapper<Velocity> vm;
    ComponentMapper<Buff> bm;
    
    /**
     * Constructs a PowerUpCollisionHandler.
     * @param world The world.
     */
    public PowerUpCollisionHandler(World world) {
    	vm = ComponentMapper.getFor(Velocity.class, world);
    	bm = ComponentMapper.getFor(Buff.class, world);
    }
    
	/**
	 * Takes the player and a power-up and applies the power-up's buff to the player.
	 * @param player The player
	 * @param powerup The power-up whose buff shall be applied to the player.
	 */
	public void handleCollision(Entity player, Entity powerup) {
		Velocity velocity = vm.get(player);
		Buff buff = bm.get(powerup);
		if(buff.getType() == BuffType.Speed) {
			velocity.addSpeed(buff.getStrength());
		}
		powerup.deleteFromWorld();
	}
}