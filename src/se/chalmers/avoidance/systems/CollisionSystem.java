package se.chalmers.avoidance.systems;

import org.andengine.entity.shape.RectangularShape;
import org.andengine.opengl.vbo.IVertexBufferObject;

import se.chalmers.avoidance.components.Size;
import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.utils.ImmutableBag;
/**
 * System for handling collision between entities
 * 
 * @author Jakob Svensson
 *
 */
public class CollisionSystem extends EntitySystem{
	
	private ComponentMapper<Transform> transformMapper;
    private ComponentMapper<Velocity> velocityMapper;
	
	public CollisionSystem(World world) {
		super(Aspect.getAspectForAll(Transform.class, Size.class));
		this.world = world;
	}
	
	@Override
	public void initialize(){
		transformMapper = world.getMapper(Transform.class);
		velocityMapper = world.getMapper(Velocity.class);

	}
	
	@Override
	protected boolean checkProcessing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		
		ImmutableBag<Entity> walls = world.getManager(GroupManager.class).getEntities("Walls");
		Entity player = world.getManager(TagManager.class).getEntity("Player");
		
		for (int i=0;i<walls.size();i++){
			if(collisionExists(player, walls.get(i))){
				Velocity velocity = velocityMapper.get(player);
				velocity.setAngle(calculateAngle(velocity.getAngle(), walls.get(i)));
				
			}
		}
		
	}
	
	
	private float calculateAngle(float angle, Entity wall){
		
		
		float width = wall.getComponent(Size.class).getWidth();
		float height = wall.getComponent(Size.class).getHeight();
		
		if(width>height){
			angle = flipVertical(angle);
		}else{
			angle = flipHorizontal(angle);
		}
		return angle;
	}
	
	private float flipVertical(float angle){ 
		  angle*=-1; 
		  return angle;
	}
	
	private float flipHorizontal(float angle){
		  //Translate and then flip vertical
		  angle += Math.PI/2;
		  angle = flipVertical(angle);
		  angle -= Math.PI/2;
		  return angle;
	}
	
		
	/**
	 * Checks if two entities is colliding with each other 
	 * 
	 * @param e1 The first entity
	 * @param e2 The second entity
	 * @return True if colliding false if not colliding
	 */
	public boolean collisionExists(Entity e1, Entity e2){
		
		float e1X = e1.getComponent(Transform.class).getX();
		float e1Y = e1.getComponent(Transform.class).getY();
		float e1Width = e1.getComponent(Size.class).getWidth();
		float e1Height = e1.getComponent(Size.class).getHeight();
		
		CollisionObject collisionObject1 =  new CollisionObject(e1X, e1Y, e1Width, e1Height) ;
		
		float e2X = e2.getComponent(Transform.class).getX();
		float e2Y = e2.getComponent(Transform.class).getY();
		float e2Width = e2.getComponent(Size.class).getWidth();
		float e2Height = e2.getComponent(Size.class).getHeight();
		
		CollisionObject collisionObject2 =  new CollisionObject(e2X, e2Y, e2Width, e2Height) ;
		
		return collisionObject1.collidesWith(collisionObject2);
		
	}
	
	/**
	 * An object used to check for collision with Andengine's collision detection
	 * 
	 * @author Jakob
	 *
	 */
	private class CollisionObject extends RectangularShape{
		
		public CollisionObject(float pX, float pY, float pWidth, float pHeight) {
			super(pX, pY, pWidth, pHeight, null);
		}

		public IVertexBufferObject getVertexBufferObject() {
			return null;
		}

		@Override
		protected void onUpdateVertices() {			
		}
		
	}

}
