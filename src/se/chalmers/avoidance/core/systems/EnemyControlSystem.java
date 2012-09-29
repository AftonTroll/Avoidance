package se.chalmers.avoidance.core.systems;

import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;

public class EnemyControlSystem extends EntitySystem{

	public EnemyControlSystem() {
		super(Aspect.getAspectForAll(Transform.class, Velocity.class));
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> arg0) {
		
	}
	
}
