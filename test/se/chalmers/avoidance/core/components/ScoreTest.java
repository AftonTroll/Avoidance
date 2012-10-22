package se.chalmers.avoidance.core.components;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ScoreTest {
	
	private Score score;
	
	@Before
	public void setUp() {
		score = new Score();
	}
	
	@Test
	public void testGetScore(){
		assertTrue(score.getScore()==0);
	}
	
	@Test
	public void testAddKillScore(){
		score.addKillScore(100);
		assertTrue(score.getScore()==100);
	}
	
	@Test
	public void testAddPowerupScore(){
		score.addPowerupScore(200);
		assertTrue(score.getScore()==200);
	}
	
	@Test
	public void testAddBothScore(){
		score.addKillScore(100);
		score.addPowerupScore(200);
		assertTrue(score.getScore()==300);
	}

}
