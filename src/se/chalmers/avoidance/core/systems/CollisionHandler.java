package se.chalmers.avoidance.core.systems;

import com.artemis.Entity;

public interface CollisionHandler {
	void handleCollision(Entity a, Entity b);
}
