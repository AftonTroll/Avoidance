/*
* Copyright (c) 2012 Filip Brynfors
*
* This file is part of Avoidance.
*
* Avoidance is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Avoidance is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Avoidance. If not, see <http://www.gnu.org/licenses/>.
*
*/

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
	private final PropertyChangeSupport pcs;
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

	/**
	 * Used when the accuracy is changed
	 */
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	/**
	 * Used when the value of the sensor is changed
	 */
	public void onSensorChanged(SensorEvent event) {
		pcs.firePropertyChange("AccelerometerX", null, event.values[0]);
		pcs.firePropertyChange("AccelerometerY", null, event.values[1]);
	}
	
	
}
