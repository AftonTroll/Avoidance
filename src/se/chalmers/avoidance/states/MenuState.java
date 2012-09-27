package se.chalmers.avoidance.states;

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

import android.opengl.GLES20;

public class MenuState implements IState, IOnMenuItemClickListener {

	private static final int CAMERA_WIDTH = 720;
    private static final int CAMERA_HEIGHT = 480;

    protected static final int MENU_START = 0;
    protected static final int MENU_HIGHSCORES = MENU_START + 1;
    protected static final int MENU_HELP = MENU_START + 2;
    protected static final int MENU_QUIT = MENU_START + 3;

    private BaseGameActivity baseGameActivity;
	private MenuScene menuScene;
	
	private BitmapTextureAtlas bitmapTextureAtlas;
    private BitmapTextureAtlas menuTexture;
    protected TextureRegion menuStartTextureRegion;
    protected TextureRegion menuHighscoreTextureRegion;
    protected TextureRegion menuHelpTextureRegion;
    protected TextureRegion menuQuitTextureRegion;
	
	public MenuState(BaseGameActivity activity) {
		this.baseGameActivity = activity;
		onLoadResources();
		initialize();
	}
	
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
	
	private void initialize() {
		menuScene = new MenuScene();
		menuScene.setBackground(new Background(0f, 0f, 0f));
		
		//center the scene
		this.menuScene.setX(this.CAMERA_WIDTH / 2 - 100);
		this.menuScene.setY(this.CAMERA_HEIGHT / 2 - 150);

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

	public Scene getScene() {
		return menuScene;
	}
	

	//TODO Move this code to the stateManager
    public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, 
    		final float pMenuItemLocalX, final float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_START:

			return true;
		case MENU_HIGHSCORES:

			return true;
		case MENU_HELP:

			return true;
		case MENU_QUIT:
			/* End Activity. */
			this.baseGameActivity.finish();
			return true;
		default:
			return false;
		}
    }


}
