package se.chalmers.avoidance;

import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;

import com.artemis.Entity;
import com.artemis.World;

public class EntityFactory {
	public static Entity createPlayer(World world){
		Entity player = world.createEntity();
		player.addComponent(new Transform());
		player.addComponent(new Velocity());
		
		return player;
	}
}
