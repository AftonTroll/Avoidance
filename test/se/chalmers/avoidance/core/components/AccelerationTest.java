package se.chalmers.avoidance.core.components;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class AccelerationTest {
	private static Acceleration a1, a2;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		a1 = new Acceleration(5);
		a2 = new Acceleration(30.5f);
	}

	@Test
	public void testConstructor() {
		assertTrue(a1.getAcceleration() == 5);
		assertTrue(a2.getAcceleration() == 30.5f);
	}

	@Test
	public void testSetGetAcceleration() {
		a1.setAcceleration(15.3f);
		assertTrue(a1.getAcceleration() == 15.3f);
		a2.setAcceleration(-3);
		assertTrue(a2.getAcceleration() == -3);
	}

}
