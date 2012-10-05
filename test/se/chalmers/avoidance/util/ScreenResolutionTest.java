package se.chalmers.avoidance.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ScreenResolutionTest {
	
	/**
	 * Tests 3 (all) methods of the ScreenResolution-class.
	 * Why? Because they are linked to each other.
	 */
	@Test
	public void testAllMethods() {
		assertTrue(ScreenResolution.getWidthResolution() == 1280);
		assertTrue(ScreenResolution.getHeightResolution() == 800);
		
		//TODO Can't test yet, since we need to use android-code. Move to testproject.
//		Activity activity = new Activity();
//		ScreenResolution.fetchFromActivity(activity);
		
//		assertTrue(ScreenResolution.getWidthResolution() == 0);
//		assertTrue(ScreenResolution.getHeightResolution() == 0);
		
	}

}
