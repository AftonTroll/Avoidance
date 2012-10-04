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

package se.chalmers.avoidance.core.systems;

import org.andengine.entity.shape.RectangularShape;
import org.andengine.opengl.vbo.IVertexBufferObject;

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
 * System for handling collision between entities
 * 
 * @author Jakob Svensson
 *
 */
public class CollisionSystem extends EntitySystem{
	
    private ComponentMapper<Velocity> velocityMapper;
    private ComponentMapper<Transform> transformMapper;
    private ComponentMapper<Size> sizeMapper;
	
    /**
     * Constructs a new CollisionSystem 
     * 
     * @param world the world object of the game
     */
	public CollisionSystem() {
		super(Aspect.getAspectForAll(Transform.class, Size.class));
	}
	
	/**
	 * This method is called when the system is initialized
	 */
	@Override
	protected void initialize(){
		velocityMapper = world.getMapper(Velocity.class);
		transformMapper = world.getMapper(Transform.class);
		sizeMapper = world.getMapper(Size.class);
	}
	
	/**
	 * Determines if the system should be processed or not
	 * 
	 * @return true if system should be processed, false if not	
	 */
	@Override
	protected boolean checkProcessing() {
		return true;
	}
	
	/**
	 * Processes entities and checks for collisions between them
	 * 
	 * @param ImmutableBag<Entity> the entities this system contains.
	 */
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		
		ImmutableBag<Entity> walls = world.getManager(GroupManager.class).getEntities("WALLS");
		ImmutableBag<Entity> enemies = world.getManager(GroupManager.class).getEntities("ENEMIES");
		Entity player = world.getManager(TagManager.class).getEntity("PLAYER");
		for (int i=0;i<walls.size();i++){
			if(collisionExists(player, walls.get(i))){
				handleWallCollision(player, walls.get(i));
			}
			for (int j=0;j<enemies.size();j++){
				if(collisionExists(enemies.get(j), walls.get(i))){
					handleWallCollision(enemies.get(j), walls.get(i));
				}
			}
		}
		
		for (int j=0;j<enemies.size();j++){
			if(collisionExists(player, enemies.get(j))){
				//handeEnemyCollision();
			}
		}
		
	}
	
	
	private void handleWallCollision(Entity player, Entity wall){		
		Size wallSize = sizeMapper.get(wall);
		Transform wallTransform = transformMapper.get(wall);
		Velocity playerVelocity = velocityMapper.get(player);
		Size playerSize = sizeMapper.get(player);
		Transform playerTransform = transformMapper.get(player);
		
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
	
	/**
	 * Checks if two entities is colliding with each other 
	 * 
	 * @param e1 The first entity
	 * @param e2 The second entity
	 * @return True if colliding false if not colliding
	 */
	public boolean collisionExists(Entity e1, Entity e2){
		
		Size e1Size = sizeMapper.get(e1);
		Transform e1Transform = transformMapper.get(e1);
		Size e2Size = sizeMapper.get(e2);
		Transform e2Transform = transformMapper.get(e2);
		
		float e1X = e1Transform.getX();
		float e1Y = e1Transform.getY();
		float e1Width = e1Size.getWidth();
		float e1Height = e1Size.getHeight();
		
		CollisionObject collisionObject1 =  new CollisionObject(e1X, e1Y, e1Width, e1Height) ;
		
		float e2X = e2Transform.getX();
		float e2Y = e2Transform.getY();
		float e2Width = e2Size.getWidth();
		float e2Height = e2Size.getHeight();
		
		CollisionObject collisionObject2 =  new CollisionObject(e2X, e2Y, e2Width, e2Height) ;
		
		return collisionObject1.collidesWith(collisionObject2);
		
	}
	
	/**
	 * An object used to check for collision with Andengine's collision detection
	 * 
	 * @author Jakob Svensson
	 *
	 */
	private class CollisionObject extends RectangularShape{
		
		/**
		 * Constructs a new collisionObject object
		 * 
		 * @param pX the x position of the object
		 * @param pY the y position of the object
		 * @param pWidth the width of the object
		 * @param pHeight the height of the object
		 */
		public CollisionObject(float pX, float pY, float pWidth, float pHeight) {
			super(pX, pY, pWidth, pHeight, null);
		}

		public IVertexBufferObject getVertexBufferObject() {
			return null;
		}

		@Override
		protected void onUpdateVertices() {			
		}
		
	}

}
