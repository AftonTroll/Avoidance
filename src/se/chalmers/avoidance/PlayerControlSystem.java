package se.chalmers.avoidance;

import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;
import android.hardware.SensorManager;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

public class PlayerControlSystem extends EntityProcessingSystem {
	private SensorManager sensorManager;
	
	public PlayerControlSystem(SensorManager sensorManager) {
		super(Aspect.getAspectForAll(Transform.class, Velocity.class));
		this.sensorManager = sensorManager;
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void process(Entity entity) {
		
	}

}
