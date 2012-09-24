package se.chalmers.avoidance.components;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.chalmers.avoidance.util.Utils;

public class TransformTest {
	
	private static float x1, x2;
	private static float y1, y2;
	private static float d1, d2;
	
	private Transform mTransform;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		x1 = 3;
		x2 = 5;
		y1 = 7;
		y2 = 8;
		d1 = 1;
		d2 = 2;
	}

	@Before
	public void setUp() throws Exception {
		mTransform = new Transform(x1, y1, d1);
	}

	@Test
	public void testTransform() {
		Transform transform = new Transform();
		assertTrue(transform.getX() == 0);
		assertTrue(transform.getY() == 0);
		assertTrue(transform.getDirection() == 0);
	}

	@Test
	public void testTransformFloatFloat() {
		Transform transform = new Transform(x2, y2);
		assertTrue(transform.getX() == x2);
		assertTrue(transform.getY() == y2);
		assertTrue(transform.getDirection() == 0);
	}

	@Test
	public void testTransformFloatFloatFloat() {
		Transform transform = new Transform(x2, y2, d2);
		assertTrue(transform.getX() == x2);
		assertTrue(transform.getY() == y2);
		assertTrue(transform.getDirection() == d2);
	}
	
	@Test
	public void testXSetterAndGetter() {
		// set positive number
		mTransform.setX(x2);
		assertTrue(mTransform.getX() == x2);
		assertTrue(mTransform.getY() == y1);
		assertTrue(mTransform.getDirection() == d1);
		
		// test to set 0 as value
		mTransform.setX(0);
		assertTrue(mTransform.getX() == 0);
		assertTrue(mTransform.getY() == y1);
		assertTrue(mTransform.getDirection() == d1);

		// test to set negative values
		mTransform.setX(-2);
		assertTrue(mTransform.getX() == -2);
		assertTrue(mTransform.getY() == y1);
		assertTrue(mTransform.getDirection() == d1);
	}
	
	@Test
	public void testYSetterAndGetter() {
		// set positive number
		mTransform.setY(y2);
		assertTrue(mTransform.getX() == x1);
		assertTrue(mTransform.getY() == y2);
		assertTrue(mTransform.getDirection() == d1);
		
		// test to set 0 as value
		mTransform.setY(0);
		assertTrue(mTransform.getX() == x1);
		assertTrue(mTransform.getY() == 0);
		assertTrue(mTransform.getDirection() == d1);

		// test to set negative values
		mTransform.setY(-2);
		assertTrue(mTransform.getX() == x1);
		assertTrue(mTransform.getY() == -2);
		assertTrue(mTransform.getDirection() == d1);
	}
	
	@Test
	public void testDirectionSetterAndGetter() {
		// add positive number
		mTransform.setDirection(d2);
		assertTrue(mTransform.getX() == x1);
		assertTrue(mTransform.getY() == y1);
		assertTrue(mTransform.getDirection() == d2);
		
		// test to set 0 as value
		mTransform.setDirection(0);
		assertTrue(mTransform.getX() == x1);
		assertTrue(mTransform.getY() == y1);
		assertTrue(mTransform.getDirection() == 0);

		// test to set negative values
		mTransform.setDirection(-2);
		assertTrue(mTransform.getX() == x1);
		assertTrue(mTransform.getY() == y1);
		System.out.println(Math.abs((mTransform.getDirection() - Utils.simplifyAngle(-2))));
		System.out.println(2f / 10000f);
		assertTrue(Math.abs((mTransform.getDirection() - Utils.simplifyAngle(-2))) < (2f / 10000f));
	}

	@Test
	public void testSetPosition() {
		// positive values
		mTransform.setPosition(x2, y2);
		assertTrue(mTransform.getX() == x2);
		assertTrue(mTransform.getY() == y2);
		assertTrue(mTransform.getDirection() == d1);

		// 0 values
		mTransform.setPosition(0, 0);
		assertTrue(mTransform.getX() == 0);
		assertTrue(mTransform.getY() == 0);
		assertTrue(mTransform.getDirection() == d1);

		// negative values
		mTransform.setPosition(-2, 2);
		assertTrue(mTransform.getX() == -2);
		assertTrue(mTransform.getY() == 2);
		assertTrue(mTransform.getDirection() == d1);
		
		mTransform.setPosition(2, -2);
		assertTrue(mTransform.getX() == 2);
		assertTrue(mTransform.getY() == -2);
		assertTrue(mTransform.getDirection() == d1);
	}

	@Test
	public void testTranslateX() {
		//add positive number
		mTransform.translateX(x2);
		assertTrue(mTransform.getX() == (x1 + x2));
		assertTrue(mTransform.getY() == y1);
		assertTrue(mTransform.getDirection() == d1);
		
		// test to set 0 as value
		mTransform.translateX(0);
		assertTrue(mTransform.getX() == (x1 + x2));
		assertTrue(mTransform.getY() == y1);
		assertTrue(mTransform.getDirection() == d1);

		// test to set negative values
		mTransform.translateX(-2);
		assertTrue(mTransform.getX() == (x1 + x2) - 2);
		assertTrue(mTransform.getY() == y1);
		assertTrue(mTransform.getDirection() == d1);
	}

	@Test
	public void testTranslateY() {
		//add positive number
		mTransform.translateY(y2);
		assertTrue(mTransform.getX() == x1);
		assertTrue(mTransform.getY() == (y1 + y2));
		assertTrue(mTransform.getDirection() == d1);

		// add 0 
		mTransform.translateY(0);
		assertTrue(mTransform.getX() == x1);
		assertTrue(mTransform.getY() == (y1 + y2));
		assertTrue(mTransform.getDirection() == d1);

		// add negative value
		mTransform.translateY(-2);
		assertTrue(mTransform.getX() == x1);
		assertTrue(mTransform.getY() == (y1 + y2) - 2);
		assertTrue(mTransform.getDirection() == d1);
	}

	@Test
	public void testTranslateDirection() {
		// add positive number
		mTransform.translateDirection(d2);
		assertTrue(mTransform.getX() == x1);
		assertTrue(mTransform.getY() == y1);
		assertTrue(mTransform.getDirection() == (d1 + d2));

		// add 0 
		mTransform.translateDirection(0);
		assertTrue(mTransform.getX() == x1);
		assertTrue(mTransform.getY() == y1);
		assertTrue(mTransform.getDirection() == (d1 + d2));

		// add negative value
		mTransform.translateDirection(-2);
		assertTrue(mTransform.getX() == x1);
		assertTrue(mTransform.getY() == y1);
		assertTrue(mTransform.getDirection() == (d1 + d2) - 2);
	}

	@Test
	public void testEqualsAndHashCode() {
		Transform oTransform = new Transform(x1, y1, d1);
		assertTrue(mTransform.equals(oTransform));
		assertTrue(mTransform.hashCode() == oTransform.hashCode());
		
		oTransform.setDirection(d2);
		assertTrue(!mTransform.equals(oTransform));
		
		oTransform.setPosition(y1, x1);
		oTransform.setDirection(d1);
		assertTrue(!mTransform.equals(oTransform));
	}

}
