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

import java.util.Map;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import se.chalmers.avoidance.constants.FontConstants;
import se.chalmers.avoidance.util.ScreenResolution;

/**
 * A Game Over Scene for displaying the users score. <p>
 * 
 * If set as a child scene to any other scene, it will look like
 * a layer which is put on top (transparent background).
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

	/**
	 * Constructs a new game over scene.
	 * 
	 * @param vbom the game engines <code>VertexBufferObjectManager</code>
	 * @param regions a <code>Map</code> containing loaded textures/regions
	 * @param fonts a <code>Map</code> containing loaded fonts
	 */
	public GameOverScene(VertexBufferObjectManager vbom, Map<String, TextureRegion> regions, 
			Map<String, Font> fonts) {
		this.vbom = vbom;
		initialize(regions, fonts);
	}
	
	/**
	 * Adds this <code>GameOverScene</code> as a child scene to 
	 * the <code>scene</code> provided in the argument.
	 * 
	 * @param scene the scene which gets this <code>GameOverScene</code> as a child scene
	 */
	public void addTo(Scene scene) {
		scene.setChildScene(this);
	}
	
	/**
	 * Initializes this <code>GameOverScene</code>.
	 * 
	 * @param regions a <code>Map</code> containing loaded textures/regions
	 * @param fonts a <code>Map</code> containing loaded fonts
	 */
	public void initialize(Map<String, TextureRegion> regions, Map<String, Font> fonts) {
		createTransparentBackground();
		createGameOverSprite(regions);
		createText(fonts);
		createButton(regions);
		
		attachChild(transparentBackground);
		attachChild(gameOverSprite);
		attachChild(scoreText);
		attachChild(button);
	}

	/**
	 * Creates and initializes the transparent background.
	 */
	private void createTransparentBackground() {
		Rectangle rect = new Rectangle(0, 0, ScreenResolution.getWidthResolution(), 
				ScreenResolution.getHeightResolution(), this.vbom);
		rect.setColor(0.1f, 0.1f, 0.1f, 0.8f);
		this.transparentBackground = rect;
	}
	
	/**
	 * Creates and initializes the text component that holds
	 * information about the users score.
	 * 
	 * @param fonts a <code>Map</code> containing loaded fonts
	 */
	private void createText(Map<String, Font> fonts) {		
		Text scoreText = new Text(0, 0, fonts.get(FontConstants.GAME_OVER_SCORE), 
				"Score:", "Score: XXXXXXX".length(), vbom);
		scoreText.setColor(1.0f, 0.9f, 0.1f, 1.0f);
		//position gets set in the setScore()-method
		this.scoreText = scoreText;
	}
	
	/**
	 * Creates and initializes the game over sprite.
	 * 
	 * @param regions a <code>Map</code> containing loaded textures/regions
	 */
	private void createGameOverSprite(Map<String, TextureRegion> regions) {
		Sprite sprite = new Sprite(0, 0, regions.get("gameOver.png"), vbom);
		
		float xPos = ScreenResolution.getXPosHorizontalCentering(sprite);
		float yPos = ScreenResolution.getYPosVerticalCentering(sprite) - 150;
		sprite.setPosition(xPos, yPos);
		
		this.gameOverSprite = sprite;
	}
	
	/**
	 * Creates and initializes the button.
	 * 
	 * @param regions a <code>Map</code> containing loaded textures/regions
	 */
	private void createButton(Map<String, TextureRegion> regions) {
		ButtonSprite okButton = new ButtonSprite(0, 0, regions.get("okButton.png"), vbom);
		
		float xPos = ScreenResolution.getXPosHorizontalCentering(okButton);
		float yPos = ScreenResolution.getYPosVerticalCentering(okButton) + 200;
		okButton.setPosition(xPos, yPos);
		
		this.button = okButton;
	}
	
	/**
	 * Sets the score to display, and positions it correctly.
	 * 
	 * @param score the users score
	 */
	public void setScore(int score) {
		//do this on the ui update thread or not?
		scoreText.setText("Score: " + score);
		
		float xPos = ScreenResolution.getXPosHorizontalCentering(scoreText);
		float yPos = ScreenResolution.getYPosVerticalCentering(scoreText) + 50;
		scoreText.setPosition(xPos, yPos);
	}
	
	/**
	 * Sets the <code>ButtonSprite.OnClickListener</code> 
	 * for this objects button. 
	 * 
	 * @param listener the <code>ButtonSprite.OnClickListener</code> 
	 * for this objects button. 
	 */
	public void setButtonSpriteOnClickListener(ButtonSprite.OnClickListener listener) {
		button.setOnClickListener(listener);
		registerTouchArea(button);
		setTouchAreaBindingOnActionDownEnabled(true);
	}
	
}
