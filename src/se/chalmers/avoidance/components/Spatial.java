package se.chalmers.avoidance.components;

import org.andengine.entity.sprite.Sprite;

import com.artemis.Component;

public class Spatial extends Component {
	private String name;
	private Sprite sprite;
	
	public Spatial(String name) {
		setName(name);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Returns the sprite if it has been set, null otherwise.
	 * @return The spatial's sprite if it's been set.
	 */
	public Sprite getSprite() {
		return sprite;
	}
	
}
