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

import se.chalmers.avoidance.constants.GameConstants;
import se.chalmers.avoidance.core.EntityFactory;
import se.chalmers.avoidance.core.components.Immortal;
import se.chalmers.avoidance.core.components.Score;
import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;
import se.chalmers.avoidance.core.systems.CollisionSystem;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

public class PowerUpCollisionHandlerTest {
	private Entity player;
	private Entity speed;
	private Entity immortality;
	private final CollisionSystem cs = new CollisionSystem();
	private final World world = new World();
	private final GroupManager groupManager = new GroupManager();
	private final TagManager tagManager = new TagManager();

	@Before
	public void setUp() {
		world.setManager(groupManager);
		world.setManager(tagManager);
		world.setSystem(cs);
		player = EntityFactory.createPlayer(world, 0, 0);
		speed = EntityFactory.createPowerupSpeed(world, 40, 40, 100);
		immortality = EntityFactory
				.createPowerUpImmortality(world, 200, 200, 2);
		world.initialize();
		world.addEntity(EntityFactory.createScore(world));
		world.addEntity(EntityFactory.createObstacleWall(world, 1, 1, 600, 600));
		world.addEntity(player);
		world.addEntity(speed);
		world.addEntity(immortality);
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
		long totalDeleted = world.getEntityManager().getTotalDeleted();
		world.process();
		world.process();
		assertTrue(player.getComponent(Velocity.class).getSpeed() == 100);
		assertTrue(world.getEntityManager().getTotalDeleted() == totalDeleted + 1);

		assertTrue(!player.getComponent(Immortal.class).isImmortal());
		playerTransform.setPosition(200, 200);
		world.process();
		assertTrue(player.getComponent(Immortal.class).isImmortal());
		assertTrue(tagManager.getEntity(GameConstants.TAG_SCORE)
				.getComponent(Score.class).getScore() == 2 * Score.POWERUP_PICKUP_SCORE);
	}
}
