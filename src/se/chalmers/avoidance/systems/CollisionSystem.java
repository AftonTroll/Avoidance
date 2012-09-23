package se.chalmers.avoidance.systems;

import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.vbo.IVertexBufferObject;

import se.chalmers.avoidance.components.Size;
import se.chalmers.avoidance.components.Transform;
import se.chalmers.avoidance.components.Velocity;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
/**
 * System for handling collision between entities
 * 
 * @author Jakob Svensson
 *
 */
public class CollisionSystem extends EntitySystem{

	public CollisionSystem() {
		super(Aspect.getAspectForAll(Transform.class, Velocity.class));
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean checkProcessing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		
		//Check collision between player and wall
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
	
	
	private class CollisionObject extends RectangularShape{

		public CollisionObject(float pX, float pY, float pWidth, float pHeight) {
			super(pX, pY, pWidth, pHeight, null);
			// TODO Auto-generated constructor stub
		}

		public IVertexBufferObject getVertexBufferObject() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onUpdateVertices() {
			// TODO Auto-generated method stub
			
		}
		
	}

}
