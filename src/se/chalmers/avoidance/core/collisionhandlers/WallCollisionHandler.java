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
import se.chalmers.avoidance.core.components.Sound;
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
	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Size> sizeMapper;
	private ComponentMapper<Sound> soundMapper;
	
	private Transform playerTransform;
	private Size playerSize;
	
	private float wallWidth;
	private float wallHeight;
	private float wallX; 
	private float wallY;		
	private float angle;
	private float newAngle;
	
	/**
	 * Constructs a WallCollisionHandler
	 * @param world The world.
	 */
	public WallCollisionHandler(World world) {
		velocityMapper = ComponentMapper.getFor(Velocity.class, world);
		transformMapper = ComponentMapper.getFor(Transform.class, world);
		sizeMapper = ComponentMapper.getFor(Size.class, world);
		soundMapper = ComponentMapper.getFor(Sound.class, world);
		
	}
	/**
	 * Handles collision between moving entities and walls
	 * 
	 * @param movingEntity the moving entity
	 * @param wall the wall
	 */
	public void handleCollision(Entity movingEntity, Entity wall){
		Size wallSize = sizeMapper.get(wall);
		Transform wallTransform = transformMapper.get(wall);
		Velocity playerVelocity = velocityMapper.get(movingEntity);
		
		playerSize = sizeMapper.get(movingEntity);
		playerTransform = transformMapper.get(movingEntity);
		
		Sound sound = soundMapper.get(wall);
		if(sound != null){
			sound.setPlaying(true);
		}
		
		wallWidth = wallSize.getWidth();
		wallHeight = wallSize.getHeight();
		wallX = wallTransform.getX(); 
		wallY = wallTransform.getY();		
		angle = playerVelocity.getAngle();
		newAngle = angle;
		
		//Check if player collides with horizontal side or vertical side
		if(playerTransform.getX()+playerSize.getWidth()/2>wallX&&playerTransform.getX()+playerSize.getWidth()/2<wallX+wallWidth){
			handleHorisontalSideCollision();			
		}else if(playerTransform.getY()+playerSize.getHeight()/2>wallY&&playerTransform.getY()+playerSize.getHeight()/2<wallY+wallHeight){
			handleVerticalSideCollision();
		}else{
			//Corner or almost corner collision	
			handleCornerCollison();
		}
		playerVelocity.setAngle(newAngle);
	
	}
	
	private void handleHorisontalSideCollision(){
		newAngle = flipVertical(angle);
		if(angle>Math.PI){
			//Collision on lower side of the wall
			playerTransform.setY(wallY+wallHeight);
		}else{
			//Collision on upper side of the wall
			playerTransform.setY(wallY-playerSize.getHeight());
		}
	}
	
	private void handleVerticalSideCollision(){
		newAngle = flipHorizontal(angle);
		if(angle>Math.PI/2&&angle<(Math.PI*3)/2){
			//Collision on right side of wall
			playerTransform.setX(wallX+wallWidth);
		}else{
			//Collision on left side of wall
			playerTransform.setX(wallX-playerSize.getWidth());
		}
		
	}
	
	private void handleCornerCollison(){
		if(playerTransform.getX()>wallX){
			if(playerTransform.getY()>wallY){
				//Collision near lower right corner		
				playerTransform.setX(wallX+wallWidth);
				playerTransform.setY(wallY+wallHeight);
			}else{
				//Collision near upper right corner
				playerTransform.setX(wallX+wallWidth);
				playerTransform.setY(wallY-playerSize.getHeight());
			}
		}else{
			if(playerTransform.getY()>wallY){
				//Collision near lower left corner
				playerTransform.setX(wallX-playerSize.getWidth());
				playerTransform.setY(wallY+wallHeight);
			}else{
				//Collision near upper left corner
				playerTransform.setX(wallX-playerSize.getWidth());
				playerTransform.setY(wallY-playerSize.getHeight());
			}
		}
		newAngle = Utils.reverseAngle(angle);
	}
	
	private float flipVertical(float angle){ 
		  return angle*-1; 
	}
	
	private float flipHorizontal(float angle){
		  //Translate and then flip vertical
		float newAngle = angle + (float) Math.PI/2;
		  newAngle = flipVertical(newAngle);
		  newAngle -= Math.PI/2;
		  return newAngle;
	}
}
