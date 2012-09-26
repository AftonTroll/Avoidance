package se.chalmers.avoidance.systems;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;
import se.chalmers.avoidance.util.Utils;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;

public class PlayerControlSystemTest {

	private final float TOLERANCE = 0.0001f;
	private Entity player;
	private PlayerControlSystem pcs = new PlayerControlSystem();
	private World world = new World();
	private TagManager tagManager = new TagManager();
	private float[] accelerationX = {-5, 5};
	private float[] accelerationY = {0, 3};
	private float[] expectedSpeed = {5, 3};
	private float[] expectedAngle = {(float) Math.PI, (float) Math.PI/2};
	private float[] expectedX = {-2.5f, -5};
	private float[] expectedY = {0, 1.5f};
	
	
	@Before
	public void setUp() {
		tagManager = new TagManager();
		world.setManager(tagManager);
		world.setSystem(pcs);
		
		player = world.createEntity();
		player.addComponent(new Transform());
		player.addComponent(new Velocity());
		tagManager.register("PLAYER", player);
		
		pcs.initialize();
	}

	@Test
	public void testProcessEntity() {
		Velocity velocity = player.getComponent(Velocity.class);
		Transform transform = player.getComponent(Transform.class);
		
		world.setDelta(1);
		for(int i = 0; i<accelerationX.length; i++){
			
			pcs.setSensorValues(accelerationX[i], accelerationY[i]);
			pcs.processEntities(null);
			
			
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
			pcs.processEntities(null);
			pcs.processEntities(null);
			
			assertTrue(Math.abs(velocity.getSpeed()-expectedSpeed[i]) <= TOLERANCE);
			assertTrue(Math.abs(Utils.simplifyAngle(velocity.getAngle())-expectedAngle[i]) <= TOLERANCE);
			assertTrue(Math.abs(transform.getX()-expectedX[i]) <= TOLERANCE);
			assertTrue(Math.abs(transform.getY()-expectedY[i]) <= TOLERANCE);
		}
	}
}

