package se.chalmers.avoidance.core.systems;


import se.chalmers.avoidance.core.EntityFactory;
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
		world.addEntity(EntityFactory.createPlayer(world));
		world.addEntity(EntityFactory.createWall(world,1200,25,0,0));
		world.addEntity(EntityFactory.createWall(world,1200,20,0,455));
		world.addEntity(EntityFactory.createWall(world,20,800,0,0));
		world.addEntity(EntityFactory.createWall(world,20,800,700,0));
	}

	@Override
	protected void process(Entity entity) {
		// TODO Auto-generated method stub
		
	}

}
