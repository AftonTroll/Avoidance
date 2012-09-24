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
			Velocity newVelocity = new Velocity();
			
			if (Math.abs(lastAccelerometerX) > 1) {
				Velocity incVelX = new Velocity(lastAccelerometerX);
				newVelocity = combineVelocities(playerVelocity, incVelX);
			}
			
			if (Math.abs(lastAccelerometerY) > 1) {
				Velocity incVelY = new Velocity(lastAccelerometerY, (float) (Math.PI/2));
				newVelocity = combineVelocities(newVelocity, incVelY);
			}
			
			playerVelocity.setAngle(newVelocity.getAngle());
			playerVelocity.setSpeed(newVelocity.getSpeed());
			
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
	
	

	
	private Velocity combineVelocities(Velocity v1, Velocity v2) {
		Velocity sumVelocity = new Velocity();
		
		float newVelX = getHorizontalSpeed(v1) + getHorizontalSpeed(v2);
		float newVelY = getVerticalSpeed(v1) + getVerticalSpeed(v2);
		
		//if atleast one of the velocites isn't 0
		//then calculate the angle of the velocity
		if (newVelX != 0 || newVelY != 0){
			sumVelocity.setAngle((float) Math.atan2(newVelY, newVelX));
		}
		sumVelocity.setSpeed((float) Math.sqrt(newVelX*newVelX+newVelY*newVelY));
		
		return sumVelocity;
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
