package se.chalmers.avoidance.core.collisionhandlers;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;

public class PitobstacleCollisionHandler implements CollisionHandler {
	
	private World world;
	
	public PitobstacleCollisionHandler(World world){
		this.world=world;
	}
	
	public void handleCollision(Entity movingEntity, Entity obstacle) {
		GroupManager groupManager = world.getManager(GroupManager.class);
		if (groupManager.getEntities("PLAYER").contains(movingEntity) && groupManager.getEntities("PITOBSTACLE").contains(obstacle)) {
			//Handle collison between pitobstacle and player
		}
		if(groupManager.getEntities("ENEMIES").contains(movingEntity) && groupManager.getEntities("PITOBSTACLE").contains(obstacle)){
			world.deleteEntity(movingEntity);
		}
	}
	
}
