package se.chalmers.avoidance.core.collisionhandlers;

import se.chalmers.avoidance.core.components.Jump;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
/**
 * Handles collision between player and enemies.
 * 
 * @author Jakob Svensson
 *
 */
public class EnemyCollisionHandler implements CollisionHandler{
	
	private World world;
	
	/**
	 * Constructs a EnemyCollisionHandler.
	 * 
	 * @param world The world.
	 */
	public EnemyCollisionHandler (World world){
		this.world=world;
	}
	/**
	 * Takes the player and enemy and handles the collision between them
	 * 
	 * @param player The player.
	 * @param enemy The enemy.
	 */
	public void handleCollision(Entity player, Entity enemy) {
		//Handle collison between enemy and player
		ComponentMapper<Jump> jumpMapper = world.getMapper(Jump.class);
		Jump jump = jumpMapper.get(player);
		if (jump == null || !jump.isInTheAir()) {
			GameOverNotifier.getInstance().gameOver();
		}
		
	}

}
