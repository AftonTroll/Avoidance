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
		Entity player = world.getManager(TagManager.class).getEntity("PLAYER");
		for (int i=0;i<walls.size();i++){
			if(collisionExists(player, walls.get(i))){
				Velocity velocity = velocityMapper.get(player);
				velocity.setAngle(calculateAngle(velocity.getAngle(), walls.get(i)));
				correctPosition(walls.get(i), player);
			}
		}
		
	}
	
	
	private float calculateAngle(float angle, Entity wall){		
		
		float width = wall.getComponent(Size.class).getWidth();
		float height = wall.getComponent(Size.class).getHeight();
		float newAngle = angle;
		
		//Check if wall is placed horizontally or vertically
		if(width>height){
			newAngle = flipVertical(angle);
		}else{
			newAngle = flipHorizontal(angle);
		}
		return newAngle;
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
	
	private void correctPosition(Entity wall, Entity player){
		float width = wall.getComponent(Size.class).getWidth();
		float height = wall.getComponent(Size.class).getHeight();
		float wallX = wall.getComponent(Transform.class).getX();
		float wallY = wall.getComponent(Transform.class).getY();
		Transform playerTransform = transformMapper.get(player);
		float playerX = playerTransform.getX();
		float playerY = playerTransform.getY();
		float playerWidth = player.getComponent(Size.class).getWidth();
		float playerHeight = player.getComponent(Size.class).getHeight();
		
		if(width>height){
			if(playerY<wallY+height&&wallY==0){
				playerY=wallY+height;
				playerTransform.setY(playerY);
			}else if(playerY<wallY){
				playerY=wallY-playerHeight;
				playerTransform.setY(playerY);
			}
		}else{
			if(playerX<wallX+width&&wallX==0){
				playerX=wallX+width;
				playerTransform.setX(playerX);
			}else if(playerX<wallX){
				playerX=wallX-playerWidth;
				playerTransform.setX(playerX);
			}
		}
	}
	
		
	/**
	 * Checks if two entities is colliding with each other 
	 * 
	 * @param e1 The first entity
	 * @param e2 The second entity
	 * @return True if colliding false if not colliding
	 */
	public boolean collisionExists(Entity e1, Entity e2){
		
		float e1X = e1.getComponent(Transform.class).getX();
		float e1Y = e1.getComponent(Transform.class).getY();
		float e1Width = e1.getComponent(Size.class).getWidth();
		float e1Height = e1.getComponent(Size.class).getHeight();
		
		CollisionObject collisionObject1 =  new CollisionObject(e1X, e1Y, e1Width, e1Height) ;
		
		float e2X = e2.getComponent(Transform.class).getX();
		float e2Y = e2.getComponent(Transform.class).getY();
		float e2Width = e2.getComponent(Size.class).getWidth();
		float e2Height = e2.getComponent(Size.class).getHeight();
		
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
