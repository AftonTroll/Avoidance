package se.chalmers.avoidance.components;

import se.chalmers.avoidance.util.Utils;

import com.artemis.Component;

public class Velocity extends Component {
	private float speed;
	private float angle;
	
	public Velocity() {
		this(0f, 0f);
	}
	
	public Velocity(float speed) {
		this(speed, 0f);
	}
	
	public Velocity(float speed, float angle) {
		setSpeed(speed);
		setAngle(angle);
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void setAngle(float angle) {
		this.angle = Utils.simplifyAngle(angle);
	}
	
	public void addSpeed(float speed) {
		this.speed += speed;
	}
	
	public void addAngle(float angle) {
		this.angle = Utils.simplifyAngle(this.angle + angle);
	}
	
	
}
