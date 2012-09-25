package se.chalmers.avoidance.systems;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;
import se.chalmers.avoidance.systems.PlayerControlSystem;

import com.artemis.Entity;
import com.artemis.World;

public class PlayerControlSystemTest {

	private Entity player;
	private PlayerControlSystem pcs;
	private World world;
	
	@Before
	public void setUp() {
		world = new World();
		world.setDelta(1);
		player = world.createEntity();
		player.addComponent(new Transform());
		player.addComponent(new Velocity());
		
		pcs = new PlayerControlSystem(player.getId());
		world.setSystem(pcs);
		world.initialize();
	}

	@Test
	public void testProcessEntity() {
		Velocity velocity = player.getComponent(Velocity.class);
		Transform transform = player.getComponent(Transform.class);
		
		pcs.setSensorValues(-5, 0);
		world.process();
		
		
		assertTrue(Math.abs(velocity.getSpeed()-5) <= 0.001);
		assertTrue(Math.abs(velocity.getAngle()-Math.PI) <= 0.001);
		assertTrue(Math.abs(transform.getX()+5) <= 0.001);
		assertTrue(Math.abs(transform.getY()-0) <= 0.001);
		
		
		pcs.setSensorValues(5, 3);
		world.process();
		
		assertTrue(Math.abs(velocity.getSpeed()-3) <= 0.001);
		assertTrue(Math.abs(velocity.getAngle()-Math.PI/2) <= 0.001);
		assertTrue(Math.abs(transform.getX()+5) <= 0.001);
		assertTrue(Math.abs(transform.getY()-3) <= 0.001);
		
		
	}
}

