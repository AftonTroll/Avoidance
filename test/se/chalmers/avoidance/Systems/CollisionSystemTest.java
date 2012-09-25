package se.chalmers.avoidance.systems;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.avoidance.components.Size;
import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;

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
	private CollisionSystem cs;
	
	@Before
	public void setUp(){
		World world = new World();
		TagManager tm = new TagManager();
		GroupManager gm = new GroupManager();
		world.setManager(tm);
		world.setManager(gm);
		
		e1 = world.createEntity();
		tm.register("Player", e1);
		e1.addComponent(new Transform(0,3));
		e1.addComponent(new Velocity(1,(float)(Math.PI/4)));
		e1.addComponent(new Size(1, 1));
		
		e2 = world.createEntity();
		gm.add(e2, "Walls");
		e2.addComponent(new Transform(0,4));	
		e2.addComponent(new Size(10, 9));
		
		e3 = world.createEntity();
		e3.addComponent(new Transform(3,3));	
		e3.addComponent(new Size(10, 10));
		
		e4 = world.createEntity();
		e4.addComponent(new Transform(40,30));	
		e4.addComponent(new Size(10, 10));
		
		e5 = world.createEntity();
		gm.add(e5, "Walls");
		e5.addComponent(new Transform(20,4));	
		e5.addComponent(new Size(9, 10));
		
		cs = new CollisionSystem(world);
		cs.initialize();
		
	}
	
	@Test
	public void testCollsionExists(){
		assertTrue(cs.collisionExists(e1, e2));	
		assertTrue(!cs.collisionExists(e3, e4));
		
	}
	
	@Test
	public void testProcessEntities(){
		cs.processEntities(null);
		assertTrue((e1.getComponent(Velocity.class).getAngle()-(float)(2*Math.PI-Math.PI/4)<0.01));
		e1.removeComponent(Transform.class);
		e1.addComponent(new Transform(20,3));
		cs.processEntities(null);
		assertTrue((e1.getComponent(Velocity.class).getAngle()-(float)(Math.PI+Math.PI/4)<0.01));
	}

}
