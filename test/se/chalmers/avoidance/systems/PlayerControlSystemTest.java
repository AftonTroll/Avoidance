package se.chalmers.avoidance.systems;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;
import se.chalmers.avoidance.util.Utils;

import com.artemis.Entity;
import com.artemis.World;

public class PlayerControlSystemTest {

	private final float TOLERANCE = 0.0001f;
	private Entity player;
	private PlayerControlSystem pcs;
	private World world;
	private float[] accelerationX = {-5, 5};
	private float[] accelerationY = {0, 3};
	private float[] expectedSpeed = {5, 3};
	private float[] expectedAngle = {(float) Math.PI, (float) Math.PI/2};
	private float[] expectedX = {-2.5f, -5};
	private float[] expectedY = {0, 1.5f};
	
	
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
		
		for(int i = 0; i<accelerationX.length; i++){
			pcs.setSensorValues(accelerationX[i], accelerationY[i]);
			pcs.process(player);
			
			
			assertTrue(Math.abs(velocity.getSpeed()-expectedSpeed[i]) <= TOLERANCE);
			assertTrue(Math.abs(Utils.simplifyAngle(velocity.getAngle())-expectedAngle[i]) <= TOLERANCE);
			assertTrue(Math.abs(transform.getX()-expectedX[i]) <= TOLERANCE);
			assertTrue(Math.abs(transform.getY()-expectedY[i]) <= TOLERANCE);
		}
		
		//reset values
		velocity.setAngle(0);
		velocity.setSpeed(0);
		transform.setX(0);
		transform.setY(0);
		
		//Two updates with the half delta should result in the same values
		world.setDelta(0.5f);
		
		for(int i = 0; i<accelerationX.length; i++){
			pcs.setSensorValues(accelerationX[i], accelerationY[i]);
			pcs.process(player);
			pcs.process(player);
			
			assertTrue(Math.abs(velocity.getSpeed()-expectedSpeed[i]) <= TOLERANCE);
			assertTrue(Math.abs(Utils.simplifyAngle(velocity.getAngle())-expectedAngle[i]) <= TOLERANCE);
			assertTrue(Math.abs(transform.getX()-expectedX[i]) <= TOLERANCE);
			assertTrue(Math.abs(transform.getY()-expectedY[i]) <= TOLERANCE);
		}
	}
}

