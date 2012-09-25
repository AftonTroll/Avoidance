package se.chalmers.avoidance;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * A listener that listens to the sensor
 * and passes on the values to its listeners
 * 
 * @author Filip brynfors
 *
 */
public class AccelerometerListener implements SensorEventListener {
	private PropertyChangeSupport pcs;
	private SensorManager manager;
	private Sensor accelerometer;
	
	/**
	 * Constructs a new listener for the accelerometer from
	 * the given sensorManager.
	 * @param sensorManager the android sensor manager
	 */
	public AccelerometerListener(SensorManager sensorManager){
		pcs = new PropertyChangeSupport(this);
		manager = sensorManager;
		accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}
	
	/**
	 * Adds a new propertyChangeListener
	 * @param pcl the listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl){
		pcs.addPropertyChangeListener(pcl);
	}
	
	/**
	 * Removes a propertyChangeListener
	 * @param pcl the listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl){
		pcs.removePropertyChangeListener(pcl);
	}
	
	/**
	 * Starts listening to the accelerometer
	 */
	public void startListening(){
		manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
	}
	
	/**
	 * Stops listening to the accelerometer
	 */
	public void stopListening(){
		manager.unregisterListener(this);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	public void onSensorChanged(SensorEvent event) {
		pcs.firePropertyChange("AccelerometerX", null, event.values[0]);
		pcs.firePropertyChange("AccelerometerY", null, event.values[1]);
	}
	
	
}
