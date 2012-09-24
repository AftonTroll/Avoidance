package se.chalmers.avoidance;

import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

/**
 * System that handles the input of the user and updates the player
 * 
 * @author Filip Brynfors
 *
 */
public class PlayerControlSystem extends EntityProcessingSystem {
	private float lastAccelerometerX = 0;
	private float lastAccelerometerY = 0;
	private int playerID;
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Velocity> velocityMapper;
	
	/**
	 * Constructs a PlayerControlSystem that listens to the accelerometer
	 * from the given sensor manager and moves the entity with the given ID
	 * @param playerID the ID of the player entity
	 */
	public PlayerControlSystem(int playerID) {
		super(Aspect.getAspectForAll(Transform.class, Velocity.class));
		this.playerID = playerID;
	}
	
	@Override
	protected void initialize() {
		transformMapper = world.getMapper(Transform.class);
		velocityMapper = world.getMapper(Velocity.class);
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void process(Entity entity) {
		//TODO: Afton. Calculate the velocity and position in respect to the tpf
		if (entity.getId() == playerID) {
			//Update the Velocity
			Velocity playerVelocity = velocityMapper.get(entity);
			float incVelX = 0;
			float incVelY = 0;
			
			if (Math.abs(lastAccelerometerX) > 1) {
				incVelX = lastAccelerometerX;
			}
			
			if (Math.abs(lastAccelerometerY) > 1) {
				incVelY = lastAccelerometerY;
			}
			
			float newVelX = getHorizontalSpeed(playerVelocity) + incVelX;
			float newVelY = getVerticalSpeed(playerVelocity) + incVelY;
			
			playerVelocity.setAngle((float) Math.atan2(newVelY, newVelX));
			playerVelocity.setSpeed((float) Math.sqrt(newVelX*newVelX+newVelY*newVelY));
			
			//Update the position
			Transform playerTransform = transformMapper.get(entity);
			playerTransform.setX(playerTransform.getX() + world.delta*getHorizontalSpeed(playerVelocity));
			playerTransform.setY(playerTransform.getY() + world.delta*getVerticalSpeed(playerVelocity));
		}
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
	
	
	//Calculates the speed of the horizontal part of the velocity
	private float getHorizontalSpeed(Velocity vel) {
		return (float) (vel.getSpeed() * Math.cos(vel.getAngle()));
	}
	
	//Calculates the speed of the vertical part of the velocity
	private float getVerticalSpeed(Velocity vel) {
		return (float) (vel.getSpeed() * Math.sin(vel.getAngle()));
	}
}
