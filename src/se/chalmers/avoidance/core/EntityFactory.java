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

import se.chalmers.avoidance.constants.FileConstants;
import se.chalmers.avoidance.constants.GameConstants;
import se.chalmers.avoidance.core.components.Acceleration;
import se.chalmers.avoidance.core.components.Buff;
import se.chalmers.avoidance.core.components.Buff.BuffType;
import se.chalmers.avoidance.core.components.Friction;
import se.chalmers.avoidance.core.components.Immortal;
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
public final class EntityFactory {

	/**
	 * Hidden constructor.
	 */
	private EntityFactory() {
	}

	/**
	 * Creates a player entity with a PLAYER tag.
	 * 
	 * @param world
	 *            The world.
	 * @param xPos
	 *            the horizontal position of the player.
	 * @param yPos
	 *            the vertical position of the player.
	 * @return An Entity (the player entity).
	 */
	public static Entity createPlayer(World world, float xPos, float yPos) {
		Entity player = world.createEntity();
		world.getManager(TagManager.class).register(GameConstants.TAG_PLAYER,
				player);
		world.getManager(GroupManager.class).add(player,
				GameConstants.GROUP_PLAYER);
		world.getManager(GroupManager.class).add(player,
				GameConstants.GROUP_MOVING_ENTITIES);
		world.getManager(GroupManager.class).add(player,
				GameConstants.GROUP_CIRCLE_SHAPES);

		player.addComponent(new Transform(xPos, yPos));
		player.addComponent(new Velocity());
		player.addComponent(new Size(64, 64));
		player.addComponent(new Friction(0.7f));
		player.addComponent(new Spatial(FileConstants.IMG_PLAYER_NORMAL));
		player.addComponent(new Jump());
		player.addComponent(new Immortal());

		return player;
	}

	/**
	 * Creates a wall and puts it in GROUP_OBSTACLE_WALLS.
	 * 
	 * @param world
	 *            The world
	 * @param width
	 *            the width of the wall
	 * @param height
	 *            the height of the wall
	 * @param xPos
	 *            the horizontal position of the wall
	 * @param yPos
	 *            the vertical position of the wall
	 * @return the new wall entity
	 */
	public static Entity createObstacleWall(World world, float width,
			float height, float xPos, float yPos) {
		Entity wall = world.createEntity();
		world.getManager(GroupManager.class).add(wall,
				GameConstants.GROUP_OBSTACLE_WALLS);

		wall.addComponent(new Transform(xPos, yPos));
		wall.addComponent(new Size(width, height));
		if (width > height) {
			wall.addComponent(new Spatial(
					FileConstants.IMG_OBSTACLE_WALL_HORIZONTAL));
		} else {
			wall.addComponent(new Spatial(
					FileConstants.IMG_OBSTACLE_WALL_VERTICAL));
		}
		wall.addComponent(new Sound(FileConstants.AUDIO_SOUND_BOUNCE));

		return wall;
	}

	/**
	 * Creates an enemy and puts it in GROUP_ENEMIES.
	 * 
	 * @param world
	 *            The World
	 * @param xPos
	 *            the horizontal position of the enemy
	 * @param yPos
	 *            the vertical position of the enemy
	 * @return the new normal enemy entity
	 */
	public static Entity createNormalEnemy(World world, float xPos, float yPos) {
		Entity normalEnemy = world.createEntity();
		world.getManager(GroupManager.class).add(normalEnemy,
				GameConstants.GROUP_ENEMIES);
		world.getManager(GroupManager.class).add(normalEnemy,
				GameConstants.GROUP_MOVING_ENTITIES);
		world.getManager(GroupManager.class).add(normalEnemy,
				GameConstants.GROUP_CIRCLE_SHAPES);

		normalEnemy.addComponent(new Transform(xPos, yPos));
		normalEnemy.addComponent(new Velocity());
		normalEnemy.addComponent(new Size(64, 64));
		normalEnemy.addComponent(new Friction(0.7f));
		normalEnemy.addComponent(new Acceleration(10));
		normalEnemy.addComponent(new Spatial(FileConstants.IMG_ENEMY_NORMAL));
		return normalEnemy;
	}

	/**
	 * Creates a quick enemy and puts it in GROUP_ENEMIES.
	 * 
	 * @param world
	 *            The World
	 * @param xPos
	 *            the horizontal position of the enemy
	 * @param yPos
	 *            the vertical position of the enemy
	 * @return the new quick enemy entity
	 */
	public static Entity createQuickEnemy(World world, float xPos, float yPos) {
		Entity quickEnemy = world.createEntity();
		world.getManager(GroupManager.class).add(quickEnemy,
				GameConstants.GROUP_ENEMIES);
		world.getManager(GroupManager.class).add(quickEnemy,
				GameConstants.GROUP_MOVING_ENTITIES);
		world.getManager(GroupManager.class).add(quickEnemy,
				GameConstants.GROUP_CIRCLE_SHAPES);

		quickEnemy.addComponent(new Transform(xPos, yPos));
		quickEnemy.addComponent(new Velocity());
		quickEnemy.addComponent(new Size(32, 32));
		quickEnemy.addComponent(new Friction(0.9f));
		quickEnemy.addComponent(new Acceleration(10));
		quickEnemy.addComponent(new Spatial(FileConstants.IMG_ENEMY_QUICK));
		return quickEnemy;
	}

	/**
	 * Creates a new Pillar obstacle and puts it in GROUP_OBSTACLE_WALLS.
	 * 
	 * @param world
	 *            The world
	 * @param width
	 *            the width of the obstacle
	 * @param height
	 *            the height of the obstacle
	 * @param xPos
	 *            the horizontal position of the obstacle
	 * @param yPos
	 *            the vertical position of the obstacle
	 * @return the new pillar entity
	 */
	public static Entity createObstaclePillar(World world, float width,
			float height, float xPos, float yPos) {
		Entity pillar = world.createEntity();
		world.getManager(GroupManager.class).add(pillar,
				GameConstants.GROUP_OBSTACLE_WALLS);

		pillar.addComponent(new Transform(xPos, yPos));
		pillar.addComponent(new Size(width, height));
		pillar.addComponent(new Spatial(FileConstants.IMG_OBSTACLE_PILLAR));
		pillar.addComponent(new Sound(FileConstants.AUDIO_SOUND_BOUNCE));

		return pillar;
	}

	/**
	 * Creates a new score entity and tags it as TAG_SCORE.
	 * 
	 * @param world
	 *            The world
	 * @return the new score entity
	 */
	public static Entity createScore(World world) {
		Entity score = world.createEntity();
		world.getManager(TagManager.class).register(GameConstants.TAG_SCORE,
				score);
		score.addComponent(new Score());
		score.addComponent(new Time());

		return score;
	}

	/**
	 * Creates a new speed-powerup and adds it to GROUP_POWERUPS.
	 * 
	 * @param world
	 *            The world
	 * @param xPos
	 *            The desired x-position of the powerup.
	 * @param yPos
	 *            The desired y-position of the powerup.
	 * @param buffStrength
	 *            The amount of speed the powerup gives.
	 * @return A speed powerup entity.
	 */
	public static Entity createPowerupSpeed(World world, float xPos,
			float yPos, int buffStrength) {
		Entity speed = world.createEntity();
		world.getManager(GroupManager.class).add(speed,
				GameConstants.GROUP_POWERUPS);
		world.getManager(GroupManager.class).add(speed,
				GameConstants.GROUP_CIRCLE_SHAPES);

		speed.addComponent(new Transform(xPos, yPos));
		speed.addComponent(new Size(64, 64));
		speed.addComponent(new Spatial(FileConstants.IMG_POWERUP_SPEED));
		speed.addComponent(new Buff(BuffType.Speed, buffStrength));

		return speed;

	}

	/**
	 * Creates a new immortal-powerup and adds it to GROUP_POWERUPS.
	 * 
	 * @param world
	 *            The world
	 * @param xPos
	 *            The desired x-position of the powerup.
	 * @param yPos
	 *            The desired y-position of the powerup.
	 * @param buffStrength
	 *            The duration of immortality the powerup gives..
	 * @return An immortality powerup entity.
	 */
	public static Entity createPowerUpImmortality(World world, float xPos,
			float yPos, int buffStrength) {
		Entity immortality = world.createEntity();
		world.getManager(GroupManager.class).add(immortality,
				GameConstants.GROUP_POWERUPS);
		world.getManager(GroupManager.class).add(immortality,
				GameConstants.GROUP_CIRCLE_SHAPES);

		immortality.addComponent(new Transform(xPos, yPos));
		immortality.addComponent(new Size(64, 64));
		immortality
				.addComponent(new Spatial(FileConstants.IMG_POWERUP_IMMORTAL));
		immortality.addComponent(new Buff(BuffType.Immortal, buffStrength));

		return immortality;

	}

	/**
	 * Creates a new pit obstacle and adds it to GROUP_OBSTACLE_PITS.
	 * 
	 * @param world
	 *            the world.
	 * @param xPos
	 *            the desired x-position of the pitobstacle.
	 * @param yPos
	 *            the desired y-position of the pitobstacle.
	 * @return A pit obstacle entity
	 */
	public static Entity createObstaclePit(World world, float xPos, float yPos) {
		Entity pit = world.createEntity();
		world.getManager(GroupManager.class).add(pit,
				GameConstants.GROUP_OBSTACLE_PITS);
		world.getManager(GroupManager.class).add(pit,
				GameConstants.GROUP_CIRCLE_SHAPES);

		pit.addComponent(new Transform(xPos, yPos));
		pit.addComponent(new Size(64, 64));
		pit.addComponent(new Spatial(FileConstants.IMG_OBSTACLE_PIT));

		return pit;
	}

	/**
	 * Creates a new kill player obstacle and adds it to GROUP_OBSTACLE_SPIKES.
	 * 
	 * @param world
	 *            the world.
	 * @param xPos
	 *            the desired x-position of the kill player obstacle.
	 * @param yPos
	 *            the desired y-position of the kill player obstacle.
	 * @return A spikes entity
	 */
	public static Entity createObstacleSpikes(World world, float xPos,
			float yPos) {
		Entity spikes = world.createEntity();
		world.getManager(GroupManager.class).add(spikes,
				GameConstants.GROUP_OBSTACLE_SPIKES);

		spikes.addComponent(new Transform(xPos, yPos));
		spikes.addComponent(new Size(64, 64));
		spikes.addComponent(new Spatial(FileConstants.IMG_OBSTACLE_SPIKES));

		return spikes;
	}

}
