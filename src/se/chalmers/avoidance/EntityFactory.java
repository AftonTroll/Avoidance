package se.chalmers.avoidance;

import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Spatial;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

public class EntityFactory {
	
	public static Entity createPlayer(World world){
		Entity player = world.createEntity();
		world.getManager(TagManager.class).register("PLAYER", player);
		
		player.addComponent(new Transform(50,100));
		player.addComponent(new Velocity());
		player.addComponent(new Size(32,32));
		player.addComponent(new Spatial("ball.png"));
		
		return player;
	}
	
	public static Entity createWall(World world, float width, float height, float xPos, float yPos){
		Entity wall = world.createEntity();
		world.getManager(GroupManager.class).add(wall, "WALLS");
		
		wall.addComponent(new Transform(xPos, yPos));
		wall.addComponent(new Size(width,height));
		if(width>height){
			wall.addComponent(new Spatial("wall_horisontal.png"));
		}else{
			wall.addComponent(new Spatial("wall_vertical.png"));
		}
		
		return wall;
	}
	
	public static Entity createObstacle(World world, float width, float height, float xPos, float yPos){
		Entity obstacle = world.createEntity();
		world.getManager(GroupManager.class).add(obstacle, "WALLS");
		
		obstacle.addComponent(new Transform(xPos, yPos));
		obstacle.addComponent(new Size(width,height));
		obstacle.addComponent(new Spatial("obstacle.png"));
		
		return obstacle;
	}
}
