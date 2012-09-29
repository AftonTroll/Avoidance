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

package se.chalmers.avoidance.core;

import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Spatial;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
/**
 * A factory for entities. 
 * 
 * All entities are created through this factory.
 * 
 * @author Markus Ekstr�m
 */
public class EntityFactory {
	
	/**
	 * Creates a player entity with a PLAYER tag.
	 * 
	 * @param world The world.
	 * @return An Entity (the player entity).
	 */
	public static Entity createPlayer(World world){
		Entity player = world.createEntity();
		world.getManager(TagManager.class).register("PLAYER", player);
		
		player.addComponent(new Transform());
		player.addComponent(new Velocity());
		player.addComponent(new Size());
		player.addComponent(new Spatial("ball.png"));
		
		return player;
	}
}