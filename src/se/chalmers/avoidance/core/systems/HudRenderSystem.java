package se.chalmers.avoidance.core.systems;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import se.chalmers.avoidance.core.components.Time;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

/**
 * System for handling rendering of the heads-up display * 
 * 
 * @author Jakob Svensson
 *
 */
public class HudRenderSystem extends EntityProcessingSystem{
	
	private Font font;
	private Text score;
	private Scene scene;
	private VertexBufferObjectManager vbom;
	private ComponentMapper<Time> timeMapper;
	
	/**
	 * Construct a new HudRenderSystem
	 * 
	 * @param scene The scene of the game
	 * @param vbom A VertexBufferObjectManager
	 * @param font The font of the score text
	 */
	public HudRenderSystem(Scene scene, VertexBufferObjectManager vbom, Font font) {
		super(Aspect.getAspectForAll(Time.class));
		this.font = font;	
		this.scene = scene;
		this.vbom = vbom;
	}
	
	/**
	 * This method is called when the system is initialized
	 */
	@Override
	protected void initialize(){
		timeMapper = world.getMapper(Time.class);
		this.score = new Text(30, 30, this.font, "Score :", "Score: XXXXX".length(), vbom);
		scene.attachChild(score);
		score.setZIndex(100);
	}
	
	/**
	 * Determines if the system should be processed or not
	 * 
	 * @return true if system should be processed, false if not	
	 */
	@Override
	protected boolean checkProcessing() {
		return true;
	}
	
	/**
	 * Processes entities
	 * 
	 * @param entity the entity to be processed
	 */
	@Override
	protected void process(Entity entity) {
		Time time = timeMapper.get(entity);
		time.updateTime(world.getDelta());
		score.setText("Score :"+Math.round(time.getTime()));
	}

}
