package se.chalmers.avoidance.core.systems;


import se.chalmers.avoidance.core.EntityFactory;
import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Time;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.util.ScreenResolution;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;

public class SpawnSystem extends EntityProcessingSystem{
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Size> sizeMapper;
	private GroupManager groupManager;
	private TagManager tagManager;
	private final int SPAWNINTERVAL = 5;
	private int lastSpawn = 0;
	
	public SpawnSystem() {
		super(Aspect.getAspectForAll(Time.class));
	}
	
	@Override
	protected void initialize() {
		transformMapper = world.getMapper(Transform.class);
		sizeMapper = world.getMapper(Size.class);
		tagManager = world.getManager(TagManager.class);
		groupManager = world.getManager(GroupManager.class);
		float wallThickness = 20f;
		world.addEntity(EntityFactory.createPlayer(world));
		world.addEntity(EntityFactory.createWall(world, ScreenResolution.getWidthResolution(), 
				wallThickness, 0, 0));
		world.addEntity(EntityFactory.createWall(world, ScreenResolution.getWidthResolution(), 
				wallThickness, 0, ScreenResolution.getHeightResolution() - wallThickness));
		world.addEntity(EntityFactory.createWall(world, wallThickness, 
				ScreenResolution.getHeightResolution(), 0, 0));
		world.addEntity(EntityFactory.createWall(world, wallThickness, 
				ScreenResolution.getHeightResolution(), 
				ScreenResolution.getWidthResolution() - wallThickness, 0));
		world.addEntity(EntityFactory.createObstacle(world, 50, 50, 200, 200));
		world.addEntity(EntityFactory.createScore(world));
	}

	@Override
	protected void process(Entity entity) {
		float currentTime = entity.getComponent(Time.class).getTime();
		//loop to not miss any spawn even in case the tpf is longer than the spawninterval
		while ((currentTime - lastSpawn) >= SPAWNINTERVAL) {
			spawnEnemyAtFreePosition();
			lastSpawn += SPAWNINTERVAL;
		}
	}
	
	//Spawns an enemy at a free position
	private void spawnEnemyAtFreePosition(){
		Entity enemy = EntityFactory.createEnemy(world, 0, 0);
		Transform enemyTransform = transformMapper.get(enemy);
		
		boolean validPosition = false;
		while(!validPosition){
			//assume the position is valid until proven otherwise
			validPosition = true;
			enemyTransform.setX((float) (Math.random()*1200));
			enemyTransform.setY((float) (Math.random()*800));
			
			//Check if the enemy is too close to the player
			Entity player = tagManager.getEntity("PLAYER");
			Transform pTrans = transformMapper.get(player);
			Size pSize = sizeMapper.get(player);
			float playerCenterX = pTrans.getX() + pSize.getWidth()/2;
			float playerCenterY = pTrans.getY() + pSize.getHeight()/2;

			Transform eTrans = transformMapper.get(enemy);
			Size eSize = sizeMapper.get(enemy);
			float enemyCenterX = eTrans.getX() + eSize.getWidth()/2;
			float enemyCenterY = eTrans.getY() + eSize.getHeight()/2;
			
			float dx = enemyCenterX - playerCenterX;
			float dy = enemyCenterY - playerCenterY;
			
			//check distance from player and enemy
			if(Math.sqrt(dx*dx+dy*dy) <= 100) {
				validPosition = false;
				continue;
			}
			
			//check if the enemy is spawned on a wall
			ImmutableBag<Entity> wallBag = groupManager.getEntities("WALLS");
			for (int i = 0; i<wallBag.size(); i++){
				if(world.getSystem(CollisionSystem.class).collisionExists(enemy, wallBag.get(i))){
					validPosition = false;
					continue;
				}
			}
			
			//check if enemy is spawned is spawned on another enemy
			ImmutableBag<Entity> enemyBag = groupManager.getEntities("ENEMIES");
			for (int i = 0; i<enemyBag.size(); i++){
				if(enemy != enemyBag.get(i)){
					if(world.getSystem(CollisionSystem.class).collisionExists(enemy, enemyBag.get(i))){
						validPosition = false;
						continue;
					}
				}
			}
		}
		world.addEntity(enemy);
	}
}
