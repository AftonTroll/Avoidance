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

import java.util.List;
import java.util.Map;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import se.chalmers.avoidance.constants.FileConstants;
import se.chalmers.avoidance.constants.FontConstants;
import se.chalmers.avoidance.util.FileUtils;
import se.chalmers.avoidance.util.ScreenResolution;

/**
 * A Game Over Scene for displaying the users score.
 * <p>
 * 
 * If set as a child scene to any other scene, it will look like a layer which
 * is put on top (transparent background).
 * 
 * @author Florian Minges
 */
public class GameOverScene extends Scene {

	private VertexBufferObjectManager vbom;

	private Rectangle transparentBackground;
	private Text scoreText;
	private ButtonSprite button;
	private Sprite gameOverSprite;
	private Sprite newHighscoreSprite;

	private static final Color TRANSPARENT_BACKGROUND_COLOR = new Color(0.1f,
			0.1f, 0.1f, 0.95f);
	private static final Color SCORE_TEXT_COLOR = new Color(1.0f, 0.9f, 0.1f,
			1.0f);

	/**
	 * Constructs a new game over scene.
	 * 
	 * @param vbom
	 *            the game engines <code>VertexBufferObjectManager</code>
	 * @param regions
	 *            a <code>Map</code> containing loaded textures/regions
	 * @param fonts
	 *            a <code>Map</code> containing loaded fonts
	 */
	public GameOverScene(VertexBufferObjectManager vbom,
			Map<String, TextureRegion> regions, Map<String, Font> fonts) {
		this.vbom = vbom;
		initialize(regions, fonts);
	}

	/**
	 * Adds this <code>GameOverScene</code> as a child scene to the
	 * <code>scene</code> provided in the argument.
	 * 
	 * @param scene
	 *            the scene which gets this <code>GameOverScene</code> as a
	 *            child scene
	 */
	public void addTo(Scene scene) {
		scene.setChildScene(this);
	}

	/**
	 * Initializes this <code>GameOverScene</code>.
	 * 
	 * @param regions
	 *            a <code>Map</code> containing loaded textures/regions
	 * @param fonts
	 *            a <code>Map</code> containing loaded fonts
	 */
	private void initialize(Map<String, TextureRegion> regions,
			Map<String, Font> fonts) {
		createTransparentBackground();
		createGameOverSprite(regions);
		createText(fonts);
		createButton(regions);
		createNewHighscoreSprite(regions);

		attachChild(transparentBackground);
		attachChild(gameOverSprite);
		attachChild(scoreText);
		attachChild(button);
		attachChild(newHighscoreSprite);

		this.setBackgroundEnabled(false);
	}

	/**
	 * Creates and initializes the transparent background.
	 */
	private void createTransparentBackground() {
		transparentBackground = new Rectangle(0, 0,
				ScreenResolution.getWidthResolution(),
				ScreenResolution.getHeightResolution(), this.vbom);
		transparentBackground.setColor(TRANSPARENT_BACKGROUND_COLOR);
	}

	/**
	 * Creates and initializes the text component that holds information about
	 * the users score.
	 * 
	 * @param fonts
	 *            a <code>Map</code> containing loaded fonts
	 */
	private void createText(Map<String, Font> fonts) {
		scoreText = new Text(0, 0, fonts.get(FontConstants.GAME_OVER_SCORE),
				"Score:", "Score: XXXXXXX".length(), vbom);
		scoreText.setColor(SCORE_TEXT_COLOR);
		// position gets set in the setScore()-method
	}

	/**
	 * Creates and initializes the game over sprite.
	 * 
	 * @param regions
	 *            a <code>Map</code> containing loaded textures/regions
	 */
	private void createGameOverSprite(Map<String, TextureRegion> regions) {
		gameOverSprite = new Sprite(0, 0,
				regions.get(FileConstants.IMG_GAME_OVER), vbom);

		float xPos = ScreenResolution
				.getXPosHorizontalCentering(gameOverSprite);
		float yPos = ScreenResolution.getYPosVerticalCentering(gameOverSprite) - 150;
		gameOverSprite.setPosition(xPos, yPos);
	}

	/**
	 * Creates and initializes the button.
	 * 
	 * @param regions
	 *            a <code>Map</code> containing loaded textures/regions
	 */
	private void createButton(Map<String, TextureRegion> regions) {
		button = new ButtonSprite(0, 0,
				regions.get(FileConstants.IMG_BUTTON_OK), vbom);

		float xPos = ScreenResolution.getXPosHorizontalCentering(button);
		float yPos = ScreenResolution.getYPosVerticalCentering(button) + 200;
		button.setPosition(xPos, yPos);
	}

	/**
	 * Creates and initializes a 'new high score'-sprite.
	 * <p>
	 * 
	 * @param regions
	 *            a <code>HashMap</code> containing loaded textures/regions
	 */
	private void createNewHighscoreSprite(Map<String, TextureRegion> regions) {
		newHighscoreSprite = new Sprite(0, 0,
				regions.get(FileConstants.IMG_NEW_HIGH_SCORE_TAG), vbom);

		float xPos = ScreenResolution
				.getXPosHorizontalCentering(newHighscoreSprite) + 400;
		float yPos = ScreenResolution
				.getYPosVerticalCentering(newHighscoreSprite);
		newHighscoreSprite.setPosition(xPos, yPos);
	}

	/**
	 * Sets the score to display, and positions it correctly.
	 * 
	 * @param score
	 *            the users score
	 */
	public void setScore(int score) {
		scoreText.setText("Score: " + score);

		float xPos = ScreenResolution.getXPosHorizontalCentering(scoreText);
		float yPos = ScreenResolution.getYPosVerticalCentering(scoreText) + 50;
		scoreText.setPosition(xPos, yPos);

		newHighscoreSprite.setVisible(isNewHighscore(score));
	}

	/**
	 * Checks if the supplied score is a new high score.
	 * <p>
	 * A new high score is currently a top 5 place.
	 * 
	 * @param score
	 *            the score
	 * @return true if the <code>score</code> is a new high score
	 */
	private boolean isNewHighscore(int score) {
		List<String> list = FileUtils.readFromFile(FileUtils.PATH);
		List<Integer> highscoreList = FileUtils.getSortedIntegers(list);
		int greaterScores = 0;
		for (int i = 0; i < highscoreList.size(); i++) {
			if (score < highscoreList.get(i)) {
				greaterScores++;
			}
		}

		return greaterScores < HighScoreState.MAX_HIGH_SCORE_ENTRIES;
	}

	/**
	 * Sets the <code>ButtonSprite.OnClickListener</code> for this objects
	 * button.
	 * 
	 * @param listener
	 *            the <code>ButtonSprite.OnClickListener</code> for this objects
	 *            button.
	 */
	public void setButtonSpriteOnClickListener(
			ButtonSprite.OnClickListener listener) {
		button.setOnClickListener(listener);
		registerTouchArea(button);
		setTouchAreaBindingOnActionDownEnabled(true);
	}

}
