package se.chalmers.avoidance.systems;

import java.util.List;
import java.util.Map;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import se.chalmers.avoidance.components.Spatial;
import se.chalmers.avoidance.components.Transform;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
/**
 * A system controlling the rendering of sprites.
 * 
 * @author Markus Ekström
 */
public class SpatialRenderSystem extends EntitySystem{
    @Mapper
    ComponentMapper<Transform> tm;
    @Mapper
    ComponentMapper<Spatial> sm;

	private List<Entity> entities;
	private Map<String, TextureRegion> regions;
	private VertexBufferObjectManager vbom;

	/**
	 * Constructs a <code>SpatialRenderSystem</code>. 
	 * Uses an aspect containing <code>Transform</code> and <code>Spatial</code> to match against entities.
	 * 
	 * @param regions A map containing <code>TextureRegion</code>s.
	 * @param vbom A <code>VertexBufferObjectManager</code>.
	 */
	@SuppressWarnings("unchecked")
	public SpatialRenderSystem(Map<String, TextureRegion> regions, VertexBufferObjectManager vbom) {
		super(Aspect.getAspectForAll(Transform.class, Spatial.class));
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
	
	/**
	 * Process an entity.
	 * @param e The entity to be processed.
	 */
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
