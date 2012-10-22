/* 
 * Copyright (c) 2012 Jakob Svensson, Markus Ekström
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
import se.chalmers.avoidance.core.components.Immortal;
import se.chalmers.avoidance.core.components.Jump;
import se.chalmers.avoidance.core.components.Score;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
/**
 * Handles collision between player and enemies.
 * 
 * @author Jakob Svensson, Markus Ekström
 *
 */
public class EnemyCollisionHandler implements CollisionHandler{

	private World world;

	/**
	 * Constructs a EnemyCollisionHandler.
	 * 
	 * @param world The world.
	 */
	public EnemyCollisionHandler (World world){
		this.world=world;
	}
	/**
	 * Takes the player and enemy and handles the collision between them.
	 * 
	 * @param player The player.
	 * @param enemy The enemy.
	 */
	public void handleCollision(Entity player, Entity enemy) {
		//Handles collison between enemy and player
		ComponentMapper<Jump> jumpMapper = world.getMapper(Jump.class);
		ComponentMapper<Immortal> immortalMapper = world.getMapper(Immortal.class);
		Jump jump = jumpMapper.get(player);
		Immortal immortal = immortalMapper.get(player);
		if(jump != null && immortal != null) {
    		if (!jump.isInTheAir() && !immortal.isImmortal()) {
    			GameOverNotifier.getInstance().gameOver();
    		} else if (immortal.isImmortal() || 
    		        (jump.isInTheAir() && jump.getInTheAirDurationLeft() <= world.delta)) {
    		    world.deleteEntity(enemy);
    		    Score score = world.getManager(TagManager.class).
    		            getEntity(GameConstants.SCORE_TAG).getComponent(Score.class);
    		    if(score != null) {
    		        score.addKillScore(Score.KILL_SCORE);
    		    }
    		}
		}
	}
}
