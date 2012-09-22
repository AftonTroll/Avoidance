package se.chalmers.avoidance.components;

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
	private double rotation;
	
	
	
	/**
	 * Creates a Transform component with default position (0, 0)
	 * and default rotation 0 (right). 
	 */
	public Transform() {
		this(0f, 0f, 0);
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
	public Transform(float x, float y, double rotation) {
		setPosition(x, y);
		setRotation(rotation);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public double getRotation() {
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
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
		simplifyRotation();
	}
	
	public void addRotation(float rotation) {
		this.rotation += rotation;
		simplifyRotation();
	}
	
	/**
	 *  Converts the rotation so its' value is in the interval
	 *  0  <=  rotation  <  (2 * PI)
	 */
	private void simplifyRotation() {
		this.rotation = this.rotation % (2 * Math.PI);
	}
	
}
