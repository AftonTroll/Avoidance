package se.chalmers.avoidance.core.systems;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

public class EnemyControlSystemTest {

	private static final float TOLERANCE = 0.0001f;
	private World world = new World();
	private TagManager tagManager = new TagManager();
	private GroupManager groupManager = new GroupManager();
	private EnemyControlSystem ecs = new EnemyControlSystem();
	private Entity player;
	private Entity e1;
	private Entity e2;

	@BeforeClass
	public void setUpBeforeClass() {
		world.setManager(tagManager);
		world.setManager(groupManager);
		world.setSystem(ecs);
		
		player = world.createEntity();
		player.addComponent(new Transform());
		tagManager.register("PLAYER", player);
		
		e1 = world.createEntity();
		e1.addComponent(new Transform());
		e1.addComponent(new Velocity());
		
		e2 = world.createEntity();
		e2.addComponent(new Transform());
		e2.addComponent(new Velocity());
		
		groupManager.add(e1, "ENEMIES");
		groupManager.add(e2, "ENEMIES");
		
		ecs.initialize();
		
	}
	
	@Before
	public void setUp() {
		e1.getComponent(Transform.class).setPosition(10, 0);
		e1.getComponent(Velocity.class).setAngle(0);
		e1.getComponent(Velocity.class).setSpeed(0);
		
		e2.getComponent(Transform.class).setPosition(-25,-25);
		e2.getComponent(Velocity.class).setAngle(0);
		e2.getComponent(Velocity.class).setSpeed(0);
		
		player.getComponent(Transform.class).setPosition(0 ,0);
	}

	@Test
	public void testProcessEntitiesWithoutWall() {
		world.setDelta(1);
		ecs.processEntities(null);
		
		assertTrue(e1.getComponent(Velocity.class).getSpeed() - 10 <= TOLERANCE);
		assertTrue(e1.getComponent(Velocity.class).getAngle() - Math.PI <= TOLERANCE);
		assertTrue(e1.getComponent(Transform.class).getX() - 5 <= TOLERANCE);
		assertTrue(e1.getComponent(Transform.class).getY() <= TOLERANCE);
		
		assertTrue(e2.getComponent(Velocity.class).getSpeed() - 10 <= TOLERANCE);
		assertTrue(e2.getComponent(Velocity.class).getAngle() - Math.PI/4 <= TOLERANCE);
		assertTrue(e2.getComponent(Transform.class).getX() +25-5/Math.sqrt(2) <= TOLERANCE);
		assertTrue(e2.getComponent(Transform.class).getY() +25-5/Math.sqrt(2) <= TOLERANCE);
	}
	
	@Test
	public void testProcessEntitiesWithWall() {
		
	}

}
