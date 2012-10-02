package se.chalmers.avoidance.core.systems;


import se.chalmers.avoidance.core.components.Time;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

public class SpawnSystem extends EntityProcessingSystem{

	public SpawnSystem() {
		super(Aspect.getAspectForAll(Time.class));
		
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void process(Entity entity) {
		// TODO Auto-generated method stub
		
	}

}
