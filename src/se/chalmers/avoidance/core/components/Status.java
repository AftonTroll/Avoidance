package se.chalmers.avoidance.core.components;

import com.artemis.Component;

public class Status extends Component {
	private boolean inTheAir = false;
	
	public void setInTheAir(boolean inTheAir) {
		this.inTheAir = inTheAir;
	}
	
	public boolean isInTheAir() {
		return inTheAir;
	}
}
