package se.chalmers.avoidance.systems;

import java.util.HashMap;
import java.util.List;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import se.chalmers.avoidance.components.SpatialForm;
import se.chalmers.avoidance.components.Transform;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;

public class RenderSystem extends EntitySystem{
    @Mapper
    ComponentMapper<Transform> tm;
    @Mapper
    ComponentMapper<SpatialForm> sm;

	private List<Entity> sortedEntities;
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
        for(int i = 0; sortedEntities.size() > i; i++) {
            process(sortedEntities.get(i));
        }
	}
	
	protected void process(Entity e) {
		
	}
	
	private Sprite createSprite() {
		return null;
	}
	
    @Override
    protected void inserted(Entity e) {
        sortedEntities.add(e);
        
//        Collections.sort(sortedEntities, new Comparator<Entity>() {
//        	@Override
////            public int compare(Entity e1, Entity e2) {
////                    Sprite s1 = sm.get(e1);
////                    Sprite s2 = sm.get(e2);
////                    return s1.layer.compareTo(s2.layer);
//            }
//        });
//
    }

    @Override
    protected void removed(Entity e) {
            sortedEntities.remove(e);
    }


}
