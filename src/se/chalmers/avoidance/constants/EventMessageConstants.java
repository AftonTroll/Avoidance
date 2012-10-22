/* 
 * Copyright (c) 2012 Florian Minges
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
 * Container of constants for event messages.
 * 
 * @author Florian Minges
 */
public final class EventMessageConstants {

	/**
	 * Identifier for the event used to quit the game.
	 */
	public static final String QUIT_GAME = "System.Exit";

	/**
	 * Identifier for the event used to signal 'Game Over'.
	 */
	public static final String GAME_OVER = "Game.Over";

	/**
	 * Identifier for the event used to change state.
	 */
	public static final String CHANGE_STATE = "Change.State";

	public static final String RESTART_GAME = "RESTART_GAME";

	private EventMessageConstants() {
	}
}
