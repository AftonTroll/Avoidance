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

public class PlayerControlSystem extends EntityProcessingSystem {
	private SensorManager sensorManager;
	private float lastAccelerometerX = 0;
	private float lastAccelerometerY = 0;
	
	public PlayerControlSystem(SensorManager sensorManager) {
		super(Aspect.getAspectForAll(Transform.class, Velocity.class));
		this.sensorManager = sensorManager;
	}
	
	@Override
	protected void initialize() {
		Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(new AccelerometerListener(), accelerometer,SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void process(Entity entity) {
		
	}
	
	
	private class AccelerometerListener implements SensorEventListener {

		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}

		public void onSensorChanged(SensorEvent event) {
			lastAccelerometerX = event.values[0];
			lastAccelerometerY = event.values[1];
		}
		
	}

}
