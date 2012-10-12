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

import se.chalmers.avoidance.core.components.Buff;
import se.chalmers.avoidance.core.components.Buff.BuffType;
import se.chalmers.avoidance.core.components.Jump;
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
		world.getManager(GroupManager.class).add(player, "PLAYER");
		
		player.addComponent(new Transform(200,100));
		player.addComponent(new Velocity());
		player.addComponent(new Size(64,64));
		player.addComponent(new Spatial("ball.png"));
		player.addComponent(new Jump());
		
		return player;
	}
	
	/**
	 * Creates a wall in the WALLS group
	 * 
	 * @param world The world
	 * @param width the width of the wall
	 * @param height the height of the wall
	 * @param xPos the horizontal position of the wall
	 * @param yPos the vertical position of the wall 
	 * @return the new wall entity
	 */
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
	
	/**
	 * Creates an enemy in the ENEMY group
	 * 
	 * @param world The World
	 * @param xPos the horizontal position of the enemy
	 * @param yPos the vertical position of the enemy
	 * @return the new enemy entity
	 */
	public static Entity createEnemy(World world, float xPos, float yPos) {
		Entity enemy = world.createEntity();
		world.getManager(GroupManager.class).add(enemy, "ENEMIES");
		
		enemy.addComponent(new Transform(xPos, yPos));
		enemy.addComponent(new Velocity());
		enemy.addComponent(new Size(64,64));
		enemy.addComponent(new Spatial("enemy.png"));
		return enemy;
	}

	/**
	 * Creates a new Obstacle in the WALLS group
	 * 
	 * @param world The world
	 * @param width the width of the obstacle
	 * @param height the height of the obstacle
	 * @param xPos the horizontal position of the obstacle
	 * @param yPos the vertical position of the obstacle
	 * @return the new obstacle entity
	 */
	public static Entity createObstacle(World world, float width, float height, float xPos, float yPos){
		Entity obstacle = world.createEntity();
		world.getManager(GroupManager.class).add(obstacle, "WALLS");
		
		obstacle.addComponent(new Transform(xPos, yPos));
		obstacle.addComponent(new Size(width,height));
		obstacle.addComponent(new Spatial("obstacle.png"));
		
		return obstacle;
	}
	
	/**
	 * Creates a new score entity
	 * @param world The world
	 * @return the new score entity
	 */
	public static Entity createScore(World world){
		Entity score = world.createEntity();
		
		score.addComponent(new Time());
		
		return score;
	}
	
	public static Entity createPowerUp(World world, float xPos, float yPos, BuffType buffType, int buffStrength) {
		Entity powerUp = world.createEntity();
		world.getManager(GroupManager.class).add(powerUp, "POWERUPS");
		
		powerUp.addComponent(new Transform(xPos, yPos));
		powerUp.addComponent(new Size(64, 64));
		powerUp.addComponent(new Spatial("powerup.png"));
		powerUp.addComponent(new Buff(buffType, buffStrength));
		
		return powerUp;
		
	}
	
}
