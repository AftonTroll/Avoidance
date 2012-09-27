package se.chalmers.avoidance.systems;

import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;
import se.chalmers.avoidance.util.Utils;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;

public class PlayerControlSystemTest {

	private static final float TOLERANCE = 0.0001f;
	private Entity player;
	private final PlayerControlSystem pcs = new PlayerControlSystem();
	private final World world = new World();
	private final TagManager tagManager = new TagManager();
	private final float[] accelerationX = {-5, 5};
	private final float[] accelerationY = {0, 3};
	private final float[] expectedSpeed = {5, 3};
	private final float[] expectedAngle = {(float) Math.PI, (float) Math.PI/2};
	private final float[] expectedX = {-2.5f, -5};
	private final float[] expectedY = {0, 1.5f};
	
	
	@Before
	public void setUp() {
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
			
			pcs.propertyChange(new PropertyChangeEvent(this, "AccelerometerX",null,accelerationX[i]));
			pcs.propertyChange(new PropertyChangeEvent(this, "AccelerometerY",null,accelerationY[i]));
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
			pcs.propertyChange(new PropertyChangeEvent(this, "AccelerometerX",null,accelerationX[i]));
			pcs.propertyChange(new PropertyChangeEvent(this, "AccelerometerY",null,accelerationY[i]));
			pcs.processEntities(null);
			pcs.processEntities(null);
			
			assertTrue(Math.abs(velocity.getSpeed()-expectedSpeed[i]) <= TOLERANCE);
			assertTrue(Math.abs(Utils.simplifyAngle(velocity.getAngle())-expectedAngle[i]) <= TOLERANCE);
			assertTrue(Math.abs(transform.getX()-expectedX[i]) <= TOLERANCE);
			assertTrue(Math.abs(transform.getY()-expectedY[i]) <= TOLERANCE);
		}
	}
}

