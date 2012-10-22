package se.chalmers.avoidance.core.components;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SoundTest {
	private Sound sound;
	
	@Before
	public void setUp() {
		sound = new Sound("bounce");
	}

	@Test
	public void testConstructor(){
		assertTrue(sound.getName().equals("bounce"));
		assertFalse(sound.isPlaying());
	}
	
	@Test
	public void testName() {
		sound.setName("bang");
		assertTrue(sound.getName().equals("bang"));
	}

	@Test
	public void testPlaying() {
		sound.setPlaying(true);
		assertTrue(sound.isPlaying());
		sound.setPlaying(false);
		assertFalse(sound.isPlaying());
	}

}
