package se.chalmers.avoidance.states;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;

public class MenuState implements IState{

	private Scene scene;
	
	public MenuState() {
		initialize();
	}
	
	private void initialize() {
		scene = new Scene();
		scene.setBackground(new Background(0f, 0f, 0f));
	}
	
	public void update(float tpf) {
		
	}

	public Scene getScene() {
		return scene;
	}
}
