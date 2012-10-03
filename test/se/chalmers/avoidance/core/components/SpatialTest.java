package se.chalmers.avoidance.core.components;

import static org.junit.Assert.assertTrue;

import org.andengine.entity.sprite.Sprite;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SpatialTest {
	private static String name;
	private static Sprite sprite;
	
	private Spatial spatial;
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		name = "test";
		sprite = new Sprite(0, 0, 0, 0, null, null, null, null);
	}
	
	@Before
	public void setUp() throws Exception {
		spatial = new Spatial(name);
	}
	
	@Test
	public void testSpatial() {
		assertTrue(spatial != null);
	}

	@Test
	public void testNameSetterAndGetter() {
		assertTrue(spatial.getName() == name);
		
		spatial.setName("testName");
		assertTrue(spatial.getName() == "testName");
	}
	
	@Test
	public void testSpriteSetterGetter() {
		spatial.setSprite(sprite);
		assertTrue(spatial.getSprite() == sprite);
	}
}
