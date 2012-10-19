/* 
 * Copyright (c) 2012 Florian Minges
 * 
 * Parts of this file are derived from a menu example, written by Nicholas Gramlich, which
 * is released under the GNU Lesser GPL. It can be found under the following link:
 * http://code.google.com/p/andengineexamples/source/browse/src/org/anddev/andengine/
 * 		examples/MenuExample.java
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

package se.chalmers.avoidance.core.states;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import se.chalmers.avoidance.constants.EventMessageConstants;
import se.chalmers.avoidance.util.ScreenResolution;
import android.opengl.GLES20;

/**
 * Class containing information and data about the menu state.
 * 
 * @author Florian Minges
 */
public class MenuState implements IState, IOnMenuItemClickListener {

    private static final int MENU_START = 0;
    private static final int MENU_HIGHSCORES = MENU_START + 1;
    private static final int MENU_CREDITS = MENU_START + 2;
    private static final int MENU_QUIT = MENU_START + 3;

    private PropertyChangeSupport pcs;
	private MenuScene menuScene;
	
    /**
     * Constructs a new <code>MenuState</code>. <p>
     * 
     * @param camera the game engines <code>Camera</code>
     * @param regions a <code>HashMap</code> containing loaded textures/regions
	 * @param vbom the game engines <code>VertexBufferObjectManager</code>
     */
	public MenuState(Camera camera, HashMap<String, TextureRegion> regions, 
			VertexBufferObjectManager vbom) {
		initialize(camera, regions, vbom);
		pcs = new PropertyChangeSupport(this);
	}
	
    /**
     * Initializes the menu scene.
     * 
     * @param camera the game engines <code>Camera</code>
     * @param regions a <code>HashMap</code> containing loaded textures/regions
	 * @param vbom the game engines <code>VertexBufferObjectManager</code>
     */
	private void initialize(Camera camera, HashMap<String, TextureRegion> regions, 
			VertexBufferObjectManager vbom) {
		menuScene = new MenuScene();
		menuScene.setCamera(camera);
		menuScene.setBackground(new Background(0f, 0f, 0f));
		
		//center the scene
		float xPos = ScreenResolution.getWidthResolution() / 2 - 250;
		float yPos = ScreenResolution.getHeightResolution() / 2 - 300;
		
		//create menu items
		final SpriteMenuItem startMenuItem = new SpriteMenuItem(MENU_START,
				regions.get("menu_start.png"), vbom);
		startMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.menuScene.addMenuItem(startMenuItem);
		startMenuItem.setPosition(xPos, yPos);

		final SpriteMenuItem highscoreMenuItem = new SpriteMenuItem(MENU_HIGHSCORES,
				regions.get("menu_highscore.png"), vbom);
		highscoreMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.menuScene.addMenuItem(highscoreMenuItem);
		highscoreMenuItem.setPosition(xPos, yPos + 150);

		
		final SpriteMenuItem creditsMenuItem = new SpriteMenuItem(MENU_CREDITS,
				regions.get("menu_credits.png"), vbom);
		creditsMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.menuScene.addMenuItem(creditsMenuItem);
		creditsMenuItem.setPosition(xPos, yPos + 300);

		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT,
				regions.get("menu_quit.png"), vbom);
		quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.menuScene.addMenuItem(quitMenuItem);
		quitMenuItem.setPosition(xPos, yPos + 450);
		
		final Sprite backgroundSprite = new Sprite(0, 0, 1024, 768, regions.get("background.jpg"), 
				vbom);
		this.menuScene.setBackground(new SpriteBackground(backgroundSprite));

//		 this.menuScene.buildAnimations(); <- does not work
		this.menuScene.setBackgroundEnabled(true);
		this.menuScene.setOnMenuItemClickListener(this);

	}
	
	public void update(float tpf) {
		
	}

	/**
	 * Returns the menu scene, or null if it is not instantiated yet.
	 * @return the menu scene
	 */
	public Scene getScene() {
		return menuScene;
	}
	

	/**
	 * {@inheritDoc}
	 */
	public boolean onMenuItemClicked(final MenuScene pMenuScene,
			final IMenuItem pMenuItem, final float pMenuItemLocalX,
			final float pMenuItemLocalY) {
		//TODO Use constants instead of hard-coded strings
		switch (pMenuItem.getID()) {
		case MENU_START:
			pcs.firePropertyChange(EventMessageConstants.CHANGE_STATE, StateID.Menu, StateID.Game);
			return true;
		case MENU_HIGHSCORES:
			pcs.firePropertyChange(EventMessageConstants.CHANGE_STATE, StateID.Menu, 
					StateID.Highscore);
			return true;
		case MENU_CREDITS:
			pcs.firePropertyChange(EventMessageConstants.CHANGE_STATE, StateID.Menu, 
					StateID.Credits);
			return true;
		case MENU_QUIT:
			/* End Activity. */
			pcs.firePropertyChange(EventMessageConstants.QUIT_GAME, null, true);
			return true;
		default:
			return false;
		}
	}

	/**
	 * Adds a listener to this state.
	 * @param pcl the listener to add
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}

	/**
	 * Removes a listener from this state.
	 * @param pcl the listener to remove
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
	}
}
