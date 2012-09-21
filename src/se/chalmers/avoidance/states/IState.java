package se.chalmers.avoidance.states;

import org.andengine.entity.scene.Scene;

public interface IState {
	
	public void update(float tpf);
	
	public Scene getScene();
	
}
