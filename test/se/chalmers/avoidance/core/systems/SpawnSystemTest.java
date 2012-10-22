package se.chalmers.avoidance.core.systems;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

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
		assertTrue(world.getManager(TagManager.class).getEntity("PLAYER") != null);
		assertTrue(world.getManager(GroupManager.class).getEntities("WALLS").size() == 8);
		assertTrue(world.getManager(GroupManager.class).getEntities("POWERUPS").size() == 1);
		assertTrue(world.getManager(GroupManager.class).getEntities("KILLPLAYEROBSTACLES").size() == 2);
		assertTrue(world.getManager(GroupManager.class).getEntities("PITOBSTACLES").size() == 1);
		assertTrue(world.getManager(TagManager.class).getEntity("SCORE") != null);
	}

	@Test
	public void testSpawnEntity() {
		Time t = e.getComponent(Time.class);		
		for(int i = 1; i<=100; i++){
			t.updateTime(5);
			world.process();
			assertTrue(world.getManager(GroupManager.class).getEntities("ENEMIES").size() == i);
			
			ImmutableBag<Entity> enemies = world.getManager(GroupManager.class).getEntities("ENEMIES");
			for (int j = 0; j<enemies.size(); j++) {
				//check if any enemy is outside the map
				Transform trans = enemies.get(j).getComponent(Transform.class);
				Size size = enemies.get(j).getComponent(Size.class);
				assertFalse(trans.getX()<0);
				assertFalse(trans.getY()<0);
				assertFalse(trans.getX() + size.getWidth() > ScreenResolution.getWidthResolution());
				assertFalse(trans.getY() + size.getHeight() > ScreenResolution.getHeightResolution());
				
				//check if any enemy is colliding with something
				String[] objectList = {"WALLS", "PITOBSTACLES", "KILLPLAYEROBSTACLES"};
				for (int k = 0; k<objectList.length; k++){
					ImmutableBag<Entity> objects = world.getManager(GroupManager.class).getEntities("WALLS");
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
		for (int i =  0; i <100; i++){
			world.deleteEntity(world.getManager(GroupManager.class).getEntities("POWERUPS").get(0));
			t.updateTime(0.5f);
			world.process();
			assertTrue(world.getManager(GroupManager.class).getEntities("POWERUPS").isEmpty());
			t.updateTime(20f);
			world.process();
			assertTrue(world.getManager(GroupManager.class).getEntities("POWERUPS").size() == 1);
			
			//Clear enemies so they don't take up the whole map
			ImmutableBag<Entity> enemies = world.getManager(GroupManager.class).getEntities("ENEMIES");
			for (int j = 0; j<enemies.size(); j++) {
				world.deleteEntity(enemies.get(j));
			}
		}
	}
}
