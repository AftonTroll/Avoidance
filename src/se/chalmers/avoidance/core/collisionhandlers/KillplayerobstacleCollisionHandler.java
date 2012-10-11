package se.chalmers.avoidance.core.collisionhandlers;

import com.artemis.Entity;
import com.artemis.World;

public class KillplayerobstacleCollisionHandler implements CollisionHandler {
	
	private World world;
	
	public KillplayerobstacleCollisionHandler(World world){
		this.world=world;
	}
	
	public void handleCollision(Entity player, Entity obstacle) {
		//game over 
	}

}
