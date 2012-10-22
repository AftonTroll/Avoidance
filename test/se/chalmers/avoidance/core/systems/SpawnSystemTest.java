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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.avoidance.constants.GameConstants;
import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Time;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.util.ScreenResolution;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.utils.ImmutableBag;

public class SpawnSystemTest {
	private static SpawnSystem ss;
	private static World world;
	private static Entity e;
	
	@Before
	public void setUp() {
		world = new World();
		world.setManager(new TagManager());
		world.setManager(new GroupManager());
		world.setSystem(new CollisionSystem());
		
		ss = new SpawnSystem();
		world.setSystem(ss);
		world.initialize();
		
		Time t = new Time();
		e = world.createEntity();
		e.addComponent(t);
		world.addEntity(e);
	}
	
	@Test
	public void testInitialize() {
		assertTrue(world.getManager(TagManager.class).getEntity(GameConstants.TAG_PLAYER) != null);
		assertTrue(world.getManager(GroupManager.class).getEntities(GameConstants.GROUP_OBSTACLE_WALLS).size() == 8);
		assertTrue(world.getManager(GroupManager.class).getEntities(GameConstants.GROUP_POWERUPS).size() == 2);
		assertTrue(world.getManager(GroupManager.class).getEntities(GameConstants.GROUP_OBSTACLE_SPIKES).size() == 2);
		assertTrue(world.getManager(GroupManager.class).getEntities(GameConstants.GROUP_OBSTACLE_PITS).size() == 1);
		assertTrue(world.getManager(TagManager.class).getEntity(GameConstants.TAG_SCORE) != null);
	}

	@Test
	public void testSpawnEntity() {
		Time t = e.getComponent(Time.class);		
		for(int i = 1; i<=100; i++){
			t.updateTime(5);
			world.process();
			assertTrue(world.getManager(GroupManager.class).getEntities(GameConstants.GROUP_ENEMIES).size() == i);
			
			ImmutableBag<Entity> enemies = world.getManager(GroupManager.class).getEntities(GameConstants.GROUP_ENEMIES);
			for (int j = 0; j<enemies.size(); j++) {
				//check if any enemy is outside the map
				Transform trans = enemies.get(j).getComponent(Transform.class);
				Size size = enemies.get(j).getComponent(Size.class);
				assertFalse(trans.getX()<0);
				assertFalse(trans.getY()<0);
				assertFalse(trans.getX() + size.getWidth() > ScreenResolution.getWidthResolution());
				assertFalse(trans.getY() + size.getHeight() > ScreenResolution.getHeightResolution());
				
				//check if any enemy is colliding with something
				String[] objectList = {GameConstants.GROUP_OBSTACLE_WALLS, GameConstants.GROUP_OBSTACLE_PITS, GameConstants.GROUP_OBSTACLE_SPIKES};
				for (int k = 0; k<objectList.length; k++){
					ImmutableBag<Entity> objects = world.getManager(GroupManager.class).getEntities(objectList[k]);
					for (int l = 0; l<objects.size(); l++){
						assertFalse(world.getSystem(CollisionSystem.class).collisionExists(enemies.get(j), objects.get(l)));
					}
				}
			}
		}
	}
	
	@Test
	public void testSpawnPowerup() {
		Time t = e.getComponent(Time.class);
		world.deleteEntity(world.getManager(GroupManager.class).getEntities(GameConstants.GROUP_POWERUPS).get(1));
		for (int i =  0; i <100; i++){
			world.deleteEntity(world.getManager(GroupManager.class).getEntities(GameConstants.GROUP_POWERUPS).get(0));
			t.updateTime(0.5f);
			world.process();
			assertTrue(world.getManager(GroupManager.class).getEntities(GameConstants.GROUP_POWERUPS).isEmpty());
			t.updateTime(20f);
			world.process();
			assertTrue(world.getManager(GroupManager.class).getEntities(GameConstants.GROUP_POWERUPS).size() == 1);
			
			//Clear enemies so they don't take up the whole map
			ImmutableBag<Entity> enemies = world.getManager(GroupManager.class).getEntities(GameConstants.GROUP_ENEMIES);
			for (int j = 0; j<enemies.size(); j++) {
				world.deleteEntity(enemies.get(j));
			}
		}
	}
}
