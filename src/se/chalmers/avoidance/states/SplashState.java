package se.chalmers.avoidance.states;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
/**
 * The splash state.
 * 
 * @author Florian Minges
 */
public class SplashState implements IState {

	private PropertyChangeSupport pcs;
	private Scene scene;
	private BitmapTextureAtlas splashAtlas;
	private TextureRegion splashImage;
	
	public SplashState() {
		this.pcs = new PropertyChangeSupport(this);
		initialize();
	}
	
	private void initialize() {
		scene = new Scene();
		scene.setBackground(new Background(0f, 0f, 0f));

	}
	
	/**
	 * {@inheritDoc}
	 */
	public void initializeState(Engine engine) {
		final float centerX = (720 - this.splashImage.getWidth()) / 2;
        final float centerY = (480 - this.splashImage.getHeight()) / 2;

		final Sprite sprite = new Sprite(centerX, centerY, splashImage, engine.getVertexBufferObjectManager());
	    scene.attachChild(sprite);
		
	}
	
	/**
	 * Updates the state.
	 * Invoked by the StateManager, do not call manually.
	 * @param tpf Time since last frame.
	 */
	public void update(float tpf) {

	}

	/**
	 * Returns the scene connected to the state.
	 * @return The state's scene.
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * Load all resources.
	 */
	public void onLoadResources(BaseGameActivity activity) {
		Engine engine = activity.getEngine();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        this.splashAtlas = new BitmapTextureAtlas(
                engine.getTextureManager(), 512, 256,
                TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.splashImage = BitmapTextureAtlasTextureRegionFactory.createFromAsset( 
        		this.splashAtlas, activity, "splash.png", 0, 0);

        engine.getTextureManager().loadTexture(splashAtlas);
		
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
		
	}

}
