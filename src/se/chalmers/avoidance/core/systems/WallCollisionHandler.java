package se.chalmers.avoidance.core.systems;

import se.chalmers.avoidance.core.components.Size;
import se.chalmers.avoidance.core.components.Transform;
import se.chalmers.avoidance.core.components.Velocity;
import se.chalmers.avoidance.util.Utils;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;

public class WallCollisionHandler implements CollisionHandler{
	@Mapper
	ComponentMapper<Velocity> velocityMapper;
	@Mapper
	ComponentMapper<Transform> transformMapper;
	@Mapper
	ComponentMapper<Size> sizeMapper;
	
	public void handleCollision(Entity player, Entity wall){		
		Size wallSize = sizeMapper.get(wall);
		Transform wallTransform = transformMapper.get(wall);
		Velocity playerVelocity = velocityMapper.get(player);
		Size playerSize = sizeMapper.get(player);
		Transform playerTransform = transformMapper.get(player);
		
		float wallWidth = wallSize.getWidth();
		float wallHeight = wallSize.getHeight();
		float wallX = wallTransform.getX(); 
		float wallY = wallTransform.getY();		
		float angle = playerVelocity.getAngle();
		float newAngle = angle;
		
		//Check if player collides with horizontal side or vertical side
		if(playerTransform.getX()+playerSize.getWidth()/2>wallX&&playerTransform.getX()+playerSize.getWidth()/2<wallX+wallWidth){
			newAngle = flipVertical(angle);
			if(angle>Math.PI){
				//Collision on lower side of the wall
				playerTransform.setY(wallY+wallHeight);
			}else{
				//Collision on upper side of the wall
				playerTransform.setY(wallY-playerSize.getHeight());
			}
			
		}else if(playerTransform.getY()+playerSize.getHeight()/2>wallY&&playerTransform.getY()+playerSize.getHeight()/2<wallY+wallHeight){
			newAngle = flipHorizontal(angle);
			if(angle>Math.PI/2&&angle<(Math.PI*3)/2){
				//Collision on right side of wall
				playerTransform.setX(wallX+wallWidth);
			}else{
				//Collision on left side of wall
				playerTransform.setX(wallX-playerSize.getWidth());
			}
			
		}else{
			//Corner or almost corner collision			
			newAngle = Utils.reverseAngle(angle);
		}
		playerVelocity.setAngle(newAngle);
	}
	
	private float flipVertical(float angle){ 
		  float newAngle=angle*-1; 
		  return newAngle;
	}
	
	private float flipHorizontal(float angle){
		  //Translate and then flip vertical
		float newAngle = angle + (float) Math.PI/2;
		  newAngle = flipVertical(newAngle);
		  newAngle -= Math.PI/2;
		  return newAngle;
	}
}
