package se.chalmers.avoidance.core.systems;

import se.chalmers.avoidance.core.components.Spatial;
import se.chalmers.avoidance.core.components.Time;
import se.chalmers.avoidance.core.components.Transform;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;

public class HudRenderSystem extends EntitySystem{

	public HudRenderSystem() {
		super(Aspect.getAspectForAll(Time.class, Transform.class, Spatial.class));
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

}
