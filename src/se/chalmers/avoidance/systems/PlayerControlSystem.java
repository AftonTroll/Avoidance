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
 * System that handles the input of the user
 * and updates the players velocity and position
 * 
 * @author Filip Brynfors
 *
 */
public class PlayerControlSystem extends EntitySystem implements PropertyChangeListener {
	private float lastAccelerationX = 0;
	private float lastAccelerationY = 0;
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Velocity> velocityMapper;
	private TagManager tagManager;
	
	/**
	 * Constructs a PlayerControlSystem that listens to the accelerometer
	 * from the given sensor manager and moves the entity with the given ID
	 */
	public PlayerControlSystem() {
		super(Aspect.getAspectForAll(Transform.class, Velocity.class));
	}
	
	/**
	 * This method is called when the system is initialized
	 */
	@Override
	protected void initialize() {
		transformMapper = world.getMapper(Transform.class);
		velocityMapper = world.getMapper(Velocity.class);
		tagManager = world.getManager(TagManager.class);
	}

	/**
	 * Determines if the system should be processed or not
	 * 
	 * @return true if system should be processed, false otherwise
	 */
	@Override
	protected boolean checkProcessing() {
		return true;
	}

	/**
	 * This method is called when the entities are to be updated.
	 * Updates the velocity and position of the player
	 * @param entities the bag of entities with the wanted components
	 */
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Entity entity = tagManager.getEntity("PLAYER");
		if (entity != null) {
			//Update the Velocity
			//https://bitbucket.org/piemaster/artemoids/src/5c3a11ff2bdd/src/net/piemaster/artemoids/systems/PlayerShipControlSystem.java
			Velocity playerVel = velocityMapper.get(entity);
			float startVelX = Utils.getHorizontalSpeed(playerVel.getSpeed(), playerVel.getAngle());
			float startVelY = Utils.getVerticalSpeed(playerVel.getSpeed(), playerVel.getAngle());
			float newVelX = startVelX;
			float newVelY = startVelY;
			
			if (Math.abs(lastAccelerationX) > 1) {
				newVelX += world.delta * lastAccelerationX;
			}
			
			if (Math.abs(lastAccelerationY) > 1) {
				newVelY += world.delta * lastAccelerationY;
			}
			
			playerVel.setAngle((float) Math.atan2(newVelY, newVelX));
			playerVel.setSpeed((float) Math.sqrt(newVelX*newVelX+newVelY*newVelY));
			
			//Update the position
			Transform playerTransform = transformMapper.get(entity);
			float speed = playerVel.getSpeed();
			float angle = playerVel.getAngle();
			float dx = world.delta * (startVelX + Utils.getHorizontalSpeed(speed, angle))/2;
			float dy = world.delta * (startVelY + Utils.getVerticalSpeed(speed, angle))/2;
			playerTransform.setX(playerTransform.getX() + dx);
			playerTransform.setY(playerTransform.getY() + dy);
		}
	}

	/**
	 * Sets the values of the acceleration
	 * @param event the propertyChangeEvent containing the values of the accelerometer
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if(event!=null && event.getNewValue() != null){
			if("AccelerometerX".equals(event.getPropertyName())){
				lastAccelerationX = (Float) event.getNewValue();
			}
			if("AccelerometerY".equals(event.getPropertyName())){
				lastAccelerationY = (Float) event.getNewValue();
			}
		}
	}
}
