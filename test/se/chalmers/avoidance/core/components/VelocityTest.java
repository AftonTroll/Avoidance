/* 
 * Copyright (c) 2012 Florian Minges
 * 
 * This file is part of Avoidance.
 * 
 * Avoidance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Avoidance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Avoidance.  If not, see <http://www.gnu.org/licenses/>. 
 *  
 */

package se.chalmers.avoidance.core.components;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.chalmers.avoidance.util.Utils;

public class VelocityTest extends FloatTest {

	private static float s1, s2;
	private static float a1, a2;

	private Velocity mVelocity;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		s1 = 4f;
		s2 = 10f;
		a1 = (float) (Math.PI / 2);
		a2 = (float) Math.PI;
	}

	@Before
	public void setUp() throws Exception {
		this.mVelocity = new Velocity(s1, a1);
	}

	@Test
	public void testVelocity() {
		Velocity velocity = new Velocity();
		assertTrue(velocity != null);
		assertFloatEquals(velocity.getSpeed() , 0);
		assertFloatEquals(velocity.getAngle() , 0);
	}

	@Test
	public void testVelocityFloat() {
		Velocity velocity = new Velocity(s2);
		assertTrue(velocity != null);
		assertFloatEquals(velocity.getSpeed() , s2);
		assertFloatEquals(velocity.getAngle() , 0);
	}

	@Test
	public void testVelocityFloatFloat() {
		Velocity velocity = new Velocity(s2, a2);
		assertTrue(velocity != null);
		assertFloatEquals(velocity.getSpeed() , s2);
		assertFloatEquals((velocity.getAngle() - a2) % (float) (2 * Math.PI));
	}

	@Test
	public void testSpeedGetterAndSetter() {
		// positive speed
		mVelocity.setSpeed(s2);
		assertFloatEquals(mVelocity.getSpeed() , s2);
		if (a1 != s2)
			assertFloatEquals(mVelocity.getAngle() , a1);
		
		// 0 speed
		mVelocity.setSpeed(0);
		assertFloatEquals(mVelocity.getSpeed() , 0);
		assertFloatEquals(mVelocity.getAngle() , a1);

		// negative-speed
		mVelocity.setSpeed(-1);
		assertFloatEquals(mVelocity.getSpeed() , Math.abs(-1));
		assertFloatEquals(mVelocity.getAngle() , a1 + (float)Math.PI);
	}

	@Test
	public void testAngleGetterAndSetter() {
		// positive angle
		mVelocity.setAngle(a2);
		assertFloatEquals(mVelocity.getAngle() , a2);
		if (s1 != a2)
			assertFloatEquals(mVelocity.getSpeed() , s1);

		// 0 angle
		mVelocity.setAngle(0);
		assertFloatEquals(mVelocity.getAngle() , 0);
		assertFloatEquals(mVelocity.getSpeed() , s1);
		
		// negative angle
		mVelocity.setAngle(-1);
		assertFloatEquals(mVelocity.getAngle() , -1 + (float) (2 * Math.PI));
		assertFloatEquals(mVelocity.getSpeed() , s1);

		
	}

	@Test
	public void testAddSpeed() {

		//-> add positive
		mVelocity.addSpeed(s2);
		assertFloatEquals(mVelocity.getSpeed() , (s1 + s2));
		assertFloatEquals(mVelocity.getAngle() , a1); // angle unaffected

		// -> add negative
		float special = -1;
		float speed = mVelocity.getSpeed() + special;
		mVelocity.addSpeed(special);
		assertFloatEquals(mVelocity.getSpeed() , speed); // still positive speed
		assertFloatEquals(mVelocity.getAngle() , a1);

		// -> goes towards 0
		mVelocity.addSpeed(-mVelocity.getSpeed());
		assertFloatEquals(mVelocity.getSpeed() , 0);
		assertFloatEquals(mVelocity.getAngle() , a1);

		// -> goes below 0
		mVelocity.addSpeed(special);
		assertFloatEquals(mVelocity.getSpeed() , Math.abs(special));
		assertFloatEquals(mVelocity.getAngle() , Utils.reverseAngle(a1));

	}

	@Test
	public void testAddAngle() {
		assertFloatEquals(mVelocity.getAngle() , a1);

		// 360 degree turn -> still the same angle
		mVelocity.addAngle((float) (2 * Math.PI));
		assertFloatEquals(mVelocity.getAngle() - a1); 
		mVelocity.addAngle((float) (-2 * Math.PI));
		assertFloatEquals(mVelocity.getAngle() - a1);

		// add angle of 0 radian
		mVelocity.addAngle(0);
		assertFloatEquals(mVelocity.getAngle() - a1);

		// add positive angle
		mVelocity.addAngle(1);
		assertFloatEquals(mVelocity.getAngle() - a1 - 1);

		// add negative angle
		mVelocity.addAngle(-1);
		assertFloatEquals(mVelocity.getAngle() - a1);

	}

	@Test
	public void testEqualsAndHashCode() {
		Velocity oVelocity = new Velocity(s1, a1);
		assertTrue(mVelocity.equals(oVelocity));
		assertTrue(mVelocity.hashCode() == oVelocity.hashCode());

		oVelocity.setSpeed(a1);
		oVelocity.setAngle(s1);
		assertTrue(!mVelocity.equals(oVelocity));
	}

}
