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
		this.width = width >= 0 ? width : 0;
	}
	
	
	/**
	 * Sets the height as specified by the argument of the same name.
	 * 
	 * @param height the height 
	 */
	public void setHeight(float height) {
		this.height = height >= 0 ? height : 0;
	}
	
	/**
	 * Adds the <code>width</code> given in the argument of the same name, to the
	 * width of this component. 
	 * 
	 * @param width the amount to add to the width
	 */
	public void addWidth(float width) {
		setWidth(this.width + width);
	}
	
	/**
	 * Adds the <code>height</code> given in the argument of the same name, to the
	 * height of this component. 
	 * 
	 * @param height the amount to add to the height
	 */
	public void addHeight(float height) {
		setHeight(this.height + height);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		//Eclipse auto-generated
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(height);
		result = prime * result + Float.floatToIntBits(width);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		//Eclipse auto-generated
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Size other = (Size) obj;
		if (Float.floatToIntBits(height) != Float.floatToIntBits(other.height))
			return false;
		if (Float.floatToIntBits(width) != Float.floatToIntBits(other.width))
			return false;
		return true;
	}

}
