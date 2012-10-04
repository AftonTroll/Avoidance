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

package se.chalmers.avoidance.core;

import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Spatial;
import se.chalmers.avoidance.core.components.Time;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
/**
 * A factory for entities. 
 * 
 * All entities are created through this factory.
 * 
 * @author Markus Ekström
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
		
		player.addComponent(new Transform(200,100));
		player.addComponent(new Velocity());
		player.addComponent(new Size(64,64));
		player.addComponent(new Spatial("ball.png"));
		
		return player;
	}
	
	public static Entity createWall(World world, float width, float height, float xPos, float yPos){
		Entity wall = world.createEntity();
		world.getManager(GroupManager.class).add(wall, "WALLS");
		
		wall.addComponent(new Transform(xPos, yPos));
		wall.addComponent(new Size(width,height));
		if(width>height){
			wall.addComponent(new Spatial("wall_horisontal.png"));
		}else{
			wall.addComponent(new Spatial("wall_vertical.png"));
		}
		
		return wall;
	}
	public static Entity createObstacle(World world, float width, float height, float xPos, float yPos){
		Entity obstacle = world.createEntity();
		world.getManager(GroupManager.class).add(obstacle, "WALLS");
		
		obstacle.addComponent(new Transform(xPos, yPos));
		obstacle.addComponent(new Size(width,height));
		obstacle.addComponent(new Spatial("obstacle.png"));
		
		return obstacle;
	}
	public static Entity createScore(World world){
		Entity score = world.createEntity();
		
		score.addComponent(new Time());
		
		return score;
	}
	
}
