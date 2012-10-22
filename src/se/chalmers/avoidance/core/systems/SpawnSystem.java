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
	private static final int POWERUP_SPAWN_CD = 10;
	private int lastSpawn = 0;
	private float lastPowerupTaken = 0;
	private int powerupSpawnCount = 0;
	
	
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
		world.addEntity(EntityFactory.createImmortalityPowerUp(world, centerX - 32, centerY - 100, 10));
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
			spawnEnemy();
			lastSpawn += SPAWNINTERVAL;
		}
		
		if (groupManager.getEntities("POWERUPS").isEmpty()) {
			//if the powerup was just taken, the time needs to be set
			if (lastPowerupTaken == 0) {
				lastPowerupTaken = currentTime;
			//Check if the cd is out
			} else if (currentTime >= (lastPowerupTaken + POWERUP_SPAWN_CD)) {
				spawnPowerup();
				lastPowerupTaken = 0;
			}

		}
	}
	
	/*
	 * Spawns a new enemy
	 */
	private void spawnEnemy(){
		Entity enemy;
		//Check if the next enemy should be a normal enemy or a quick enemy
		if((lastSpawn/SPAWNINTERVAL)%ENEMY_QUICKENEMY_RATIO == 1){
			enemy = EntityFactory.createQuickEnemy(world, 0, 0);
		} else {
			enemy = EntityFactory.createEnemy(world, 0, 0);
		}
		moveEntityToFreePosition(enemy);
		world.addEntity(enemy);
	}
	
	/*
	 * Spawns a new powerup
	 */
	private void spawnPowerup(){
	    Entity powerup;
	    if(powerupSpawnCount % 2 == 0) {
	        powerup = EntityFactory.createSpeedPowerUp(world, 0, 0, 300);
	    } else {
	        powerup = EntityFactory.createImmortalityPowerUp(world, 0, 0, 10);
	    }
		moveEntityToFreePosition(powerup);
		world.addEntity(powerup);
	}
	
	/*
	 * Moves an entity to a free position
	 */
	private void moveEntityToFreePosition(Entity entity){
		Transform transform = transformMapper.get(entity);
		if(transform == null){
			return;
		}
		boolean validPosition = false;
		while(!validPosition){
			//assume the position is valid until proven otherwise
			validPosition = true;
			transform.setX((float) (Math.random()*ScreenResolution.getWidthResolution()));
			transform.setY((float) (Math.random()*ScreenResolution.getHeightResolution()));
			
			
			//Check if the enemy is too close to the player
			Entity player = tagManager.getEntity("PLAYER");
			if(entity != player){
				Transform pTrans = transformMapper.get(player);
				Size pSize = sizeMapper.get(player);
				float playerCenterX = pTrans.getX() + pSize.getWidth()/2;
				float playerCenterY = pTrans.getY() + pSize.getHeight()/2;
	
				Transform eTrans = transformMapper.get(entity);
				Size eSize = sizeMapper.get(entity);
				float entityCenterX = eTrans.getX() + eSize.getWidth()/2;
				float entityCenterY = eTrans.getY() + eSize.getHeight()/2;
				
				float dx = entityCenterX - playerCenterX;
				float dy = entityCenterY - playerCenterY;
				
				//check distance from player and entity
				if(Math.sqrt(dx*dx+dy*dy) <= SPAWN_RANGE) {
					validPosition = false;
					continue;
				}
			}
			
			//check if entity is spawned is spawned on another entity
			String[] entityGroups = {"WALLS", "ENEMIES", "PITOBSTACLES", "KILLPLAYEROBSTACLES"};
			for(int j = 0; j<entityGroups.length; j++){
				ImmutableBag<Entity> enemyBag = groupManager.getEntities(entityGroups[j]);
				for (int i = 0; i<enemyBag.size(); i++){
					if(entity != enemyBag.get(i) && world.getSystem(CollisionSystem.class).collisionExists(entity, enemyBag.get(i))){
						validPosition = false;
						continue;
					}
				}
			}
		}
	}
}
