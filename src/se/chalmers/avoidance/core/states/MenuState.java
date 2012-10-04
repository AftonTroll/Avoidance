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

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import se.chalmers.avoidance.constants.EventMessageConstants;
import se.chalmers.avoidance.util.ScreenResolution;

import android.opengl.GLES20;

/**
 * Class containing information and data about the menu state.
 * @author Florian
 *
 */
public class MenuState implements IState, IOnMenuItemClickListener {

    protected static final int MENU_START = 0;
    protected static final int MENU_HIGHSCORES = MENU_START + 1;
    protected static final int MENU_HELP = MENU_START + 2;
    protected static final int MENU_QUIT = MENU_START + 3;

    private BaseGameActivity baseGameActivity;
    private PropertyChangeSupport pcs;
	private MenuScene menuScene;
	
	private BitmapTextureAtlas bitmapTextureAtlas;
    private BitmapTextureAtlas menuTexture;
    protected TextureRegion menuStartTextureRegion;
    protected TextureRegion menuHighscoreTextureRegion;
    protected TextureRegion menuHelpTextureRegion;
    protected TextureRegion menuQuitTextureRegion;
	
    
	public MenuState(BaseGameActivity activity) {
		this.baseGameActivity = activity;
		pcs = new PropertyChangeSupport(this);
		onLoadResources();
		initialize();
	}
	
	/**
	 * Loads the required resources.
	 */
    public void onLoadResources() {
    	Engine engine = this.baseGameActivity.getEngine();
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
           
    	this.bitmapTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 256, 256, 
    			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    	engine.getTextureManager().loadTexture(this.bitmapTextureAtlas);

    	// create textures
    	this.menuTexture = new BitmapTextureAtlas(engine.getTextureManager(), 256, 256, 
    			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    	this.menuStartTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
    			this.menuTexture, this.baseGameActivity, "menu_start.png", 0, 0);
    	this.menuHighscoreTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
    			this.menuTexture, this.baseGameActivity, "menu_highscore.png", 0, 64);
    	this.menuHelpTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
    			this.menuTexture, this.baseGameActivity, "menu_help.png", 0, 128);
    	this.menuQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
    			this.menuTexture, this.baseGameActivity, "menu_quit.png", 0, 192);
    	engine.getTextureManager().loadTexture(this.menuTexture);
    }
	
    /**
     * Initializes the menu scene.
     */
	private void initialize() {
		menuScene = new MenuScene();
		menuScene.setBackground(new Background(0f, 0f, 0f));
		
		//center the scene
		this.menuScene.setX(ScreenResolution.getWidthResolution() / 2 - 100);
		this.menuScene.setY(ScreenResolution.getHeightResolution() / 2 - 150);

		//create menu items
		final SpriteMenuItem startMenuItem = new SpriteMenuItem(MENU_START,
				this.menuStartTextureRegion, this.baseGameActivity.getVertexBufferObjectManager());
		startMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.menuScene.addMenuItem(startMenuItem);
		startMenuItem.setPosition(0, 0);

		final SpriteMenuItem highscoreMenuItem = new SpriteMenuItem(MENU_HIGHSCORES,
				this.menuHighscoreTextureRegion, 
				this.baseGameActivity.getVertexBufferObjectManager());
		highscoreMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.menuScene.addMenuItem(highscoreMenuItem);
		highscoreMenuItem.setPosition(0, 75);

		
		final SpriteMenuItem helpMenuItem = new SpriteMenuItem(MENU_HELP,
				this.menuHelpTextureRegion, this.baseGameActivity.getVertexBufferObjectManager());
		helpMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.menuScene.addMenuItem(helpMenuItem);
		helpMenuItem.setPosition(0, 150);

		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT,
				this.menuQuitTextureRegion, this.baseGameActivity.getVertexBufferObjectManager());
		quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.menuScene.addMenuItem(quitMenuItem);
		quitMenuItem.setPosition(0, 225);

//		 this.menuScene.buildAnimations(); <- does not work
		this.menuScene.setBackgroundEnabled(true);
		this.menuScene.setOnMenuItemClickListener(this);
		this.menuScene.setCamera(this.baseGameActivity.getEngine().getCamera());

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
		case MENU_HELP:
			pcs.firePropertyChange(EventMessageConstants.CHANGE_STATE, StateID.Menu, StateID.Help);
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
