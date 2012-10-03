package se.chalmers.avoidance.core.components;

import com.artemis.Component;
/**
 * Component holding the current time
 * 
 * @author Jakob Svensson
 *
 */
public class Time extends Component {
	
	private float currentTime=0;
	
	/**
	 * Returns the current time
	 * 
	 * @return the current time in seconds 
	 */
	public float getTime(){
		return currentTime;
	}
	
	/**
	 * Updates the time by adding the current time/frame
	 * 
	 * @param tpf the current time/frame
	 */
	public void updateTime(float tpf){
		currentTime+=tpf;
	}
}
