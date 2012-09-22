package se.chalmers.avoidance.Systems;


import org.junit.Before;
import org.junit.Test;

import se.chalmers.avoidance.components.Size;
import se.chalmers.avoidance.components.Transform;

import com.artemis.Entity;
import com.artemis.World;

public class CollisionSystemTest {
	
	
	private Entity e1;
	private Entity e2; 
	@Before
	public void setUp(){
		World world = new World();
		e1 = world.createEntity();
		e1.addComponent(new Transform(0,3));	
		e1.addComponent(new Size(10, 10));
		
		e2 = world.createEntity();
		e2.addComponent(new Transform(0,4));	
		e2.addComponent(new Size(10, 10));
		
		
	}
	
	@Test
	public void testCollsionExists(){
		
	}

}
