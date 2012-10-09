package se.chalmers.avoidance.core.components;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TimeTest {
	
	private Time time;
	
	@Before
	public void setUp() throws Exception {
		time = new Time();
	}

	@Test
	public void testGetTime() {
		assertTrue(time.getTime()==0);
	}
	
	@Test
	public void testUpdateTime(){
		time.updateTime(3);
		assertTrue(time.getTime()==3);
	}
}
