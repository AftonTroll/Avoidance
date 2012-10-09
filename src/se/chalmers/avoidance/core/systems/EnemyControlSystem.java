/*
* Copyright (c) 2012 Filip Brynfors
* 
* Parts if this file are derived from an example, written by Oliver Lade, which
* can be found under the following link:
* https://bitbucket.org/piemaster/artemoids/src/5c3a11ff2bdd/src/net/piemaster/artemoids/
* 		systems/PlayerShipControlSystem.java
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
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Avoidance. If not, see <http://www.gnu.org/licenses/>.
*
*/

package se.chalmers.avoidance.core.systems;

import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;
import se.chalmers.avoidance.util.Utils;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.utils.ImmutableBag;

/**
 * System that updates the enemies velocity and position
 * 
 * @author Filip Brynfors
 *
 */
public class EnemyControlSystem extends EntitySystem{
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<Size> sizeMapper;
	private TagManager tagManager;
	private GroupManager groupManager;
	
	/**
	 * Constructs a new PlayerControlSystem.
	 */
	public EnemyControlSystem() {
		super(Aspect.getAspectForAll(Transform.class, Velocity.class, Size.class));
	}
	
	/**
	 * This method is called when the system is initialized
	 */
	@Override
	protected void initialize() {
		transformMapper = world.getMapper(Transform.class);
		velocityMapper = world.getMapper(Velocity.class);
		sizeMapper = world.getMapper(Size.class);
		tagManager = world.getManager(TagManager.class);
		groupManager = world.getManager(GroupManager.class);
	}

	/**
	 * Determines if the system should be processed or not
	 * 
	 * @return true if system should be processed, false otherwise
	 */
	@Override
	protected boolean checkProcessing() {
		return true;
	}

	/**
	 * This method is called when the enemies are to be updated.
	 * Updates the velocity and position of the enemies
	 * @param entities the bag of entities with the wanted components
	 */
	@Override
	protected void processEntities(ImmutableBag<Entity> bag) {
		float friction = 0.9f;
		
		Entity player = tagManager.getEntity("PLAYER");
		if (player != null) {
			Transform playerTransform = transformMapper.get(player);
			Size playerSize = sizeMapper.get(player);
			float playerCenterX = playerTransform.getX() + playerSize.getWidth()/2;
			float playerCenterY = playerTransform.getY() + playerSize.getHeight()/2;
			
			ImmutableBag<Entity> enemyBag = groupManager.getEntities("ENEMIES");
			for (int i = 0; i<enemyBag.size(); i++) {
				Entity enemy = enemyBag.get(i);
				Transform enemyTransform = transformMapper.get(enemy);
				Size enemySize = sizeMapper.get(enemy);
				float enemyCenterX = enemyTransform.getX() + enemySize.getWidth()/2;
				float enemyCenterY = enemyTransform.getY() + enemySize.getHeight()/2;

				float accelerationAngle = (float) Math.atan2(playerCenterY-enemyCenterY, 
						playerCenterX - enemyCenterX);
				float accelX = Utils.getHorizontalSpeed(10, accelerationAngle);
				float accelY = Utils.getVerticalSpeed(10, accelerationAngle);
				
				//Update the Velocity
				//Based on https://bitbucket.org/piemaster/artemoids/src/5c3a11ff2bdd/src/net/piemaster/artemoids/
				//  systems/PlayerShipControlSystem.java
				Velocity vel = velocityMapper.get(enemy);
				float startVelX = Utils.getHorizontalSpeed(vel.getSpeed(), vel.getAngle());
				float startVelY = Utils.getVerticalSpeed(vel.getSpeed(), vel.getAngle());
				float newVelX = startVelX;
				float newVelY = startVelY;
				
				newVelX += world.delta * accelX;
				newVelY += world.delta * accelY;	
				float newSpeed = (float) Math.sqrt(newVelX*newVelX+newVelY*newVelY);
				
				//Apply friction
				newSpeed *= Math.pow(friction, world.delta);
				
				vel.setAngle((float) Math.atan2(newVelY, newVelX));
				vel.setSpeed(newSpeed);
				
				//Update the position
				Transform trans = transformMapper.get(enemy);
				float speed = vel.getSpeed();
				float angle = vel.getAngle();
				float dx = world.delta * (startVelX + Utils.getHorizontalSpeed(speed, angle))/2;
				float dy = world.delta * (startVelY + Utils.getVerticalSpeed(speed, angle))/2;
				trans.setX(trans.getX() + dx);
				trans.setY(trans.getY() + dy);
				
			}
		}
	}
}
