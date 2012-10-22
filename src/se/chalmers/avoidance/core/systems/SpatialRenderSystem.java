/* 
 * Copyright (c) 2012 Markus Ekström
 * 
 * This file is based on an example acquired from http://gamadu.com/artemis/demos.html (Spaceship Warrior), which
 * can be found under the following link:
 * http://code.google.com/p/spaceship-warrior/source/browse/src/com/gamadu/spaceshipwarrior/systems/SpriteRenderSystem.java
 * 
 * This file is part of Avoidance.
 * 
 * Avoidance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Avoidance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Avoidance.  If not, see <http://www.gnu.org/licenses/>. 
 *  
 */

package se.chalmers.avoidance.core.systems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import se.chalmers.avoidance.core.components.Immortal;
import se.chalmers.avoidance.core.components.Jump;
import se.chalmers.avoidance.core.components.Spatial;
import se.chalmers.avoidance.core.components.Transform;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.utils.ImmutableBag;
/**
 * A system controlling the rendering of sprites.
 * 
 * @author Markus Ekström
 */
public class SpatialRenderSystem extends EntitySystem{
    @Mapper
    private ComponentMapper<Transform> tm;
    @Mapper
    private ComponentMapper<Spatial> sm;
    
    private TagManager tagManager;
	private List<Entity> entities;
	private Map<String, TextureRegion> regions;
	private VertexBufferObjectManager vbom;
	private Scene scene;
	private boolean playerImmortal = false;

	/**
	 * Constructs a <code>SpatialRenderSystem</code>. 
	 * Uses an aspect containing <code>Transform</code> and <code>Spatial</code> to match against entities.
	 * 
	 * @param regions A map containing <code>TextureRegion</code>s.
	 * @param vbom A <code>VertexBufferObjectManager</code>.
	 */
	@SuppressWarnings("unchecked")
	public SpatialRenderSystem(Map<String, TextureRegion> regions, VertexBufferObjectManager vbom, Scene scene) {
		super(Aspect.getAspectForAll(Transform.class, Spatial.class));
		this.regions = regions;
		this.vbom = vbom;
		this.scene = scene;
		entities = new ArrayList<Entity>();
	}

	@Override
	protected void initialize() {
		tagManager = world.getManager(TagManager.class);
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
        spatial.getSprite().setRotation((float) (Math.toDegrees(tf.getDirection())));
        
        if(e.getId() == world.getManager(TagManager.class).getEntity("PLAYER").getId()) {
        	handleJumpScaling(e);
        	handleImmortalSprite(e);
        }
	}
	
	/**
	 * Handles scaling of the sprite when the player has jumped.
	 * @param player The player entity.
	 */
	private void handleJumpScaling(Entity player) {
		if(player.getComponent(Jump.class).isInTheAir()) {
			player.getComponent(Spatial.class).getSprite().setScale(2);
		} else {
			player.getComponent(Spatial.class).getSprite().setScale(1);
		}
	}
	
	private void handleImmortalSprite(Entity player) {
	    if (player.getComponent(Immortal.class).isImmortal() && !playerImmortal) {
	        Transform tf = tm.get(player);
	        sm.get(player).getSprite().detachSelf();
	        sm.get(player).setSprite(new Sprite(tf.getX(), tf.getY(), regions.get("immortal_ball.png"), vbom));
	        scene.attachChild(sm.get(player).getSprite());
	        playerImmortal = true;
	    } else if (!player.getComponent(Immortal.class).isImmortal() && playerImmortal) {
	        Transform tf = tm.get(player);
            Spatial spatial = sm.get(player);
            spatial.getSprite().detachSelf();
            sm.get(player).setSprite(new Sprite(tf.getX(), tf.getY(), regions.get(spatial.getName()), vbom));
            scene.attachChild(spatial.getSprite());
            playerImmortal = false;
	    }
	}
	
    @Override
    protected void inserted(Entity e) {
    	Spatial spatial = sm.get(e);
		Transform tf = tm.get(e);
		spatial.setSprite(new Sprite(tf.getX(), tf.getY(), regions.get(spatial.getName()), vbom));
		scene.attachChild(spatial.getSprite());
		if(tagManager.getEntity("PLAYER").equals(e)){
			spatial.getSprite().setZIndex(50);
		}
		scene.sortChildren();
        entities.add(e);
    }

    @Override
    protected void removed(Entity e) {
    	e.getComponent(Spatial.class).getSprite().detachSelf();
        entities.remove(e);
    }
}
