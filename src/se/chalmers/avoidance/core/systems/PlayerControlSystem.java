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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import se.chalmers.avoidance.constants.GameConstants;
import se.chalmers.avoidance.core.components.Friction;
import se.chalmers.avoidance.core.components.Immortal;
import se.chalmers.avoidance.core.components.Jump;
import se.chalmers.avoidance.core.components.Spatial;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;
import se.chalmers.avoidance.util.Utils;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.utils.ImmutableBag;

/**
 * System that handles the input of the user.
 * and updates the players velocity and position
 * 
 * @author Filip Brynfors
 *
 */
public class PlayerControlSystem extends EntitySystem implements PropertyChangeListener {
	private static final float ACCELERATION_MODIFIER = 20;
	private static final float MAX_SPEED = 400;
	private float lastAccelerationX = 0;
	private float lastAccelerationY = 0;
	private TagManager tagManager;
	@Mapper
	private ComponentMapper<Friction> frictionMapper;
	@Mapper
	private ComponentMapper<Velocity> velocityMapper;
	@Mapper
	private ComponentMapper<Transform> transformMapper;
	@Mapper
	private ComponentMapper<Jump> jumpMapper;
	@Mapper
    private ComponentMapper<Immortal> immortalMapper;
	
	/**
	 * Constructs a new PlayerControlSystem.
	 */
	public PlayerControlSystem() {
		super(Aspect.getAspectForAll(Transform.class, Velocity.class));
	}
	
	/**
	 * This method is called when the system is initialized.
	 */
	@Override
	protected void initialize() {
		tagManager = world.getManager(TagManager.class);
	}	

	/**
	 * Determines if the system should be processed or not.
	 * 
	 * @return true if system should be processed, false otherwise
	 */
	@Override
	protected boolean checkProcessing() {
		return true;
	}

	/**
	 * This method is called when the player is to be updated.
	 * Updates the velocity and position of the player
	 * @param entities the bag of entities with the wanted components
	 */
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		
		Entity entity = tagManager.getEntity(GameConstants.PLAYER_TAG);
		if (entity != null) {
			handleJump(entity); //Check if the player should be in the air.
			handleImmortal(entity);
			//Update the Velocity
			//Based on https://bitbucket.org/piemaster/artemoids/src/5c3a11ff2bdd/src/net/piemaster/artemoids/
			//  systems/PlayerShipControlSystem.java
			Velocity playerVel = velocityMapper.get(entity);
			float startVelX = Utils.getHorizontalSpeed(playerVel.getSpeed(), playerVel.getAngle());
			float startVelY = Utils.getVerticalSpeed(playerVel.getSpeed(), playerVel.getAngle());
			float newVelX = startVelX;
			float newVelY = startVelY;
			
			newVelX += world.delta * lastAccelerationX*ACCELERATION_MODIFIER;
			newVelY += world.delta * lastAccelerationY*ACCELERATION_MODIFIER;	
			float newSpeed = (float) Math.sqrt(newVelX*newVelX+newVelY*newVelY);
			
			//Apply friction
			newSpeed *= Math.pow(frictionMapper.get(entity).getFriction(), world.delta);
			
			//Adjust the speed so it's not higher than the max speed
			if(newSpeed > MAX_SPEED){
				newSpeed = MAX_SPEED;
			}
			
			if(!entity.getComponent(Jump.class).isInTheAir()) {
				playerVel.setAngle((float) Math.atan2(newVelY, newVelX));
			}
			playerVel.setSpeed(newSpeed);
			
			//Update the position
			Transform playerTransform = transformMapper.get(entity);
			playerTransform.setDirection((float) Math.atan2(lastAccelerationY, lastAccelerationX));
			float speed = playerVel.getSpeed();
			float angle = playerVel.getAngle();
			float dx = world.delta * (startVelX + Utils.getHorizontalSpeed(speed, angle))/2;
			float dy = world.delta * (startVelY + Utils.getVerticalSpeed(speed, angle))/2;
			playerTransform.setX(playerTransform.getX() + dx);
			playerTransform.setY(playerTransform.getY() + dy);
		}
	}
	
	/**
	 * Handles entity mortality.
	 * @param entity The entity entity.
	 */
	private void handleImmortal(Entity e) {
	    Immortal immortal = immortalMapper.getSafe(e);
        immortal.subtractImmortalDurationLeft(world.delta);
        if(immortal.isImmortal()) {
            immortal.subtractImmortalDurationLeft(world.delta);
            if(immortal.getDurationLeft() == 0) {
                immortal.setImmortal(false);
            }
        }
        
    }

    /**
	 * Handles entity jumping.
	 * @param e The entity.
	 */
	private void handleJump(Entity e) {
		Jump jump = jumpMapper.getSafe(e);
		jump.subtractJumpCooldownLeft(world.delta);
		if(jump.isInTheAir()) {
			jump.subtractInTheAirDurationLeft(world.delta);
			if(jump.getInTheAirDurationLeft() == 0) {
				jump.setInTheAir(false);
			}
		}
	}

	/**
	 * Sets the values of the acceleration.
	 * @param event the propertyChangeEvent containing the values of the accelerometer
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if(event==null){
			return;
		}
		if(event.getNewValue() != null){
			if("AccelerometerX".equals(event.getPropertyName())){
				lastAccelerationX = (Float) event.getNewValue();
			}
			if("AccelerometerY".equals(event.getPropertyName())){
				lastAccelerationY = (Float) event.getNewValue();
			}
		//if there is no new value
		} else {
			if("touch".equals(event.getPropertyName()) && 
					tagManager.getEntity(GameConstants.PLAYER_TAG).getComponent(Jump.class).getJumpCooldownLeft() == 0) {
				jumpMapper.get(tagManager.getEntity(GameConstants.PLAYER_TAG)).setInTheAir(true);
			}
		}
	}
}
