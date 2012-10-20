/* 
 * Copyright (c) 2012 Jakob Svensson & Markus Ekström
 * 
 * This file is based on an example acquired from http://gamadu.com/artemis/demos.html (Spaceship Warrior), which
 * can be found under the following link:
 * http://code.google.com/p/spaceship-warrior/source/browse/src/com/gamadu/spaceshipwarrior/systems/CollisionSystem.java
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

import se.chalmers.avoidance.core.collisionhandlers.CollisionHandler;
import se.chalmers.avoidance.core.collisionhandlers.EnemyCollisionHandler;
import se.chalmers.avoidance.core.collisionhandlers.KillplayerobstacleCollisionHandler;
import se.chalmers.avoidance.core.collisionhandlers.PitobstacleCollisionHandler;
import se.chalmers.avoidance.core.collisionhandlers.PowerUpCollisionHandler;
import se.chalmers.avoidance.core.collisionhandlers.WallCollisionHandler;
import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;
import se.chalmers.avoidance.util.ScreenResolution;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
/**
 * System for handling collision between entities
 * 
 * @author Jakob Svensson
 * @author Markus Ekström
 *
 */
public class CollisionSystem extends EntitySystem{

	@Mapper
	ComponentMapper<Velocity> velocityMapper;
	@Mapper
	ComponentMapper<Transform> transformMapper;
	@Mapper
	ComponentMapper<Size> sizeMapper;
    private Bag<CollisionPair> collisionPairs;
    private CollisionObject collisionObject1 = new CollisionObject(0, 0, 0, 0);
    private CollisionObject collisionObject2 = new CollisionObject(0, 0, 0, 0);
	
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
        collisionPairs = new Bag<CollisionPair>();
        collisionPairs.add(new CollisionPair("MOVINGENTITIES", "WALLS", new WallCollisionHandler(world))); 
        collisionPairs.add(new CollisionPair("PLAYER", "POWERUPS", new PowerUpCollisionHandler(world)));
        collisionPairs.add(new CollisionPair("MOVINGENTITIES", "PITOBSTACLES", new PitobstacleCollisionHandler(world)));
        collisionPairs.add(new CollisionPair("PLAYER", "KILLPLAYEROBSTACLES", new KillplayerobstacleCollisionHandler(world)));
        collisionPairs.add(new CollisionPair("PLAYER", "ENEMIES", new EnemyCollisionHandler(world)));
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
		
        for(int i = 0; collisionPairs.size() > i; i++) {
            collisionPairs.get(i).checkForCollisions();
        }
       
        Entity player = world.getManager(TagManager.class).getEntity("PLAYER");  
        Transform playerTransform = transformMapper.get(player);
        ImmutableBag<Entity> walls = world.getManager(GroupManager.class).getEntities("WALLS");
        Size wallSize = sizeMapper.get(walls.get(0));
        Size playerSize = sizeMapper.get(player);
        
        float wallthickness;
        if(wallSize.getWidth()<wallSize.getHeight()){
        	wallthickness=wallSize.getWidth();
        }else{
        	wallthickness=wallSize.getHeight();
        }
        //Check if player is outside of the map
        if(playerTransform.getX()<0){
        	playerTransform.setX(wallthickness);
        }
        if(playerTransform.getX()>ScreenResolution.getWidthResolution()){
        	playerTransform.setX(ScreenResolution.getWidthResolution()-wallthickness-playerSize.getWidth());
        }
        if(playerTransform.getY()<0){
        	playerTransform.setY(wallthickness);
        }
        if(playerTransform.getY()>ScreenResolution.getHeightResolution()){
        	playerTransform.setY(ScreenResolution.getHeightResolution()-wallthickness-playerSize.getHeight());
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
		
		Size e1Size = sizeMapper.get(e1);
		Transform e1Transform = transformMapper.get(e1);
		Size e2Size = sizeMapper.get(e2);
		Transform e2Transform = transformMapper.get(e2);
		
		float e1X = e1Transform.getX();
		float e1Y = e1Transform.getY();
		float e1Width = e1Size.getWidth();
		float e1Height = e1Size.getHeight();
		
		float e2X = e2Transform.getX();
		float e2Y = e2Transform.getY();
		float e2Width = e2Size.getWidth();
		float e2Height = e2Size.getHeight();
		
		GroupManager groupManager = world.getManager(GroupManager.class);
		
		if(groupManager.getEntities("CIRCLESHAPES").contains(e1)&&groupManager.getEntities("CIRCLESHAPES").contains(e2)){
			
			float xDelta = e1X+e1Width/2-(e2X+e2Width/2);
			float yDelta = e1Y+e1Height/2-(e2Y+e2Height/2);
			float colDist = e1Width/2+e2Width/2;
			return xDelta*xDelta+yDelta*yDelta<=colDist*colDist;		
			
		}

		collisionObject1.setX(e1X);
		collisionObject1.setY(e1Y);
		collisionObject1.setWidth(e1Width);
		collisionObject1.setHeight(e1Height);
				
		collisionObject2.setX(e2X);
		collisionObject2.setY(e2Y);
		collisionObject2.setWidth(e2Width);
		collisionObject2.setHeight(e2Height);
		
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
	
    private class CollisionPair {
        private ImmutableBag<Entity> groupEntitiesA;
        private ImmutableBag<Entity> groupEntitiesB;
        private CollisionHandler handler;

        public CollisionPair(String group1, String group2, CollisionHandler handler) {
                groupEntitiesA = world.getManager(GroupManager.class).getEntities(group1);
                groupEntitiesB = world.getManager(GroupManager.class).getEntities(group2);
                this.handler = handler;
        }

        public void checkForCollisions() {
                for(int a = 0; groupEntitiesA.size() > a; a++) {
                        for(int b = 0; groupEntitiesB.size() > b; b++) {
                                Entity entityA = groupEntitiesA.get(a);
                                Entity entityB = groupEntitiesB.get(b);
                                if(collisionExists(entityA, entityB)) {
                                        handler.handleCollision(entityA, entityB);
                                }
                        }
                }
        }
    }
}
