package se.chalmers.avoidance.systems;

import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
/**
 * System for handling collision between entities
 * 
 * @author Jakob Svensson
 *
 */
public class CollisionSystem extends EntitySystem{

	public CollisionSystem() {
		super(Aspect.getAspectForAll(Transform.class, Velocity.class));
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean checkProcessing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		
		//Check collision between player and wall
	}
	/**
	 * Checks if two entities is colliding with each other 
	 * 
	 * @param e1 The first entity
	 * @param e2 The second entity
	 * @return True if colliding false if not colliding
	 */
	public boolean collisionExists(Entity e1, Entity e2){
		
		return false;
	}

}
