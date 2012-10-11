package se.chalmers.avoidance.core.collisionhandlers;

import se.chalmers.avoidance.core.components.Score;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

public class PitobstacleCollisionHandler implements CollisionHandler {
	
	private World world;
	private ComponentMapper<Score> scoreMapper;
	
	/**
	 * Construct a new PitobstacleCollisionHandler
	 * 
	 * @param world
	 */
	public PitobstacleCollisionHandler(World world){
		this.world=world;
		scoreMapper = world.getMapper(Score.class);
	}
	
	/**
	 * Handles collision between moving entities and pitobstcales
	 * 
	 * @param movingEntity the moving entity
	 * @param obstacle the pitobstacle
	 */
	public void handleCollision(Entity movingEntity, Entity obstacle) {
		GroupManager groupManager = world.getManager(GroupManager.class);
		if (groupManager.getEntities("PLAYER").contains(movingEntity) && groupManager.getEntities("PITOBSTACLES").contains(obstacle)) {
			//Handle collison between pitobstacle and player
		}
		if(groupManager.getEntities("ENEMIES").contains(movingEntity) && groupManager.getEntities("PITOBSTACLES").contains(obstacle)){
			world.deleteEntity(movingEntity);
			Score score = scoreMapper.get(world.getManager(TagManager.class).getEntity("SCORE"));
			score.addKillScore(100);
		}
	}
	
}
