package se.chalmers.avoidance.core.systems;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import se.chalmers.avoidance.core.components.Sound;

import com.artemis.Entity;
import com.artemis.World;

public class SoundSystemTest {
	private static SoundSystem ss;
	private static World world;
	private static Entity e1;
	private static Entity e2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		world = new World();
		ss = new SoundSystem();
		world.setSystem(ss);
		world.initialize();
		e1 = world.createEntity();
		e2 = world.createEntity();
		e1.addComponent(new Sound("bounce"));
		e2.addComponent(new Sound("boom"));
		world.addEntity(e1);
		world.addEntity(e2);
	}

	@Test
	public void testProcessEntity() {
		e1.getComponent(Sound.class).setPlaying(true);
		world.process();
		assertFalse(e1.getComponent(Sound.class).isPlaying());
		assertFalse(e2.getComponent(Sound.class).isPlaying());
		
		e2.getComponent(Sound.class).setPlaying(true);
		world.process();
		assertFalse(e1.getComponent(Sound.class).isPlaying());
		assertFalse(e2.getComponent(Sound.class).isPlaying());
		
	}

}
