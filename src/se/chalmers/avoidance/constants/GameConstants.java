/* 
 * Copyright (c) 2012 Jakob Svensson, Markus Ekström
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Avoidance.  If not, see <http://www.gnu.org/licenses/>. 
 *  
 */

package se.chalmers.avoidance.constants;

/**
 * Container of constants for mapping groups and tags.
 * 
 * @author Jakob Svensson, Markus Ekström
 * 
 */
public final class GameConstants {

	/**
	 * Hidden constructor
	 */
	private GameConstants() {
	}

	/**
	 * Identifier for the player tag.
	 */
	public static final String TAG_PLAYER = "tag_player";

	/**
	 * Identifier for the score tag.
	 */
	public static final String TAG_SCORE = "tag_score";

	/**
	 * Identifier for the player group.
	 */
	public static final String GROUP_PLAYER = "group_player";

	/**
	 * Identifier for the wall group.
	 */
	public static final String GROUP_OBSTACLE_WALLS = "group_obstacle_walls";

	/**
	 * Identifier for the movingentities group.
	 */
	public static final String GROUP_MOVING_ENTITIES = "group_moving_entities";

	/**
	 * Identifier for the powerups group.
	 */
	public static final String GROUP_POWERUPS = "group_powerups";

	/**
	 * Identifier for the pitobstacle group.
	 */
	public static final String GROUP_OBSTACLE_PITS = "group_obstacle_pits";

	/**
	 * Identifier for the killplayerobstacle group.
	 */
	public static final String GROUP_OBSTACLE_SPIKES = "group_obstacle_spikes";

	/**
	 * Identifier for the enemies group.
	 */
	public static final String GROUP_ENEMIES = "group_enemies";

	/**
	 * Identifier for the circleshapes group.
	 */
	public static final String GROUP_CIRCLE_SHAPES = "group_circle_shapes";

}
