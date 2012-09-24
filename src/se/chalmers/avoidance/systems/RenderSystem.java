package se.chalmers.avoidance.systems;

import java.util.HashMap;
import java.util.List;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import se.chalmers.avoidance.components.Spatial;
import se.chalmers.avoidance.components.SpatialForm;
import se.chalmers.avoidance.components.Transform;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;

public class RenderSystem extends EntitySystem{
    @Mapper
    ComponentMapper<Transform> tm;
    @Mapper
    ComponentMapper<Spatial> sm;

	private List<Entity> entities;
	private HashMap<String, TextureRegion> regions;
	private VertexBufferObjectManager vbom;


	
	@SuppressWarnings("unchecked")
	public RenderSystem(HashMap<String, TextureRegion> regions, VertexBufferObjectManager vbom) {
		super(Aspect.getAspectForAll(Transform.class, SpatialForm.class));
		this.regions = regions;
		this.vbom = vbom;
	}

	@Override
	protected void initialize() {
		
	}
	
	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> arg0) {
        for(int i = 0; entities.size() > i; i++) {
            process(entities.get(i));
        }
	}
	
	protected void process(Entity e) {
		Spatial spatial = sm.get(e);
        Transform tf = tm.get(e);
        spatial.getSprite().setPosition(tf.getX(), tf.getY());
	}
	
    @Override
    protected void inserted(Entity e) {
    	Spatial spatial = sm.get(e);
		Transform tf = tm.get(e);
		spatial.setSprite(new Sprite(tf.getX(), tf.getY(), regions.get(spatial.getName()), vbom));
        entities.add(e);
    }

    @Override
    protected void removed(Entity e) {
        entities.remove(e);
    }
}
