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

public class HudRenderSystem extends EntityProcessingSystem{
	
	private Font font;
	private Text score;
	private Scene scene;
	private VertexBufferObjectManager vbom;
	private ComponentMapper<Time> timeMapper;
	
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
		this.score = new Text(100, 160, this.font, "Score :", "Score: XXXXX".length(), vbom);
		scene.attachChild(score);
	}
	
	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void process(Entity entity) {
		Time time = timeMapper.get(entity);
		time.updateTime(world.getDelta());
		score.setText("Score :"+Math.round(time.getTime()));
	}

}
