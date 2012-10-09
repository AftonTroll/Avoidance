package se.chalmers.avoidance.core.components;

import com.artemis.Component;
/**
 * Component holding the score
 * 
 * @author Jakob Svensson
 *
 */
public class Score extends Component {
	
	private int killScore = 0;
	private int powerupScore = 0;
	
	/**
	 * Returns the current score from killing enemies and picking up power-ups
	 * 
	 * @return the score 
	 */
	public int getScore(){
		return killScore+powerupScore;
	}
	
	/**
	 * Adds score from killing enemies
	 * 
	 * @param score the score to be added
	 */
	public void addKillScore(int score){
		killScore+=score;
	}
	
	/**
	 * Adds score from picking up power-ups
	 * 
	 * @param score the score to be added
	 */
	public void addPowerupScore(int score){
		powerupScore+=score;
	}

}
