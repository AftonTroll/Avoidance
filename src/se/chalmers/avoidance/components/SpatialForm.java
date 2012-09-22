package se.chalmers.avoidance.components;

import com.artemis.Component;

public class SpatialForm extends Component {
	private String form;
	private boolean visible;
	
	public SpatialForm() {
		this("", false);
	}
	
	public SpatialForm(String form) {
		this(form, true);
	}
	
	public SpatialForm(String form, boolean visible) {
		setForm(form);
		setVisible(visible);
	}
	
	public String getForm() {
		return form;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void appendToForm(String string) {
		form += string;
	}

}
