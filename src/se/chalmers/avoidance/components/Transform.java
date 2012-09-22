package se.chalmers.avoidance.components;

import se.chalmers.avoidance.util.Utils;

import com.artemis.Component;

/**
 * A transform component. Contains information about position and rotation.
 * 
 * @author Florian Minges
 */
public class Transform extends Component {

	/** The x-coordinate of this position. */
	private float x;
	
	/** The y-coordinate of this position. */
	private float y;
	
	
	/** 
	 * The rotation in radians. <p>
	 * 
	 * 0				=>	right <br>
	 * pi / 2			=> 	up <br>
	 * pi				=>	left <br>
	 * (3 * pi) / 2		=>	down
	 * 
	 */
	private float rotation;
	
	
	
	/**
	 * Creates a Transform component with default position (0, 0)
	 * and default rotation 0 (right). 
	 */
	public Transform() {
		this(0f, 0f, 0f);
	}
	
	/**
	 * Creates a Transform component with default rotation 0 (right).
	 * 
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 */
	public Transform(float x, float y) {
		this(x, y, 0);
	}
	
	
	/**
	 * Creates a Transform component with the specified position and rotation.
	 * 
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @param rotation The {@link #rotation rotation}.
	 */
	public Transform(float x, float y, float rotation) {
		setPosition(x, y);
		setRotation(rotation);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void addX(float x) {
		this.x += x;
	}
	
	public void addY(float y) {
		this.y += y;
	}
	
	public void setRotation(float rotation) {
		this.rotation = Utils.simplifyAngle(rotation);
	}
	
	public void addRotation(float rotation) {
		this.rotation = Utils.simplifyAngle(this.rotation + rotation);
	}
	
}
