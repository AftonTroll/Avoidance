package se.chalmers.avoidance.input;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import android.view.MotionEvent;


public class TouchListener {
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction(); 
        if(eventAction == MotionEvent.ACTION_DOWN) {}
        	pcs.firePropertyChange("touch", null, null);
        return true;
    }
    
    public void addListener(PropertyChangeListener pcl) {
    	pcs.addPropertyChangeListener(pcl);
    }
}
