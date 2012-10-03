package se.chalmers.avoidance.core.systems;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import se.chalmers.avoidance.core.components.Time;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

public class SpawnSystemTest {
	private static SpawnSystem ss;
	private static World world;
	private static Entity e;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		world = new World();
		world.setManager(new TagManager());
		world.setManager(new GroupManager());
		world.setSystem(new CollisionSystem());
		
		ss = new SpawnSystem();
		world.setSystem(ss);
		ss.initialize();
		
		Time t = new Time();
		e = world.createEntity();
		e.addComponent(t);
		world.addEntity(e);
	}
	
	@Test
	public void testInitialize() {
		assertTrue(world.getManager(TagManager.class).getEntity("PLAYER") != null);
		assertTrue(world.getManager(GroupManager.class).getEntities("WALLS").size() == 4);
	}

	@Test
	public void testProcessEntity() {
		Time t = e.getComponent(Time.class);		
		for(int i = 1; i<=50; i++){
			t.updateTime(5);
			ss.process(e);
			assertTrue(world.getManager(GroupManager.class).getEntities("ENEMIES").size() == i);
		}
	}
}
