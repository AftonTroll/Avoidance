package se.chalmers.avoidance.components;

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
	 * Constructs a <code>Velocity</code> component with zero speed
	 * and an angle of 0 radians.
	 */
	public Velocity() {
		this(0f, 0f);
	}
	
	/** 
	 * Constructs a <code>Velocity</code> component with a speed given
	 * by the argument with the same name and an angle of 0 radians.
	 * 
	 * @param speed the speed
	 */
	public Velocity(float speed) {
		this(speed, 0f);
	}
	
	/**
	 * Constructs a <code>Velocity</code> component with the speed and
	 * angle, given by the arguments with the same name.
	 * 
	 * @param speed the speed
	 * @param angle the angle
	 */
	public Velocity(float speed, float angle) {
		setSpeed(speed);
		setAngle(angle);
	}
	
	/**
	 * Returns the components speed.
	 * @return the speed.
	 */
	public float getSpeed() {
		return speed;
	}
	
	/**
	 * Returns the components angle.
	 * @return the angle.
	 */
	public float getAngle() {
		return angle;
	}
	
	/**
	 * Sets the speed of this component.
	 * 
	 * @param speed the speed
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
		if (this.speed < 0) {
			this.speed = Math.abs(this.speed);
			setAngle(Utils.reverseAngle(this.angle));
		}
	}
	
	/**
	 * Sets the angle of this component.
	 * 
	 * @param angle the angle
	 */
	public void setAngle(float angle) {
		this.angle = Utils.simplifyAngle(angle);
	}
	
	/**
	 * Adds the <code>speed</code> given in the argument of the same name, 
	 * to the speed of this component. 
	 * 
	 * @param speed the speed to add
	 */
	public void addSpeed(float speed) {
		setSpeed(this.speed + speed);
	}
	
	/**
	 * Adds the <code>angle</code> given in the argument of the same name, 
	 * to the angle of this component. 
	 * 
	 * @param angle the angle to add
	 */
	public void addAngle(float angle) {
		setAngle(this.angle + angle);
	}
	
	
}
