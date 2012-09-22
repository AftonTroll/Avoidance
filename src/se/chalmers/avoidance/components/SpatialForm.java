package se.chalmers.avoidance.components;

import com.artemis.Component;

/**
 * Component containing information about visibility and the type of spatial.
 * 
 * @author Florian Minges
 */
public class SpatialForm extends Component {
	
	/** The spatial type. */
	private String form;
	
	/** The visibility status. */
	private boolean visible;
	
	
	/**
	 * Constructs a <code>SpatialForm</code> component with the visibility
	 * status <code>true</code> and whose form is specified by the argument
	 * of the same name.
	 * 
	 * @param form the spatial form 
	 */
	public SpatialForm(String form) {
		this(form, true);
	}
	
	/**
	 * Constructs a <code>SpatialForm</code> component whose form and 
	 * visibility status are specified by the arguments.
	 * 
	 * @param form the spatial form
	 * @param visible the visibility status 
	 */
	public SpatialForm(String form, boolean visible) {
		setForm(form);
		setVisible(visible);
	}
	
	/**
	 * Returns the spatial form of this component.
	 * @return the spatial form.
	 */
	public String getForm() {
		return form;
	}
	
	/**
	 * Returns the visibility status of this component.
	 * @return the visibility status.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets the spatial form of this component.
	 * 
	 * @param form the spatial form 
	 */
	public void setForm(String form) {
		this.form = form;
	}

	/**
	 * Sets the visibility status of this component.
	 * 
	 * @param visible the visibility status
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
