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

import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.avoidance.core.components.Jump;
import se.chalmers.avoidance.core.components.Friction;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;
import se.chalmers.avoidance.util.Utils;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;

public class PlayerControlSystemTest {

	private static final float TOLERANCE = 0.0001f;
	private static final float friction = 0.7f;
	private Entity player;
	private final PlayerControlSystem pcs = new PlayerControlSystem();
	private final World world = new World();
	private final TagManager tagManager = new TagManager();
	private final float[] accelerationX = {-5f/20, 5*friction/20};
	private final float[] accelerationY = {0, 20f/20};
	private final float[] expectedSpeed = {5*friction, 20*friction};
	private final float[] expectedAngle = {(float) Math.PI, (float) Math.PI/2};
	private PropertyChangeSupport pcsup = new PropertyChangeSupport(this);
	private final float[] expectedX = {-5*friction/2, -5*friction};
	private final float[] expectedY = {0, 10*friction};
	
	
	@Before
	public void setUp() {
		world.setManager(tagManager);
		world.setSystem(pcs);
		
		player = world.createEntity();
		world.addEntity(player);
		player.addComponent(new Transform());
		player.addComponent(new Velocity());
		player.addComponent(new Jump());
		player.addComponent(new Friction(friction));
		tagManager.register("PLAYER", player);
		
		world.initialize();
		pcsup.addPropertyChangeListener(pcs);
	}

	@Test
	public void testProcessEntity() {
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
	
	@Test
	public void testHandleJump() {
		Jump jump = player.getComponent(Jump.class);
		world.setDelta(1);
		world.process();
		assertTrue(!jump.isInTheAir());
		pcsup.firePropertyChange("touch", null, null);
		world.process();
		assertTrue(jump.isInTheAir());
		world.process();
	}
}

