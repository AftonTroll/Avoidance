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
import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;
import se.chalmers.avoidance.core.components.Buff.BuffType;
import se.chalmers.avoidance.util.Utils;

public class PowerUpCollisionHandlerTest {
	private Entity player;
	private Entity powerup;
	private final CollisionSystem cs = new CollisionSystem();
	private final World world = new World();
	private final TagManager tagManager = new TagManager();
	private final Buff buff = new Buff(BuffType.Speed, 1);
	
	
	@Before
	public void setUp() {
		world.setManager(tagManager);
		world.setSystem(cs);
		player = world.createEntity();
		player.addComponent(new Transform());
		player.addComponent(new Velocity());
		player.addComponent(new Size());
		tagManager.register("PLAYER", player);
		
		powerup = world.createEntity();
		powerup.addComponent(new Transform());
		powerup.addComponent(buff);
		powerup.addComponent(new Size());
		tagManager.register("POWERUP", powerup);
		
		cs.initialize();
	}

	@Test
	public void testHandleCollision() {
		Transform playerTransform = player.getComponent(Transform.class);
		Transform powerupTransform = powerup.getComponent(Transform.class);
		world.setDelta(1);
		
		playerTransform.setPosition(40, 40);
		powerupTransform.setPosition(30, 30);
		
		cs.processEntities(null);
		
		assertTrue(player.getComponent(Velocity.class).getSpeed() == 0);
		
		powerupTransform.setPosition(40, 40);
		
		cs.processEntities(null);
		
		assertTrue(player.getComponent(Velocity.class).getSpeed() == 1);
		assertTrue(powerup.getWorld() == null);
		
	}
}
