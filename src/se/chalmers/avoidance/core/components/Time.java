package se.chalmers.avoidance.core.components;

import com.artemis.Component;

public class Time extends Component {
	
	private float currentTime=0;
	
	public float getTime(){
		return currentTime;
	}
	
	public void updateTime(float tpf){
		currentTime+=tpf;
	}
}
