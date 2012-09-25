package se.chalmers.avoidance.systems;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;

import com.artemis.Entity;
import com.artemis.World;

public class PlayerControlSystemTest {

	private final float TOLERANCE = 0.0001f;
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
		pcs.initialize();
	}

	@Test
	public void testProcessEntity() {
		Velocity velocity = player.getComponent(Velocity.class);
		Transform transform = player.getComponent(Transform.class);
		
		pcs.setSensorValues(-5, 0);
		pcs.process(player);
		
		
		assertTrue(Math.abs(velocity.getSpeed()-5) <= TOLERANCE);
		assertTrue(Math.abs(velocity.getAngle()-Math.PI) <= TOLERANCE);
		assertTrue(Math.abs(transform.getX()+5) <= TOLERANCE);
		assertTrue(Math.abs(transform.getY()-0) <= TOLERANCE);
		
		
		pcs.setSensorValues(5, 3);
		pcs.process(player);
		
		assertTrue(Math.abs(velocity.getSpeed()-3) <= TOLERANCE);
		assertTrue(Math.abs(velocity.getAngle()-Math.PI/2) <= TOLERANCE);
		assertTrue(Math.abs(transform.getX()+5) <= TOLERANCE);
		assertTrue(Math.abs(transform.getY()-3) <= TOLERANCE);
		
		world.setDelta(0.5f);
		pcs.setSensorValues(0, -6);
		pcs.process(player);
		
		//Check new position
	}
}

