/*
* Copyright (c) 2012 Filip Brynfors
*
* This file is part of Avoidance.
*
* Avoidance is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Avoidance is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Avoidance. If not, see <http://www.gnu.org/licenses/>.
*
*/

package se.chalmers.avoidance.core.systems;


import se.chalmers.avoidance.core.EntityFactory;
import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Time;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Buff.BuffType;
import se.chalmers.avoidance.util.ScreenResolution;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;

/**
 * A system that creates the initial entities
 * and then continues to create one more enemy
 * every 5 seconds
 * 
 * @author Filip Brynfors
 *
 */
public class SpawnSystem extends EntityProcessingSystem{
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Size> sizeMapper;
	private GroupManager groupManager;
	private TagManager tagManager;
	private final int SPAWNINTERVAL = 5;
	private int lastSpawn = 0;
	
	
	/**
	 * Constructs a new SpawnSystem
	 */
	public SpawnSystem() {
		super(Aspect.getAspectForAll(Time.class));
	}
	
	/**
	 * This method is called when the system is initialized
	 * Creates all the initial entities
	 */
	@Override
	protected void initialize() {
		transformMapper = world.getMapper(Transform.class);
		sizeMapper = world.getMapper(Size.class);
		tagManager = world.getManager(TagManager.class);
		groupManager = world.getManager(GroupManager.class);
		float wallThickness = 20f;
		//Create all the entities that should be on the map when the game starts
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
		world.addEntity(EntityFactory.createSpeedPowerUp(world, 300, 300, 300));
		world.addEntity(EntityFactory.createImmortalPowerUp(world, 500, 500, 5));
		world.addEntity(EntityFactory.createPitobstacle(world, 400, 600));
		world.addEntity(EntityFactory.createKillplayerbstacle(world, 800, 600));
	}

	/**
	 * This method is called when the spawning is to be updated.
	 * Spawns a new enemy every 5 seconds
	 * @param entities the bag of entities with the wanted components
	 */
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
		Entity enemy;
		if((lastSpawn/SPAWNINTERVAL)%5 == 1){
			enemy = EntityFactory.createQuickEnemy(world, 0, 0);
		} else {
			enemy = EntityFactory.createEnemy(world, 0, 0);
		}
		Transform enemyTransform = transformMapper.get(enemy);
		
		boolean validPosition = false;
		while(!validPosition){
			//assume the position is valid until proven otherwise
			validPosition = true;
			enemyTransform.setX((float) (Math.random()*ScreenResolution.getWidthResolution()));
			enemyTransform.setY((float) (Math.random()*ScreenResolution.getHeightResolution()));
			
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
