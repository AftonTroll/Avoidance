package se.chalmers.avoidance.systems;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.avoidance.components.Size;
import se.chalmers.avoidance.components.Transform;

import com.artemis.Entity;
import com.artemis.World;


public class CollisionSystemTest {
	private Entity e1;
	private Entity e2; 
	private Entity e3;
	private Entity e4; 
	private CollisionSystem cs;
	
	@Before
	public void setUp(){
		World world = new World();
		e1 = world.createEntity();
		e1.addComponent(new Transform(0,3));	
		e1.addComponent(new Size(10, 10));
		
		e2 = world.createEntity();
		e2.addComponent(new Transform(0,4));	
		e2.addComponent(new Size(10, 10));
		
		e3 = world.createEntity();
		e3.addComponent(new Transform(3,3));	
		e3.addComponent(new Size(10, 10));
		
		e4 = world.createEntity();
		e4.addComponent(new Transform(40,30));	
		e4.addComponent(new Size(10, 10));
		
		cs = new CollisionSystem();
		
		
	}
	
	@Test
	public void testCollsionExists(){
		assertTrue(cs.collisionExists(e1, e2));	
		assertTrue(!cs.collisionExists(e3, e4));
		assertTrue(cs.collisionExists(e1, e3));
		
	}

}
