package se.chalmers.avoidance.states;

import java.util.HashMap;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import se.chalmers.avoidance.AccelerometerListener;
import se.chalmers.avoidance.EntityFactory;
import se.chalmers.avoidance.systems.PlayerControlSystem;
import se.chalmers.avoidance.systems.SpatialRenderSystem;
import se.chalmers.avoidance.systems.CollisionSystem;
import android.hardware.SensorManager;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
/**
 * The game state.
 * 
 * @author Markus Ekström
 */
public class GameState implements IState{

	private Scene scene;
	private World world;
	
	public GameState(SensorManager sensorManager, HashMap<String, TextureRegion> regions, VertexBufferObjectManager vbom) {
		initialize(sensorManager, regions, vbom);
	}
	
	private void initialize(SensorManager sensorManager, HashMap<String, TextureRegion> regions, VertexBufferObjectManager vbom) {
		scene = new Scene();
		scene.setBackground(new Background(1f, 0f, 0f));
		world = new World();
		
		world.setManager(new GroupManager());
		world.setManager(new TagManager());
		
		
		
		//Create and set systems here
		world.setSystem(new SpatialRenderSystem(regions, vbom, scene));
		world.setSystem(new CollisionSystem());
		world.setSystem(new PlayerControlSystem());
		
		//Initialize world.
		world.initialize();
		
		//Add listeners
		AccelerometerListener aL = new AccelerometerListener(sensorManager);
		aL.addPropertyChangeListener(world.getSystem(PlayerControlSystem.class));
		aL.startListening();
		
		//Initialize entities
		world.addEntity(EntityFactory.createPlayer(world));
		world.addEntity(EntityFactory.createWall(world));
	}
	
	/**
	 * Updates the state.
	 * Invoked by the StateManager, do not call manually.
	 * @param tpf Time since last frame.
	 */
	public void update(float tpf) {
		world.setDelta(tpf);
		world.process();
	}

	/**
	 * Returns the scene connected to the state.
	 * @return The state's scene.
	 */
	public Scene getScene() {
		return scene;
	}
}
