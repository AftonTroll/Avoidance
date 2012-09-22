package se.chalmers.avoidance.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;

public class CollisionSystem extends EntitySystem{

	public CollisionSystem(Aspect aspect) {
		super(aspect);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean checkProcessing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean collisionExists(Entity e1, Entity e2){
		
		return false;
	}

}
