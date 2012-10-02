package se.chalmers.avoidance.states;

import java.beans.PropertyChangeListener;

import org.andengine.entity.scene.Scene;

public interface IState {
	
	public void update(float tpf);
	
	public Scene getScene();
	
	public void addPropertyChangeListener(PropertyChangeListener pcl);
	public void removePropertyChangeListener(PropertyChangeListener pcl);
}
