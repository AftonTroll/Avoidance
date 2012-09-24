package se.chalmers.avoidance.systems;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;

import se.chalmers.avoidance.components.SpatialForm;
import se.chalmers.avoidance.components.Transform;

import android.content.res.AssetManager;

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
	private TextureManager textureManager;
	private AssetManager assetManager;

	
	@SuppressWarnings("unchecked")
	public RenderSystem(TextureManager textureManager, AssetManager assetManager) {
		super(Aspect.getAspectForAll(Transform.class, SpatialForm.class));
		this.textureManager = textureManager;
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
	
	private Texture createTexture(Entity e) {
		try {
			Texture texture = new AssetBitmapTexture(textureManager, assetManager, sm.get(e).getForm());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
