package se.chalmers.avoidance.core.components;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class FrictionTest {
	private static Friction f1, f2, f3;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		f1 = new Friction(0.1f);
		f2 = new Friction(0.9f);
		f3 = new Friction(3f);
	}

	@Test
	public void testGetFriction() {
		assertTrue(f1.getFriction() == 0.1f);
		assertTrue(f2.getFriction() == 0.9f);
		assertTrue(f3.getFriction() == 3f);
	}

}
