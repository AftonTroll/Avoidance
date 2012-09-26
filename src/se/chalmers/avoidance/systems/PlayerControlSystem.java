package se.chalmers.avoidance.systems;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;
import se.chalmers.avoidance.util.Utils;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.managers.TagManager;
import com.artemis.utils.ImmutableBag;

/**
 * System that handles the input of the user and updates the player
 * 
 * @author Filip Brynfors
 *
 */
public class PlayerControlSystem extends EntitySystem implements PropertyChangeListener {
	private float lastAccelerometerX = 0;
	private float lastAccelerometerY = 0;
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Velocity> velocityMapper;
	private TagManager tagManager;
	
	/**
	 * Constructs a PlayerControlSystem that listens to the accelerometer
	 * from the given sensor manager and moves the entity with the given ID
	 * @param playerID the ID of the player entity
	 */
	public PlayerControlSystem() {
		super(Aspect.getAspectForAll(Transform.class, Velocity.class));
	}
	
	@Override
	protected void initialize() {
		transformMapper = world.getMapper(Transform.class);
		velocityMapper = world.getMapper(Velocity.class);
		tagManager = world.getManager(TagManager.class);
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity entity = tagManager.getEntity("PLAYER");
		//Update the Velocity
		//https://bitbucket.org/piemaster/artemoids/src/5c3a11ff2bdd/src/net/piemaster/artemoids/systems/PlayerShipControlSystem.java
		Velocity playerVelocity = velocityMapper.get(entity);
		float startVelX = Utils.getHorizontalSpeed(playerVelocity);
		float startVelY = Utils.getVerticalSpeed(playerVelocity);
		float newVelX = startVelX;
		float newVelY = startVelY;
		
		if (Math.abs(lastAccelerometerX) > 1) {
			newVelX += world.delta * lastAccelerometerX;
		}
		
		if (Math.abs(lastAccelerometerY) > 1) {
			newVelY += world.delta * lastAccelerometerY;
		}
		
		playerVelocity.setAngle((float) Math.atan2(newVelY, newVelX));
		playerVelocity.setSpeed((float) Math.sqrt(newVelX*newVelX+newVelY*newVelY));
		
		//Update the position
		Transform playerTransform = transformMapper.get(entity);

		float dx = world.delta*(startVelX + Utils.getHorizontalSpeed(playerVelocity))/2;
		float dy = world.delta*(startVelY + Utils.getVerticalSpeed(playerVelocity))/2;
		playerTransform.setX(playerTransform.getX() + dx);
		playerTransform.setY(playerTransform.getY() + dy);
	}
	
	/**
	 * Manually sets the values of the sensor
	 * @param x the x value of the sensor
	 * @param y the y value of the sensor
	 */
	public void setSensorValues(float x, float y){
		lastAccelerometerX = x;
		lastAccelerometerY = y;
	}

	public void propertyChange(PropertyChangeEvent event) {
		if(event!=null && event.getNewValue() != null){
			if("AccelerometerX".equals(event.getPropertyName())){
				lastAccelerometerX = (Float) event.getNewValue();
			}
			if("AccelerometerY".equals(event.getPropertyName())){
				lastAccelerometerY = (Float) event.getNewValue();
			}
		}
	}
}
