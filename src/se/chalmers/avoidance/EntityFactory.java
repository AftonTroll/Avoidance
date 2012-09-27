package se.chalmers.avoidance;

import se.chalmers.avoidance.components.Size;
import se.chalmers.avoidance.components.Spatial;
import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

public class EntityFactory {
	
	public static Entity createPlayer(World world){
		Entity player = world.createEntity();
		world.getManager(TagManager.class).register("PLAYER", player);
		
		player.addComponent(new Transform(50,300));
		player.addComponent(new Velocity());
		player.addComponent(new Size());
		player.addComponent(new Spatial("ball.png"));
		
		return player;
	}
	
	public static Entity createWall(World world){
		Entity wall = world.createEntity();
		world.getManager(GroupManager.class).add(wall, "WALLS");
		
		wall.addComponent(new Transform());
		wall.addComponent(new Size());
		wall.addComponent(new Spatial("wall_horisontal.png"));
		
		return wall;
	}
}
