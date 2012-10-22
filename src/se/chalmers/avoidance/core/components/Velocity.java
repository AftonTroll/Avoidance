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

import se.chalmers.avoidance.util.Utils;

import com.artemis.Component;

/**
 * Component containing information about speed and its angle.
 * 
 * @author Florian Minges
 */
public class Velocity extends Component {

	/** The speed. */
	private float speed;

	/** The angle of the speed. */
	private float angle;

	/**
	 * Constructs a <code>Velocity</code> component with zero speed and an angle
	 * of 0 radians.
	 */
	public Velocity() {
		this(0f, 0f);
	}

	/**
	 * Constructs a <code>Velocity</code> component with a speed given by the
	 * argument with the same name and an angle of 0 radians.
	 * 
	 * @param speed
	 *            the speed
	 */
	public Velocity(float speed) {
		this(speed, 0f);
	}

	/**
	 * Constructs a <code>Velocity</code> component with the speed and angle,
	 * given by the arguments with the same name.
	 * 
	 * @param speed
	 *            the speed
	 * @param angle
	 *            the angle
	 */
	public Velocity(float speed, float angle) {
		super();
		setAngle(angle);
		setSpeed(speed);
	}

	/**
	 * Returns the components speed.
	 * 
	 * @return the speed.
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * Returns the components angle.
	 * 
	 * @return the angle.
	 */
	public float getAngle() {
		return angle;
	}

	/**
	 * Sets the speed of this component.
	 * 
	 * @param speed
	 *            the speed
	 */
	public final void setSpeed(float speed) {
		this.speed = speed;
		if (this.speed < 0) {
			this.speed = Math.abs(this.speed);
			setAngle(Utils.reverseAngle(this.angle));
		}
	}

	/**
	 * Sets the angle of this component.
	 * 
	 * @param angle
	 *            the angle
	 */
	public final void setAngle(float angle) {
		this.angle = Utils.simplifyAngle(angle);
	}

	/**
	 * Adds the <code>speed</code> given in the argument of the same name, to
	 * the speed of this component.
	 * 
	 * @param speed
	 *            the speed to add
	 */
	public void addSpeed(float speed) {
		setSpeed(this.speed + speed);
	}

	/**
	 * Adds the <code>angle</code> given in the argument of the same name, to
	 * the angle of this component.
	 * 
	 * @param angle
	 *            the angle to add
	 */
	public void addAngle(float angle) {
		setAngle(this.angle + angle);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		// Eclipse auto-generated
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(angle);
		result = prime * result + Float.floatToIntBits(speed);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		// Eclipse auto-generated
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Velocity other = (Velocity) obj;
		if (Float.floatToIntBits(angle) != Float.floatToIntBits(other.angle)) {
			return false;
		}
		if (Float.floatToIntBits(speed) != Float.floatToIntBits(other.speed)) {
			return false;
		}
		return true;
	}

}
