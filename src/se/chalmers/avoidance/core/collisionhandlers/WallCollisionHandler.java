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

import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;
import se.chalmers.avoidance.util.Utils;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

/**
 * Handles collisions between the player and a wall.
 * 
 * @author Jakob Svensson
 * @author Markus Ekström
 */
public class WallCollisionHandler implements CollisionHandler{
	ComponentMapper<Velocity> velocityMapper;
	ComponentMapper<Transform> transformMapper;
	ComponentMapper<Size> sizeMapper;
	
	/**
	 * Constructs a WallCollisionHandler
	 * @param world The world.
	 */
	public WallCollisionHandler(World world) {
		velocityMapper = ComponentMapper.getFor(Velocity.class, world);
		transformMapper = ComponentMapper.getFor(Transform.class, world);
		sizeMapper = ComponentMapper.getFor(Size.class, world);
		
	}
	
	public void handleCollision(Entity movingEntity, Entity wall){
		Size wallSize = sizeMapper.get(wall);
		Transform wallTransform = transformMapper.get(wall);
		Velocity playerVelocity = velocityMapper.get(movingEntity);
		Size playerSize = sizeMapper.get(movingEntity);
		Transform playerTransform = transformMapper.get(movingEntity);
		
		float wallWidth = wallSize.getWidth();
		float wallHeight = wallSize.getHeight();
		float wallX = wallTransform.getX(); 
		float wallY = wallTransform.getY();		
		float angle = playerVelocity.getAngle();
		float newAngle = angle;
		
		//Check if player collides with horizontal side or vertical side
		if(playerTransform.getX()+playerSize.getWidth()/2>wallX&&playerTransform.getX()+playerSize.getWidth()/2<wallX+wallWidth){
			newAngle = flipVertical(angle);
			if(angle>Math.PI){
				//Collision on lower side of the wall
				playerTransform.setY(wallY+wallHeight);
			}else{
				//Collision on upper side of the wall
				playerTransform.setY(wallY-playerSize.getHeight());
			}
			
		}else if(playerTransform.getY()+playerSize.getHeight()/2>wallY&&playerTransform.getY()+playerSize.getHeight()/2<wallY+wallHeight){
			newAngle = flipHorizontal(angle);
			if(angle>Math.PI/2&&angle<(Math.PI*3)/2){
				//Collision on right side of wall
				playerTransform.setX(wallX+wallWidth);
			}else{
				//Collision on left side of wall
				playerTransform.setX(wallX-playerSize.getWidth());
			}
			
		}else{
			//Corner or almost corner collision			
			newAngle = Utils.reverseAngle(angle);
		}
		playerVelocity.setAngle(newAngle);
	
	}
	
	private float flipVertical(float angle){ 
		  float newAngle=angle*-1; 
		  return newAngle;
	}
	
	private float flipHorizontal(float angle){
		  //Translate and then flip vertical
		float newAngle = angle + (float) Math.PI/2;
		  newAngle = flipVertical(newAngle);
		  newAngle -= Math.PI/2;
		  return newAngle;
	}
}
