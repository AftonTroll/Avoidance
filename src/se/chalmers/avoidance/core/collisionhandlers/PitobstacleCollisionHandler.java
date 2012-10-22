/* 
 * Copyright (c) 2012 Jakob Svensson
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

import se.chalmers.avoidance.constants.GameConstants;
import se.chalmers.avoidance.core.components.Jump;
import se.chalmers.avoidance.core.components.Score;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

/**
 * Handles collision between player and pitobstacles.
 * 
 * @author Jakob Svensson
 * 
 */
public class PitobstacleCollisionHandler implements CollisionHandler {

	private World world;
	private ComponentMapper<Score> scoreMapper;

	/**
	 * Construct a new PitobstacleCollisionHandler.
	 * 
	 * @param world
	 */
	public PitobstacleCollisionHandler(World world) {
		this.world = world;
		scoreMapper = world.getMapper(Score.class);
	}

	/**
	 * Handles collision between moving entities and pitobstcales.
	 * 
	 * @param movingEntity
	 *            the moving entity
	 * @param obstacle
	 *            the pitobstacle
	 */
	public void handleCollision(Entity movingEntity, Entity obstacle) {
		GroupManager groupManager = world.getManager(GroupManager.class);
		if (groupManager.getEntities(GameConstants.GROUP_PLAYER).contains(
				movingEntity)
				&& groupManager.getEntities(GameConstants.GROUP_OBSTACLE_PITS)
						.contains(obstacle)) {
			// Handle collison between pitobstacle and player
			ComponentMapper<Jump> jumpMapper = world.getMapper(Jump.class);
			Jump jump = jumpMapper.get(movingEntity);
			if (jump == null || !jump.isInTheAir()) {
				GameOverNotifier.getInstance().gameOver();
			}

		}
		if (groupManager.getEntities(GameConstants.GROUP_ENEMIES).contains(
				movingEntity)
				&& groupManager.getEntities(GameConstants.GROUP_OBSTACLE_PITS)
						.contains(obstacle)) {
			world.deleteEntity(movingEntity);
			Score score = scoreMapper.get(world.getManager(TagManager.class)
					.getEntity(GameConstants.TAG_SCORE));
			score.addKillScore(Score.KILL_SCORE);
		}
	}

}
