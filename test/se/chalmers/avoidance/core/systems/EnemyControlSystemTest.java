package se.chalmers.avoidance.core.systems;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.chalmers.avoidance.core.components.Acceleration;
import se.chalmers.avoidance.core.components.Friction;
import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

public class EnemyControlSystemTest {

	private static final float TOLERANCE = 0.0001f;
	private static World world = new World();
	private static TagManager tagManager = new TagManager();
	private static GroupManager groupManager = new GroupManager();
	private static EnemyControlSystem ecs = new EnemyControlSystem();
	private static Entity player;
	private static Entity e1;
	private static Entity e2;

	@BeforeClass
	public static void setUpBeforeClass() {
		world.setManager(tagManager);
		world.setManager(groupManager);
		world.setSystem(ecs);
		
		player = world.createEntity();
		player.addComponent(new Transform());
		player.addComponent(new Size(32,32));
		tagManager.register("PLAYER", player);
		
		e1 = world.createEntity();
		e1.addComponent(new Transform());
		e1.addComponent(new Velocity());
		e1.addComponent(new Size(32,32));
		e1.addComponent(new Friction(0.7f));
		e1.addComponent(new Acceleration(10));
		
		e2 = world.createEntity();
		e2.addComponent(new Transform());
		e2.addComponent(new Velocity());
		e2.addComponent(new Size(32,32));
		e2.addComponent(new Friction(0.9f));
		e2.addComponent(new Acceleration(10));
		
		groupManager.add(e1, "ENEMIES");
		groupManager.add(e2, "ENEMIES");
		
		ecs.initialize();
		
	}
	
	@Before
	public void setUp() {
		e1.getComponent(Transform.class).setPosition(10, 0);
		e1.getComponent(Velocity.class).setAngle(0);
		e1.getComponent(Velocity.class).setSpeed(0);
		
		e2.getComponent(Transform.class).setPosition(25,25);
		e2.getComponent(Velocity.class).setAngle(0);
		e2.getComponent(Velocity.class).setSpeed(0);
		
		player.getComponent(Transform.class).setPosition(0 ,0);
	}

	@Test
	public void testProcessEntitiesWithoutWall() {
		world.setDelta(1);
		ecs.processEntities(null);
		
		assertTrue(Math.abs(e1.getComponent(Velocity.class).getSpeed() - 7) <= TOLERANCE);
		assertTrue(Math.abs(e1.getComponent(Velocity.class).getAngle() - Math.PI) <= TOLERANCE);
		assertTrue(Math.abs(e1.getComponent(Transform.class).getX() - (10-7/2f)) <= TOLERANCE);
		assertTrue(Math.abs(e1.getComponent(Transform.class).getY()) <= TOLERANCE);
		
		assertTrue(Math.abs(e2.getComponent(Velocity.class).getSpeed() - 9) <= TOLERANCE);
		assertTrue(Math.abs(e2.getComponent(Velocity.class).getAngle() - 5*Math.PI/4) <= TOLERANCE);
		assertTrue(Math.abs(e2.getComponent(Transform.class).getX() -(25-4.5/Math.sqrt(2))) 
				<= TOLERANCE);
		assertTrue(Math.abs(e2.getComponent(Transform.class).getY() -(25-4.5/Math.sqrt(2))) 
				<= TOLERANCE);
	}
}
