package se.chalmers.avoidance.core.systems;

import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;

import se.chalmers.avoidance.core.components.Buff;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;
import se.chalmers.avoidance.core.components.Buff.BuffType;
import se.chalmers.avoidance.util.Utils;

public class PowerUpCollisionHandlerTest {
	private Entity player;
	private final PlayerControlSystem pcs = new PlayerControlSystem();
	private final World world = new World();
	private final TagManager tagManager = new TagManager();
	private final float[] accelerationX = {-5, 4.5f};
	private final float[] accelerationY = {0, 20};
	private final float[] expectedSpeed = {4.5f, 18};
	private final float[] expectedAngle = {(float) Math.PI, (float) Math.PI/2};
	private final float[] expectedX = {-2.25f, -4.5f};
	private final float[] expectedY = {0, 9};
	
	
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
	public void testHandleCollision() {
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
		
	}
}
