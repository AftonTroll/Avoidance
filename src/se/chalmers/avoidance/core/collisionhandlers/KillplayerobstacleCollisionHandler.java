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

import se.chalmers.avoidance.core.components.Jump;
import se.chalmers.avoidance.core.components.Score;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

public class KillplayerobstacleCollisionHandler implements CollisionHandler {
	
	private World world;
	
	/**
	 * Construct a new KillplayerobstacleCollisionHandler
	 * 
	 * @param world the world object
	 */
	public KillplayerobstacleCollisionHandler(World world){
		this.world=world;
	}
	
	/**
	 * Handles collision between player and killplayerobstacles
	 * 
	 * @param player the player
	 * @param obstacle the killplayerobstacle
	 */
	public void handleCollision(Entity player, Entity obstacle) {
		GroupManager groupManager = world.getManager(GroupManager.class);
		if (groupManager.getEntities("PLAYER").contains(player) && groupManager.getEntities("KILLPLAYEROBSTACLES").contains(obstacle)) {
			ComponentMapper<Jump> jumpMapper = world.getMapper(Jump.class);
			Jump jump = jumpMapper.get(player);
			if (jump == null || !jump.isInTheAir()) {
				GameOverNotifier.getInstance().gameOver();
			}
			
		}
	}

}
