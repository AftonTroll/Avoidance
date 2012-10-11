package se.chalmers.avoidance.core.collisionhandlers;

import com.artemis.Entity;
import com.artemis.World;

public class KillplayerobstacleCollisionHandler implements CollisionHandler {
	
	private World world;
	
	/**
	 * Construct a new KillplayerobstacleCollisionHandler
	 * 
	 * @param world the world object
	 */
	public KillplayerobstacleCollisionHandler(World world){
		this.world=world;
	}
	
	/**
	 * Handles collision between player and killplayerobstacles
	 * 
	 * @param player the player
	 * @param obstacle the killplayerobstacle
	 */
	public void handleCollision(Entity player, Entity obstacle) {
		//game over 
	}

}
