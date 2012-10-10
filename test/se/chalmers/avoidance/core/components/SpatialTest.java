/* 
 * Copyright (c) 2012 Markus Ekström
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
