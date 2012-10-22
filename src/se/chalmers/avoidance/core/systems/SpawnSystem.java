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
import se.chalmers.avoidance.core.components.Buff.BuffType;
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

/**
 * A system that creates the initial entities and then continues
 * to create one more enemy every 5 seconds.
 * 
 * @author Filip Brynfors
 *
 */
public class SpawnSystem extends EntityProcessingSystem{
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Size> sizeMapper;
	private GroupManager groupManager;
	private TagManager tagManager;
	/* The time between the spawning of the enemies */
	private static final int SPAWNINTERVAL = 5;
	private static final float WALL_THICKNESS = 20f;
	/* The range of closest range enemies can spawn to a player */
	private static final int SPAWN_RANGE = 100;
	private static final int ENEMY_QUICKENEMY_RATIO = 5;
	private int lastSpawn = 0;
	
	
	/**
	 * Constructs a new SpawnSystem.
	 */
	public SpawnSystem() {
		super(Aspect.getAspectForAll(Time.class));
	}
	
	/**
	 * This method is called when the system is initialized.
	 * Creates all the initial entities
	 */
	@Override
	protected void initialize() {
		transformMapper = world.getMapper(Transform.class);
		sizeMapper = world.getMapper(Size.class);
		tagManager = world.getManager(TagManager.class);
		groupManager = world.getManager(GroupManager.class);
		float centerX = ScreenResolution.getWidthResolution()/2f;
		float centerY = ScreenResolution.getHeightResolution()/2f;

		//Create all the entities that should be on the map when the game starts
		world.addEntity(EntityFactory.createPlayer(world, centerX -32, centerY -32));
		world.addEntity(EntityFactory.createWall(world, ScreenResolution.getWidthResolution(), 
				WALL_THICKNESS, 0, 0));
		world.addEntity(EntityFactory.createWall(world, ScreenResolution.getWidthResolution(), 
				WALL_THICKNESS, 0, ScreenResolution.getHeightResolution() - WALL_THICKNESS));
		world.addEntity(EntityFactory.createWall(world, WALL_THICKNESS, 
				ScreenResolution.getHeightResolution(), 0, 0));
		world.addEntity(EntityFactory.createWall(world, WALL_THICKNESS, 
				ScreenResolution.getHeightResolution(), 
				ScreenResolution.getWidthResolution() - WALL_THICKNESS, 0));
		world.addEntity(EntityFactory.createObstacle(world, 50, 50, centerX-225, centerY -125));
		world.addEntity(EntityFactory.createObstacle(world, 50, 50, centerX+175, centerY -125));
		world.addEntity(EntityFactory.createObstacle(world, 50, 50, centerX-225, centerY +75));
		world.addEntity(EntityFactory.createObstacle(world, 50, 50, centerX+175, centerY +75));
		world.addEntity(EntityFactory.createPitobstacle(world, centerX-32, 
				ScreenResolution.getHeightResolution() - WALL_THICKNESS-164));
		world.addEntity(EntityFactory.createSpeedPowerUp(world, centerX-32, 
		        WALL_THICKNESS + 50, 300));
		world.addEntity(EntityFactory.createKillplayerbstacle(world, centerX-232,
				ScreenResolution.getHeightResolution()-WALL_THICKNESS-114));
		world.addEntity(EntityFactory.createKillplayerbstacle(world, centerX+168,
				ScreenResolution.getHeightResolution()-WALL_THICKNESS-114));
		world.addEntity(EntityFactory.createScore(world));
		world.addEntity(EntityFactory.createImmortalPowerUp(world, centerX - 32, centerY - 100, 10));
	}

	/**
	 * This method is called when the spawning is to be updated.
	 * Spawns a new enemy every 5 seconds
	 * @param entity an entity with the wanted components
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
		//Check if the next enemy should be a normal enemy or a quick enemy
		if((lastSpawn/SPAWNINTERVAL)%ENEMY_QUICKENEMY_RATIO == 1){
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
			if(Math.sqrt(dx*dx+dy*dy) <= SPAWN_RANGE) {
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
				if(enemy != enemyBag.get(i) && world.getSystem(CollisionSystem.class).collisionExists(enemy, enemyBag.get(i))){
					validPosition = false;
					continue;
				}
			}
		}
		world.addEntity(enemy);
	}
}
