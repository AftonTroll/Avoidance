package se.chalmers.avoidance.components;

/**
 * Component containing information about the size.
 * 
 * @author Florian Minges
 *
 */
public class Size {
	private float width;
	private float height;
	
	public Size() {
		this(1, 1);
	}
	
	public Size(float width, float height) {
		setSize(width, height);
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public void setSize(float width, float height) {
		setWidth(width);
		setHeight(height);
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void addWidth(float width) {
		this.width += width;
	}
	
	public void addHeight(float height) {
		this.height += height;
	}

}
