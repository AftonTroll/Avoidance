package se.chalmers.avoidance.core.components;

import com.artemis.Component;

public class Score extends Component {
	
	private int killScore = 0;
	private int powerupScore = 0;
	
	public int getScore(){
		return killScore+powerupScore;
	}
	
	public void addKillScore(int score){
		killScore+=score;
	}
	
	public void addPowerupScore(int score){
		powerupScore+=score;
	}

}
