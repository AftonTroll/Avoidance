/* 
 * Copyright (c) 2012 Jakob Svensson
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

package se.chalmers.avoidance.core.systems;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.avoidance.constants.GameConstants;
import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;


public class CollisionSystemTest {
	private Entity e1;
	private Entity e2; 
	private Entity e3;
	private Entity e4; 
	private Entity e5; 
	private Entity e6; 
	private Entity e7; 
	private CollisionSystem cs;
	
	@Before
	public void setUp(){
		World world = new World();
		TagManager tm = new TagManager();
		GroupManager gm = new GroupManager();
		world.setManager(tm);
		world.setManager(gm);
		
		e1 = world.createEntity();
		tm.register(GameConstants.TAG_PLAYER, e1);
		e1.addComponent(new Transform(0,3));
		e1.addComponent(new Velocity(1,(float)(Math.PI/4)));
		e1.addComponent(new Size(1, 1));
		
		e2 = world.createEntity();
		gm.add(e2, GameConstants.GROUP_OBSTACLE_WALLS);
		e2.addComponent(new Transform(0,4));	
		e2.addComponent(new Size(10, 9));
		
		e3 = world.createEntity();
		e3.addComponent(new Transform(3,3));	
		e3.addComponent(new Size(10, 10));
		
		e4 = world.createEntity();
		e4.addComponent(new Transform(40,30));	
		e4.addComponent(new Size(10, 10));
		
		e5 = world.createEntity();
		gm.add(e5, GameConstants.GROUP_OBSTACLE_WALLS);
		e5.addComponent(new Transform(20,4));	
		e5.addComponent(new Size(9, 10));
		
		e6 = world.createEntity();
		gm.add(e6, GameConstants.GROUP_CIRCLE_SHAPES);
		e6.addComponent(new Transform(95,80));	
		e6.addComponent(new Size(10, 10));
		
		e7 = world.createEntity();
		gm.add(e7, GameConstants.GROUP_CIRCLE_SHAPES);
		e7.addComponent(new Transform(90,80));	
		e7.addComponent(new Size(10, 10));
		
		cs = new CollisionSystem();
		world.setSystem(cs);
		world.initialize();
		
	}
	
	@Test
	public void testCollsionExists(){
		assertTrue(cs.collisionExists(e1, e2));	
		assertTrue(!cs.collisionExists(e3, e4));
		assertTrue(!cs.collisionExists(e1, e5));
		assertTrue(cs.collisionExists(e6, e7));	
	}
	
	@Test
	public void testOutsideMap(){
		e1.getComponent(Transform.class).setX(-20);
		cs.processEntities(null);
		assertTrue(playerInsideMap());
		
		e1.getComponent(Transform.class).setX(2000);
		cs.processEntities(null);
		assertTrue(playerInsideMap());
		
		e1.getComponent(Transform.class).setY(-20);
		cs.processEntities(null);
		assertTrue(playerInsideMap());
		
		e1.getComponent(Transform.class).setY(900);
		cs.processEntities(null);
		assertTrue(playerInsideMap());
		
	}
	
	private boolean playerInsideMap(){
		Transform playerTransform = e1.getComponent(Transform.class);
		
		return playerTransform.getX()>0&&playerTransform.getX()<1280&&playerTransform.getY()>0&&playerTransform.getY()<800;
	}

}
