/* 
 * Copyright (c) 2012 Jakob Svensson
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

package se.chalmers.avoidance.core.components;

import com.artemis.Component;
/**
 * Component holding the score.
 * 
 * @author Jakob Svensson
 *
 */
public class Score extends Component {

	private int killScore = 0;
	private int powerupScore = 0;
	public static final int POWERUP_PICKUP_SCORE = 50;
	public static final int KILL_SCORE = 100;

	/**
	 * Returns the current score from killing enemies and picking up power-ups.
	 * 
	 * @return the score 
	 */
	public int getScore(){
		return killScore+powerupScore;
	}

	/**
	 * Adds score from killing enemies.
	 * 
	 * @param score the score to be added
	 */
	public void addKillScore(int score){
		killScore+=score;
	}

	/**
	 * Adds score from picking up power-ups.
	 * 
	 * @param score the score to be added
	 */
	public void addPowerupScore(int score){
		powerupScore+=score;
	}

}
