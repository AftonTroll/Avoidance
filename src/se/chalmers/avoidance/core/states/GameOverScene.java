package se.chalmers.avoidance.core.states;

import java.util.HashMap;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import se.chalmers.avoidance.constants.FontConstants;
import se.chalmers.avoidance.util.ScreenResolution;

public class GameOverScene extends Scene {
	
	private VertexBufferObjectManager vbom;

	private Rectangle transparentBackground;
	private Text scoreText;
	private ButtonSprite button;
	private Sprite gameOverSprite;
	private Sprite newHighscoreSprite;

	public GameOverScene(VertexBufferObjectManager vbom, HashMap<String, TextureRegion> regions, 
			HashMap<String, Font> fonts) {
		this.vbom = vbom;
		initialize(regions, fonts);
	}
	
	public void addTo(Scene scene) {
		scene.setChildScene(this);
	}
	
	public void initialize(HashMap<String, TextureRegion> regions, HashMap<String, Font> fonts) {
		createTransparentBackground();
		createGameOverSprite(regions);
		createText(fonts);
		createButton(regions);
		
		attachChild(transparentBackground);
		attachChild(gameOverSprite);
		attachChild(scoreText);
		attachChild(button);
	}

	private void createTransparentBackground() {
		Rectangle rect = new Rectangle(0, 0, ScreenResolution.getWidthResolution(), 
				ScreenResolution.getHeightResolution(), this.vbom);
		rect.setColor(0.1f, 0.1f, 0.1f, 0.8f);
		this.transparentBackground = rect;
	}
	
	private void createText(HashMap<String, Font> fonts) {		
		Text scoreText = new Text(0, 0, fonts.get(FontConstants.GAME_OVER_SCORE), 
				"Score:", "Score: XXXXXXX".length(), vbom);
		scoreText.setColor(1.0f, 0.9f, 0.1f, 1.0f);
		//position gets set in the setScore()-method
		this.scoreText = scoreText;
	}
	
	private void createGameOverSprite(HashMap<String, TextureRegion> regions) {
		Sprite sprite = new Sprite(0, 0, regions.get("gameOver.png"), vbom);
		
		float xPos = getXPosHorizontalCentering(sprite);
		float yPos = getYPosVerticalCentering(sprite) - 150;
		sprite.setPosition(xPos, yPos);
		
		this.gameOverSprite = sprite;
	}
	
	private void createButton(HashMap<String, TextureRegion> regions) {
		ButtonSprite okButton = new ButtonSprite(0, 0, regions.get("okButton.png"), vbom);
		
		float xPos = getXPosHorizontalCentering(okButton);
		float yPos = getYPosVerticalCentering(okButton) + 200;
		okButton.setPosition(xPos, yPos);
		
		this.button = okButton;
	}
	
	private float getXPosHorizontalCentering(RectangularShape shape) {
		return (ScreenResolution.getWidthResolution() - shape.getWidth()) / 2;
	}
	
	private float getYPosVerticalCentering(RectangularShape shape) {
		return (ScreenResolution.getHeightResolution() - shape.getHeight()) / 2;
	}
	
	public void setScore(int score) {
		//do this on the ui update thread or not?
		scoreText.setText("Score: " + score);
		
		float xPos = getXPosHorizontalCentering(scoreText);
		float yPos = getYPosVerticalCentering(scoreText) + 50;
		scoreText.setPosition(xPos, yPos);
	}
	
	public void setButtonSpriteOnClickListener(ButtonSprite.OnClickListener listener) {
		button.setOnClickListener(listener);
		registerTouchArea(button);
		setTouchAreaBindingOnActionDownEnabled(true);
	}
	
}
