package se.chalmers.avoidance;

import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.artemis.Aspect;
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
		
	}
	
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

}
