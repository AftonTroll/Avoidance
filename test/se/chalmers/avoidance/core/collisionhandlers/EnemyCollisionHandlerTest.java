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
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Avoidance. If not, see <http://www.gnu.org/licenses/>.
*
*/

package se.chalmers.avoidance.core.collisionhandlers;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.avoidance.constants.GameConstants;
import se.chalmers.avoidance.core.EntityFactory;
import se.chalmers.avoidance.core.components.Immortal;
import se.chalmers.avoidance.core.components.Jump;
import se.chalmers.avoidance.core.components.Score;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.systems.CollisionSystem;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

public class EnemyCollisionHandlerTest {
    private Entity player;
    private Entity enemy1;
    private Entity enemy2;
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
        enemy1 = EntityFactory.createEnemy(world, 100, 100);
        enemy2 = EntityFactory.createEnemy(world, 300, 300);
        world.initialize();
        world.addEntity(EntityFactory.createScore(world));
        world.addEntity(EntityFactory.createWall(world, 1, 1, 600, 600));
        world.addEntity(player);
        world.addEntity(enemy1);
        world.addEntity(enemy2);
    }

    @Test
    public void testHandleCollision() {
        Transform playerTransform = player.getComponent(Transform.class);
        world.setDelta(0.5f);
        assertTrue(!player.getComponent(Jump.class).isInTheAir());
        assertTrue(!player.getComponent(Immortal.class).isImmortal());
        player.getComponent(Immortal.class).setDuration(2);
        player.getComponent(Immortal.class).setImmortal(true);
        playerTransform.setPosition(100, 100);
        assertTrue(cs.collisionExists(player, enemy1));
        long totalDeleted = world.getEntityManager().getTotalDeleted();
        world.process();
        world.process();
        assertTrue(world.getEntityManager().getTotalDeleted() == totalDeleted + 1);
        
        world.setDelta(1f);
        
        player.getComponent(Immortal.class).setImmortal(false);
        assertTrue(!player.getComponent(Immortal.class).isImmortal());
        
        player.getComponent(Jump.class).setInTheAir(true);
        assertTrue(player.getComponent(Jump.class).isInTheAir());
        playerTransform.setPosition(300, 300);
        assertTrue(cs.collisionExists(player, enemy2));
        totalDeleted = world.getEntityManager().getTotalDeleted();
        world.process();
        world.process();
        assertTrue(world.getEntityManager().getTotalDeleted() != totalDeleted + 1);
        player.getComponent(Jump.class).subtractInTheAirDurationLeft(1.8f);
        world.process();
        world.process();
        assertTrue(world.getEntityManager().getTotalDeleted() == totalDeleted + 1);
        assertTrue(tagManager.getEntity(GameConstants.SCORE_TAG).getComponent(Score.class).getScore() == 2 * Score.KILL_SCORE);
    }

}
