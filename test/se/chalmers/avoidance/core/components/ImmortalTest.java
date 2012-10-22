package se.chalmers.avoidance.core.components;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class ImmortalTest {
    private static int duration;
    
    @BeforeClass
    public static void beforeClass() {
        duration = 2;
    }
    
    @Test
    public void testImmortal() {
        Immortal immortal = new Immortal();
        assertTrue(immortal != null);
        
        assertTrue(immortal.getDurationLeft() == 0);
        immortal.setDuration(duration);
        immortal.setImmortal(true);
        assertTrue(immortal.getDurationLeft() == duration);
        immortal.subtractImmortalDurationLeft(1);
        assertTrue(immortal.getDurationLeft() == duration - 1);
    }
}
