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
 * Component containing information about position and direction.
 * 
 * @author Florian Minges
 */
public class Transform extends Component {

	/** The x-coordinate of the position. */
	private float x;
	
	/** The y-coordinate of the position. */
	private float y;
	
	
	/** 
	 * Represents a direction measured in radians.<p>
	 * 
	 * 0				=>	right <br>
	 * pi / 2			=> 	up <br>
	 * pi				=>	left <br>
	 * (3 * pi) / 2		=>	down
	 * 
	 */
	private float direction;
	
	
	
	/**
	 * Creates a Transform component with default position (0, 0)
	 * and default direction 0 (right). 
	 */
	public Transform() {
		this(0f, 0f, 0f);
	}
	
	/**
	 * Creates a Transform component with default direction 0 (right).
	 * 
	 * @param x the x-coordinate 
	 * @param y the y-coordinate 
	 */
	public Transform(float x, float y) {
		this(x, y, 0);
	}
	
	
	/**
	 * Creates a Transform component with the specified position and direction.
	 * 
	 * @param x the x-coordinate 
	 * @param y the y-coordinate 
	 * @param direction the {@link #direction direction} 
	 */
	public Transform(float x, float y, float direction) {
		super();
		setPosition(x, y);
		setDirection(direction);
	}
	
	/**
	 * Returns the x-coordinate.
	 * @return the x-coordinate.
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Returns the y-coordinate.
	 * @return the y-coordinate.
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Return the direction.
	 * @return the direction.
	 */
	public float getDirection() {
		return direction;
	}
	
	/**
	 * Sets the x and y-coordinate of this component.
	 * 
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public final void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}
	
	/**
	 * Sets the x-coordinate of this component.
	 * 
	 * @param x the x-coordinate
	 */
	public final void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Sets the y-coordinate of this component.
	 * 
	 * @param y the y-coordinate
	 */
	public final void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Moves the x-coordinate of this component by the given argument.
	 *  
	 * @param x the distance to move along the x-axis
	 */
	public void translateX(float x) {
		setX(this.x + x);
	}
	
	/**
	 * Moves the y-coordinate of this component by the given argument.
	 *  
	 * @param y the distance to move along the y-axis
	 */
	public void translateY(float y) {
		setY(this.y + y);
	}
	
	/**
	 * Sets the direction of this component.
	 * 
	 * @param direction the direction
	 */
	public final void setDirection(float direction) {
		this.direction = Utils.simplifyAngle(direction);
	}
	
	/**
	 * Rotates the direction of this component by the given argument.
	 * 
	 * @param direction the degree to rotate in radians
	 */
	public void translateDirection(float direction) {
		setDirection(this.direction + direction);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		//Eclipse auto-generated
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(direction);
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		//Eclipse auto-generated
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Transform other = (Transform) obj;
		if (Float.floatToIntBits(direction) != Float.floatToIntBits(other.direction)) {
			return false;
		}
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x)) {
			return false;
		}
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y)) {
			return false;
		}
		return true;
	}
	
}
