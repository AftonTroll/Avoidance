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

import se.chalmers.avoidance.core.components.Acceleration;
import se.chalmers.avoidance.core.components.Buff;
import se.chalmers.avoidance.core.components.Buff.BuffType;
import se.chalmers.avoidance.core.components.Friction;
import se.chalmers.avoidance.core.components.Jump;
import se.chalmers.avoidance.core.components.Score;
import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Sound;
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
	 * @param xPos the horizontal position of the player.
	 * @param yPos the vertical position of the player.
	 * @return An Entity (the player entity).
	 */
	public static Entity createPlayer(World world, float xPos, float yPos){
		Entity player = world.createEntity();
		world.getManager(TagManager.class).register("PLAYER", player);
		world.getManager(GroupManager.class).add(player, "PLAYER");
		world.getManager(GroupManager.class).add(player, "MOVINGENTITIES");
		world.getManager(GroupManager.class).add(player, "CIRCLESHAPES");
		
		player.addComponent(new Transform(xPos,yPos));
		player.addComponent(new Velocity());
		player.addComponent(new Size(64,64));
		player.addComponent(new Friction(0.7f));
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
		wall.addComponent(new Sound("bounce.ogg"));
		
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
		world.getManager(GroupManager.class).add(enemy, "MOVINGENTITIES");
		world.getManager(GroupManager.class).add(enemy, "CIRCLESHAPES");
		
		enemy.addComponent(new Transform(xPos, yPos));
		enemy.addComponent(new Velocity());
		enemy.addComponent(new Size(64,64));
		enemy.addComponent(new Friction(0.7f));
		enemy.addComponent(new Acceleration(10));
		enemy.addComponent(new Spatial("enemy.png"));
		return enemy;
	}
	
	/**
	 * Creates a quick enemy in the ENEMY group
	 * 
	 * @param world The World
	 * @param xPos the horizontal position of the enemy
	 * @param yPos the vertical position of the enemy
	 * @return the new enemy entity
	 */
	public static Entity createQuickEnemy(World world, float xPos, float yPos) {
		Entity enemy = world.createEntity();
		world.getManager(GroupManager.class).add(enemy, "ENEMIES");
		world.getManager(GroupManager.class).add(enemy, "MOVINGENTITIES");
		world.getManager(GroupManager.class).add(enemy, "CIRCLESHAPES");
		
		enemy.addComponent(new Transform(xPos, yPos));
		enemy.addComponent(new Velocity());
		enemy.addComponent(new Size(32,32));
		enemy.addComponent(new Friction(0.9f));
		enemy.addComponent(new Acceleration(10));
		enemy.addComponent(new Spatial("quickenemy.png"));
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
		obstacle.addComponent(new Sound("bounce.ogg"));
		
		return obstacle;
	}
	
	/**
	 * Creates a new score entity
	 * @param world The world
	 * @return the new score entity
	 */
	public static Entity createScore(World world){
		Entity score = world.createEntity();
		world.getManager(TagManager.class).register("SCORE", score);
		score.addComponent(new Score());
		score.addComponent(new Time());
		
		return score;
	}
	
	/**
	 * Creates a new powerup and adds it to the POWERUPS group.
	 * @param world The world
	 * @param xPos The desired x-position of the powerup.
	 * @param yPos The desired y-position of the powerup.
	 * @param buffType The type of the powerup.
	 * @param buffStrength The strength of the powerup.
	 * @return A powerup entity.
	 */
	public static Entity createPowerUp(World world, float xPos, float yPos, BuffType buffType, int buffStrength) {
		Entity powerUp = world.createEntity();
		world.getManager(GroupManager.class).add(powerUp, "POWERUPS");
		world.getManager(GroupManager.class).add(powerUp, "CIRCLESHAPES");
		
		powerUp.addComponent(new Transform(xPos, yPos));
		powerUp.addComponent(new Size(64, 64));
		powerUp.addComponent(new Spatial("powerup.png"));
		powerUp.addComponent(new Buff(buffType, buffStrength));
		
		return powerUp;
		
	}
	
	/**
	 * Creates a new pit obstacle and adds it to the PITOBSTACLES group.
	 * @param world the world.
	 * @param xPos the desired x-position of the pitobstacle.
	 * @param yPos the desired y-position of the pitobstacle.
	 * @return A pit obstacle entity
	 */
	public static Entity createPitobstacle(World world, float xPos, float yPos){
		Entity pitobstacle = world.createEntity();
		world.getManager(GroupManager.class).add(pitobstacle, "PITOBSTACLES");
		world.getManager(GroupManager.class).add(pitobstacle, "CIRCLESHAPES");
		
		pitobstacle.addComponent(new Transform(xPos, yPos));
		pitobstacle.addComponent(new Size(64,64));
		pitobstacle.addComponent(new Spatial("pitobstacle.png"));
		
		return pitobstacle;
	}
	
	/**
	 * Creates a new kill player obstacle and adds it to the KILLPLAYERPITOBSTACLES group.
	 * @param world the world.
	 * @param xPos the desired x-position of the kill player obstacle.
	 * @param yPos the desired y-position of the kill player obstacle.
	 * @return A kill player obstacle entity
	 */
	public static Entity createKillplayerbstacle(World world, float xPos, float yPos){
		Entity killplayerobstacle = world.createEntity();
		world.getManager(GroupManager.class).add(killplayerobstacle, "KILLPLAYEROBSTACLES");
		
		killplayerobstacle.addComponent(new Transform(xPos, yPos));
		killplayerobstacle.addComponent(new Size(64,64));
		killplayerobstacle.addComponent(new Spatial("killplayerobstacle.png"));
		
		return killplayerobstacle;
	}
	
}
