package se.chalmers.avoidance.components;

import com.artemis.Component;

/**
 * Component containing information about size.
 * 
 * @author Florian Minges
 *
 */
public class Size extends Component {
	
	/** The width of this component. */
	private float width;
	
	/** The height of this component. */
	private float height;
	
	
	/**
	 * Constructs a new <code>Size</code> component, whose width and height
	 * are both set to one.
	 */
	public Size() {
		this(1, 1);
	}
	
	/**
	 * Constructs a new <code>Size</code> component, whose width and height
     * are specified by the arguments of the same name.
     * 
	 * @param width the width 
	 * @param height the height 
	 */
	public Size(float width, float height) {
		setSize(width, height);
	}
	
	/**
	 * Returns the width.
	 * @return the width 
	 */
	public float getWidth() {
		return this.width;
	}
	
	/**
	 * Returns the height.
	 * @return the height 
	 */
	public float getHeight() {
		return this.height;
	}
	
	/**
	 * Sets the width and height, as specified by the arguments of the same name.
	 * 
	 * @param width the width 
	 * @param height the height 
	 */
	public void setSize(float width, float height) {
		setWidth(width);
		setHeight(height);
	}
	
	/**
	 * Sets the width as specified by the argument of the same name.
	 * 
	 * @param width the width 
	 */
	public void setWidth(float width) {
		this.width = width;
	}
	
	
	/**
	 * Sets the height as specified by the argument of the same name.
	 * 
	 * @param height the height 
	 */
	public void setHeight(float height) {
		this.height = height;
	}
	
	/**
	 * Adds the <code>width</code> given in the argument of the same name, to the
	 * width of this component. 
	 * 
	 * @param width the amount to add to the width
	 */
	public void addWidth(float width) {
		this.width += width;
	}
	
	/**
	 * Adds the <code>height</code> given in the argument of the same name, to the
	 * height of this component. 
	 * 
	 * @param height the amount to add to the height
	 */
	public void addHeight(float height) {
		this.height += height;
	}

}
