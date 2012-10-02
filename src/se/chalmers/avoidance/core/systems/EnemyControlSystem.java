package se.chalmers.avoidance.core.systems;

import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.utils.ImmutableBag;

public class EnemyControlSystem extends EntitySystem{
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<Size> sizeMapper;
	private TagManager tagManager;
	private GroupManager groupManager;
	
	public EnemyControlSystem() {
		super(Aspect.getAspectForAll(Transform.class, Velocity.class, Size.class));
	}
	
	/**
	 * This method is called when the system is initialized
	 */
	@Override
	protected void initialize() {
		transformMapper = world.getMapper(Transform.class);
		velocityMapper = world.getMapper(Velocity.class);
		sizeMapper = world.getMapper(Size.class);
		tagManager = world.getManager(TagManager.class);
		groupManager = world.getManager(GroupManager.class);
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> bag) {
		Entity player = tagManager.getEntity("PLAYER");
		if (player != null) {
			Transform playerTransform = transformMapper.get(player);
			Size playerSize = sizeMapper.get(player);
			float playerCenterX = playerTransform.getX() + playerSize.getWidth()/2;
			float playerCenterY = playerTransform.getY() + playerSize.getHeight()/2;
			
			ImmutableBag<Entity> enemyBag = groupManager.getEntities("ENEMIES");
			for (int i = 0; i<enemyBag.size(); i++) {
				Entity enemy = enemyBag.get(i);
				Transform enemyTransform = transformMapper.get(enemy);
				Size enemySize = sizeMapper.get(enemy);
				Velocity enemyVelocity = velocityMapper.get(enemy);
				float enemyCenterX = enemyTransform.getX() + enemySize.getWidth()/2;
				float enemyCenterY = enemyTransform.getY() + enemySize.getHeight()/2;

				float dx = enemyCenterX-playerCenterX;
				float dy = enemyCenterY-playerCenterY;
				float accelerationAngle = (float) Math.atan2(dy, dx);
				
			}
		}
	}
}