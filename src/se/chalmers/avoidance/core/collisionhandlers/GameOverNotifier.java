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

package se.chalmers.avoidance.core.collisionhandlers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import se.chalmers.avoidance.constants.EventMessageConstants;
import se.chalmers.avoidance.core.components.Score;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;

/**
 * Class for forwarding 'Game Over'-events to the appropriate place. <p>
 * 
 * Handlers should call the following statement on 'Game Over':
 * <code>GameOverNotifier.getInstance().gameOver();</code>
 * 
 * @author Florian Minges
 */
public final class GameOverNotifier {

	private static GameOverNotifier instance;
	private PropertyChangeSupport pcs;
	private World world;
	
	/**
	 * Returns the instance of this <code>GameOverNotifier</code>-class. <p>
	 * Creates a new instance if there is none yet.
	 * @return the instance of this <code>GameOverNotifier</code>-class
	 */
	public static GameOverNotifier getInstance() {
		if (instance == null)
			instance = new GameOverNotifier();
		return instance;
	}
	
	/**
	 * Constructs a GameOverNotifier. <p>
	 */
	private GameOverNotifier() {
		this.pcs = new PropertyChangeSupport(this);
	}
	
	/**
	 * Signals 'Game Over' to the appropriate class.
	 * @param score the users score
	 */
	void gameOver() {
		int score = 0;
		
		TagManager tagManager = world.getManager(TagManager.class);
		ComponentMapper<Score> scoreMapper = world.getMapper(Score.class);
		Entity scoreEntity = tagManager.getEntity("SCORE");
		if (scoreMapper != null && scoreEntity != null) {
			score = scoreMapper.get(scoreEntity).getScore();
		}
		pcs.firePropertyChange(EventMessageConstants.GAME_OVER, 0, score);
		System.out.println("GameOverNotifier processed - right?");
	}
	
	/**
	 * Sets the world of this class. <p>
	 * This should be done BEFORE any 'Game Over'-calls are made.
	 * 
	 * @param world the <code>World</code>
	 */
	public void setWorld(World world) {
		this.world = world;
	}
	
	/**
	 * Adds a listener to this class.
	 * @param pcl the listener to add
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		System.out.println("ADDED PROPERTY CHANGE LISTENER");
		pcs.addPropertyChangeListener(pcl);
	}

	/**
	 * Removes a listener from this class.
	 * @param pcl the listener to remove
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
	}
}
