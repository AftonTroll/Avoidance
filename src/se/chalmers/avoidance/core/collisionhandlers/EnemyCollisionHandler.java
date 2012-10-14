package se.chalmers.avoidance.core.collisionhandlers;

import se.chalmers.avoidance.core.components.Jump;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

public class EnemyCollisionHandler implements CollisionHandler{
	
	private World world;
	
	public EnemyCollisionHandler (World world){
		this.world=world;
	}
	
	public void handleCollision(Entity player, Entity enemy) {
		//Handle collison between enemy and player
		ComponentMapper<Jump> jumpMapper = world.getMapper(Jump.class);
		Jump jump = jumpMapper.get(player);
		if (jump == null || !jump.isInTheAir()) {
			GameOverNotifier.getInstance().gameOver();
		}
		
	}

}
