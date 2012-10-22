/*
 * Copyright (c) 2012 Filip Brynfors
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Avoidance. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package se.chalmers.avoidance.core.systems;

import static org.junit.Assert.assertFalse;

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
	public static void setUpBeforeClass() {
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
