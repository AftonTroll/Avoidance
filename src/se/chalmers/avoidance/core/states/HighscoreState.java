/* 
 * Copyright (c) 2012 Florian Minges
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import se.chalmers.avoidance.constants.EventMessageConstants;
import se.chalmers.avoidance.constants.FontConstants;
import se.chalmers.avoidance.util.FileUtils;
import se.chalmers.avoidance.util.Utils;

/**
 * The high score state.
 * 
 * @author Florian Minges
 */
public class HighscoreState implements IState {
	
	private Scene scene;
	private PropertyChangeSupport pcs;
	
	private Sprite highscoreTitle;
	private Text highscoreList;
	private ButtonSprite backButton;
	private ButtonSprite extraButton;
	
	private final int MAX_HIGH_SCORE_ENTRIES = 5;
	
	/**
	 * Constructs a new <code>HighscoreState</code>.
	 */
	public HighscoreState(HashMap<String, TextureRegion> regions, HashMap<String, Font> fonts, 
			VertexBufferObjectManager vbom) {
		this.scene = new Scene();
		initialize(regions, fonts, vbom);
		this.pcs = new PropertyChangeSupport(this);
	}

	/**
	 * Initializes the high score state.
	 * 
	 * @param regions a <code>HashMap</code> containing loaded textures/regions
	 * @param fonts a <code>HashMap</code> containing loaded fonts
	 * @param vbom the game engines <code>VertexBufferObjectManager</code>
	 */
	private void initialize(HashMap<String, TextureRegion> regions, HashMap<String, Font> fonts, 
			VertexBufferObjectManager vbom) {
		createHighscoreTitle(regions, vbom);
		createHighscoreList(fonts, vbom);
		createBackButton(regions, vbom);
		
		scene.attachChild(highscoreTitle);
		scene.attachChild(highscoreList);
		scene.attachChild(backButton);
		
		simulateHighscore();
		List<String> list = FileUtils.readFromFile(FileUtils.PATH);
		this.setHighscoreList(list);
	}
	
	//temporary method for testing the GUI
	private void simulateHighscore() {
		List<String> list = new ArrayList<String>();
		list.add("1392");
		list.add("983");
		list.add("1198");
		list.add("1835");
		list.add("444");
		list.add("944");
		list.add("1000");
		FileUtils.saveToFile(list, FileUtils.PATH);
	}
	
	/**
	 * Creates and initializes a high score title, 
	 * using a <code>Sprite</code>.
	 * 
	 * @param regions a <code>HashMap</code> containing loaded textures/regions
	 * @param vbom the game engines <code>VertexBufferObjectManager</code>
	 */
	private void createHighscoreTitle(HashMap<String, TextureRegion> regions, 
			VertexBufferObjectManager vbom) {
		Sprite sprite = new Sprite(0, 0, regions.get("highscore.png"), vbom);
		
		float xPos = Utils.getXPosHorizontalCentering(sprite);
		float yPos = Utils.getYPosVerticalCentering(sprite) - 300;
		sprite.setPosition(xPos, yPos);
		
		this.highscoreTitle = sprite;
	}
	
	/**
	 * Creates and initializes the high score list.
	 * 
	 * @param fonts a <code>HashMap</code> containing loaded fonts
	 * @param vbom the game engines <code>VertexBufferObjectManager</code>
	 */
	private void createHighscoreList(HashMap<String, Font> fonts, VertexBufferObjectManager vbom) {
		Text list = new Text(0, 0, fonts.get(FontConstants.GAME_OVER_SCORE), 
				"01)  ", "01)   XXXXXXX".length() * MAX_HIGH_SCORE_ENTRIES, vbom);
		list.setColor(1.0f, 0.9f, 0.1f, 1.0f);
		
		float xPos = Utils.getXPosHorizontalCentering(list);
		float yPos = Utils.getYPosVerticalCentering(list);
		list.setPosition(xPos, yPos);
		this.highscoreList = list;
	}
	
	/**
	 * Creates and initializes the back button.
	 * 
	 * @param regions a <code>HashMap</code> containing loaded textures/regions
	 * @param vbom the game engines <code>VertexBufferObjectManager</code>
	 */
	private void createBackButton(HashMap<String, TextureRegion> regions, VertexBufferObjectManager vbom) {
		ButtonSprite button = new ButtonSprite(0, 0, regions.get("okButton.png"), vbom);
		
		float xPos = Utils.getXPosHorizontalCentering(button);
		float yPos = Utils.getYPosVerticalCentering(button) + 300;
		button.setPosition(xPos, yPos);
		
		button.setOnClickListener(new ButtonSprite.OnClickListener() {
			public void onClick( ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				pcs.firePropertyChange(EventMessageConstants.CHANGE_STATE, StateID.Highscore, StateID.Menu);
		    } 
		});
		this.scene.registerTouchArea(button);
		this.scene.setTouchAreaBindingOnActionDownEnabled(true);
		
		this.backButton = button;
	}
	
	/**
	 * Sets the text of the high score list, or displays an
	 * error message if no satisfying list was provided.
	 * 
	 * @param list the high scores
	 */
	public void setHighscoreList(List<String> list) {
		String highscore = list.isEmpty() ? "No highscores found" : 
			FileUtils.sortList(list, MAX_HIGH_SCORE_ENTRIES);
		this.highscoreList.setText(highscore);
		
		float xPos = Utils.getXPosHorizontalCentering(highscoreList);
		float yPos = Utils.getYPosVerticalCentering(highscoreList) - 20;
		highscoreList.setPosition(xPos, yPos);
	}
	
	/**
	 * Updates the state.
	 * Invoked by the StateManager, do not call manually.
	 * @param tpf Time since last frame.
	 */
	public void update(float tpf) {
		
	}

	/**
	 * Returns the high score scene, or null if it is not instantiated yet.
	 * @return the high score scene
	 */
	public Scene getScene() {
		return scene;
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
