package se.chalmers.avoidance;

import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.FloatMath;

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
	private SensorManager sensorManager;
	private float lastAccelerometerX = 0;
	private float lastAccelerometerY = 0;
	private int playerID;
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Velocity> velocityMapper;
	
	/**
	 * Constructs a PlayerControlSystem that listens to the accelerometer
	 * from the given sensor manager and moves the entity with the given ID
	 * @param sensorManager the android sensor manager
	 * @param playerID the ID of the player entity
	 */
	public PlayerControlSystem(SensorManager sensorManager, int playerID) {
		super(Aspect.getAspectForAll(Transform.class, Velocity.class));
		this.sensorManager = sensorManager;
		this.playerID = playerID;
	}
	
	@Override
	protected void initialize() {
		transformMapper = world.getMapper(Transform.class);
		velocityMapper = world.getMapper(Velocity.class);
		
		if(sensorManager != null){
			Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			sensorManager.registerListener(new AccelerometerListener(), accelerometer,SensorManager.SENSOR_DELAY_GAME);
		}
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
	
	
	// A listener that listens to the events from the accelerometer
	// and stores the last values from the events.
	private class AccelerometerListener implements SensorEventListener {
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}

		public void onSensorChanged(SensorEvent event) {
			setSensorValues(event.values[0], event.values[1]);
		}
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
		return (float) (vel.getSpeed() * FloatMath.cos(vel.getAngle()));
	}
	
	//Calculates the speed of the vertical part of the velocity
	private float getVerticalSpeed(Velocity vel) {
		return (float) (vel.getSpeed() * FloatMath.sin(vel.getAngle()));
	}
}
