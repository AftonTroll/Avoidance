/* 
 * Copyright (c) 2012 Markus Ekström
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Avoidance.  If not, see <http://www.gnu.org/licenses/>. 
 *  
 */
package se.chalmers.avoidance.core.collisionhandlers;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.avoidance.core.EntityFactory;
import se.chalmers.avoidance.core.components.Immortal;
import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;
import se.chalmers.avoidance.core.systems.CollisionSystem;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;

public class PowerUpCollisionHandlerTest {
	private Entity player;
	private Entity speed;
	private Entity immortality;
	private final CollisionSystem cs = new CollisionSystem();
	private final World world = new World();
	private final GroupManager groupManager = new GroupManager();
	private final TagManager tagManager = new TagManager();
    private ImmutableBag<Entity> groupEntitiesA;
    private ImmutableBag<Entity> groupEntitiesB;
    private Bag<Entity> entities = new Bag<Entity>();
	
	
	@Before
	public void setUp() {
		world.setManager(groupManager);
		world.setManager(tagManager);
		world.setSystem(cs);
		player = EntityFactory.createPlayer(world, 0, 0);
		speed = EntityFactory.createSpeedPowerUp(world, 40, 40, 100);
		immortality = EntityFactory.createImmortalityPowerUp(world, 200, 200, 2);
		world.initialize();
		world.addEntity(EntityFactory.createScore(world));
		world.addEntity(EntityFactory.createWall(world, 1, 1, 600, 600));
		
		
		groupEntitiesA = world.getManager(GroupManager.class).getEntities("PLAYER");
		groupEntitiesB = world.getManager(GroupManager.class).getEntities("POWERUP");
		entities.addAll(groupEntitiesA);
		entities.addAll(groupEntitiesB);
	}

	@Test
	public void testHandleCollision() {
		Transform playerTransform = player.getComponent(Transform.class);
		Transform speedTransform = speed.getComponent(Transform.class);
		world.setDelta(1);
		
		playerTransform.setPosition(40, 40);
		speedTransform.setPosition(30, 30);
		
		assertTrue(player.getComponent(Velocity.class).getSpeed() == 0);
		
		speedTransform.setPosition(40, 40);
		assertTrue(player != null);
		assertTrue(speed != null);
		assertTrue(cs != null);
		assertTrue(player.getComponent(Size.class) != null);
		assertTrue(speed.getComponent(Size.class) != null);
		assertTrue(cs.collisionExists(player, speed));
		
		world.process();
		
		assertTrue(player.getComponent(Velocity.class).getSpeed() == 100);
		assertTrue(world.getEntity(player.getId()) == null);
		
		assertTrue(!player.getComponent(Immortal.class).isImmortal());
		playerTransform.setPosition(200, 200);
		world.process();
		assertTrue(player.getComponent(Immortal.class).isImmortal());
	}
}
