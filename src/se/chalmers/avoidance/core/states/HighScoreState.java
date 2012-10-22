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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Map;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import se.chalmers.avoidance.constants.EventMessageConstants;
import se.chalmers.avoidance.constants.FileConstants;
import se.chalmers.avoidance.constants.FontConstants;
import se.chalmers.avoidance.util.FileUtils;
import se.chalmers.avoidance.util.ScreenResolution;
import se.chalmers.avoidance.util.Utils;

/**
 * The high score state.
 * 
 * @author Florian Minges
 */
public class HighScoreState implements IState , PropertyChangeListener {
	
	private Scene scene;
	private PropertyChangeSupport pcs;
	
	private Sprite highscoreTitle;
	private Text highscoreList;
	private Text rankingNumber;
	private ButtonSprite backButton;
	
	/**
	 * The maximum number of entries shown in the high score list.
	 */
	public static final int MAX_HIGH_SCORE_ENTRIES = 5;
	
	private static final Color HIGH_SCORE_LIST_COLOR = new Color(1.0f, 0.9f, 0.1f, 1.0f);
	
	
	/**
	 * Constructs a new <code>HighscoreState</code>.
	 */
	public HighScoreState(Map<String, TextureRegion> regions, Map<String, Font> fonts, 
			VertexBufferObjectManager vbom) {
		this.scene = new Scene();
		initialize(regions, fonts, vbom);
		this.pcs = new PropertyChangeSupport(this);
	}

	/**
	 * Initializes the high score state.
	 * 
	 * @param regions a <code>Map</code> containing loaded textures/regions
	 * @param fonts a <code>Map</code> containing loaded fonts
	 * @param vbom the game engines <code>VertexBufferObjectManager</code>
	 */
	private void initialize(Map<String, TextureRegion> regions, Map<String, Font> fonts, 
			VertexBufferObjectManager vbom) {
		createHighScoreTitle(regions, vbom);
		createHighScoreList(fonts, vbom);
		createBackButton(regions, vbom);
		
		scene.attachChild(highscoreTitle);
		scene.attachChild(rankingNumber);
		scene.attachChild(highscoreList);
		scene.attachChild(backButton);
		
		this.updateHighScoreList();
	}
	
	/**
	 * Creates and initializes a high score title, 
	 * using a <code>Sprite</code>.
	 * 
	 * @param regions a <code>Map</code> containing loaded textures/regions
	 * @param vbom the game engines <code>VertexBufferObjectManager</code>
	 */
	private void createHighScoreTitle(Map<String, TextureRegion> regions, 
			VertexBufferObjectManager vbom) {
		highscoreTitle = new Sprite(0, 0, regions.get(FileConstants.IMG_HIGH_SCORE_TITLE), vbom);
		
		float xPos = ScreenResolution.getXPosHorizontalCentering(highscoreTitle);
		float yPos = ScreenResolution.getYPosVerticalCentering(highscoreTitle) - 300;
		highscoreTitle.setPosition(xPos, yPos);
	}
	
	/**
	 * Creates and initializes the high score list.
	 * 
	 * @param fonts a <code>Map</code> containing loaded fonts
	 * @param vbom the game engines <code>VertexBufferObjectManager</code>
	 */
	private void createHighScoreList(Map<String, Font> fonts, VertexBufferObjectManager vbom) {
		highscoreList = new Text(0, 0, fonts.get(FontConstants.HIGH_SCORE), 
				"", "XXXXXXXXX".length() * MAX_HIGH_SCORE_ENTRIES, vbom);
		rankingNumber = new Text(0, 0, fonts.get(FontConstants.HIGH_SCORE), 
				"", "1) ".length() * MAX_HIGH_SCORE_ENTRIES, vbom);
		highscoreList.setColor(HIGH_SCORE_LIST_COLOR);
		rankingNumber.setColor(HIGH_SCORE_LIST_COLOR);
		highscoreList.setHorizontalAlign(HorizontalAlign.RIGHT);
		rankingNumber.setHorizontalAlign(HorizontalAlign.RIGHT);
		
		float xPos = ScreenResolution.getXPosHorizontalCentering(highscoreList);
		float yPos = ScreenResolution.getYPosVerticalCentering(highscoreList);
		rankingNumber.setPosition(xPos, yPos);
		highscoreList.setPosition(xPos + rankingNumber.getWidth(), yPos);
	}
	
	/**
	 * Creates and initializes the back button.
	 * 
	 * @param regions a <code>Map</code> containing loaded textures/regions
	 * @param vbom the game engines <code>VertexBufferObjectManager</code>
	 */
	private void createBackButton(Map<String, TextureRegion> regions, VertexBufferObjectManager vbom) {
		backButton = new ButtonSprite(0, 0, regions.get(FileConstants.IMG_BUTTON_OK), vbom);
		
		float xPos = ScreenResolution.getXPosHorizontalCentering(backButton);
		float yPos = ScreenResolution.getYPosVerticalCentering(backButton) + 300;
		backButton.setPosition(xPos, yPos);
		
		backButton.setOnClickListener(new ButtonSprite.OnClickListener() {
			public void onClick( ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				pcs.firePropertyChange(EventMessageConstants.CHANGE_STATE, StateID.Highscore, StateID.Menu);
		    } 
		});
		this.scene.registerTouchArea(backButton);
		this.scene.setTouchAreaBindingOnActionDownEnabled(true);
	}
	
	/**
	 * Sets the text of the high score list, or displays an
	 * error message if no satisfying list was provided.
	 */
	private void updateHighScoreList() {
		String highscore;
		String rank;
		
		try {
			highscore = retrieveHighScoreString(MAX_HIGH_SCORE_ENTRIES);
			StringBuilder builder = new StringBuilder();
			for (int i = 1; i <= MAX_HIGH_SCORE_ENTRIES; i++) {
				builder.append(i);
				builder.append(")   \n");
			}
			rank = builder.toString();
			FileUtils.saveToFile(highscore, FileUtils.PATH); //save only the high scores
		} catch (NoHighScoreException nhe) {
			highscore = "No highscores found";
			rank = "";
		}
		this.highscoreList.setText(highscore);
		this.rankingNumber.setText(rank);
		
		float xPos = ScreenResolution.getXPosHorizontalCentering(rankingNumber) - (highscoreList.getWidth() / 2);
		float yPos = ScreenResolution.getYPosVerticalCentering(rankingNumber);
		rankingNumber.setPosition(xPos, yPos);
		highscoreList.setPosition(xPos + rankingNumber.getWidth(), yPos);
	}
	
	/**
	 * Returns the high scores, formatted as one multi line string. <p>
	 *
	 * @return a sorted multi line string representing the high scores
	 */
	private String retrieveHighScoreString(int maxEntries) throws NoHighScoreException {
		List<String> list = FileUtils.readFromFile(FileUtils.PATH);
		List<Integer> numbers = FileUtils.getSortedIntegers(list);
		if (numbers == null || numbers.isEmpty()) {
			throw new NoHighScoreException();
		}
		Utils.trimList(numbers, maxEntries);
		return FileUtils.createMultiLineString(numbers);
	}
	
	/**
	 * Updates the state.
	 * Invoked by the StateManager, do not call manually.
	 * @param tpf Time since last frame.
	 */
	public void update(float tpf) {
		//
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
	
	/**
	 * An exception thrown when no high score was found.
	 * @author Florian Minges
	 */
	private class NoHighScoreException extends Exception {
		private static final long serialVersionUID = -4952572847775951630L;
	}

	/**
	 * Listens to <code>PropertyChangeEvents</code>.<p>
	 * Do NOT call manually.
	 * 
	 * @param event an event
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (event != null && event.getNewValue() != null && 
				EventMessageConstants.GAME_OVER.equals(event.getPropertyName())) {
			int score = 0;
			try {
				score = (Integer) event.getNewValue();
			} catch (ClassCastException cce) {
			} // score is 0 if error occurs
			FileUtils.addToFile(String.valueOf(score), FileUtils.PATH);
			updateHighScoreList();
		}
	}

}
