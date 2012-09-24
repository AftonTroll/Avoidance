package se.chalmers.avoidance.components;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SizeTest {

	private static float w1;
	private static float h1;
	private static float w2;
	private static float h2;
	
	private Size mSize;
	
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		w1 = 2f;
		h1 = 3f;
		w2 = 4f;
		h2 = 5f;
	}
	
	@Before
	public void setUp() throws Exception {
		this.mSize = new Size(w1, h1);
	}
	

	@Test
	public void testSize() {
		Size size = new Size();
		assertTrue(size != null);
		assertTrue(size.getWidth() == 1);
		assertTrue(size.getHeight() == 1);
	}

	@Test
	public void testSizeFloatFloat() {
		Size size = new Size(w1, h1);
		assertTrue(size != null);
		assertTrue(size.getWidth() == w1);
		assertTrue(size.getHeight() == h1);
	}

	@Test
	public void testSetSize() {
		mSize.setSize(w2, h2);
		assertTrue(mSize.getWidth() == w2);
		assertTrue(mSize.getHeight() == h2);
	}
	
	@Test
	public void testWidthSetterAndGetter() {
		// positive value
		mSize.setWidth(w2);
		assertTrue(mSize.getWidth() == w2);
		if (h1 != w2)
			assertTrue(mSize.getHeight() == h1);
		
		// negative value
		mSize.setWidth(-1);
		assertTrue(mSize.getWidth() == 0); 
		assertTrue(mSize.getHeight() == h1);
		
		// zero value
		mSize.setWidth(0);
		assertTrue(mSize.getWidth() == 0); 
		assertTrue(mSize.getHeight() == h1);
		
	}

	@Test
	public void testWidthHeightSetterAndGetter() {
		// positive value
		mSize.setHeight(h2);
		assertTrue(mSize.getHeight() == h2);
		if (w1 != h2)
			assertTrue(mSize.getWidth() == w1);
		
		// negative value
		mSize.setHeight(-1);
		assertTrue(mSize.getHeight() == 0);
		assertTrue(mSize.getWidth() == w1);
		
		//zero value
		mSize.setHeight(0);
		assertTrue(mSize.getHeight() == 0);
		assertTrue(mSize.getWidth() == w1);
		
	}

	@Test
	public void testAddWidth() {
		// add positive
		mSize.addWidth(w2);
		assertTrue(mSize.getWidth() == (w1 + w2));
		assertTrue(mSize.getHeight() == h1); //height unchanged
		
		//-> add negative
		float special = -1; 
		float width = mSize.getWidth() + special;
		mSize.addWidth(special);
		assertTrue(mSize.getWidth() == width); //still positive width
		assertTrue(mSize.getHeight() == h1);
		
		//-> goes towards 0
		mSize.addWidth(-mSize.getWidth());
		assertTrue(mSize.getWidth() == 0);
		assertTrue(mSize.getHeight() == h1);
		
		//-> goes below 0
		mSize.addWidth(special);
		assertTrue(mSize.getWidth() == 0); 
		assertTrue(mSize.getHeight() == h1);
	}

	@Test
	public void testAddHeight() {
		// add positive
		mSize.addHeight(h2);
		assertTrue(mSize.getHeight() == (h1 + h2));
		assertTrue(mSize.getWidth() == w1); //height unchanged
		
		//-> add negative
		float special = -1; 
		float height = mSize.getHeight() + special;
		mSize.addHeight(special);
		assertTrue(mSize.getHeight() == height); //still positive width
		assertTrue(mSize.getWidth() == w1);
		
		//-> goes towards 0
		mSize.addHeight(-mSize.getHeight());
		assertTrue(mSize.getHeight() == 0);
		assertTrue(mSize.getWidth() == w1);
		
		//-> goes below 0
		mSize.addHeight(special);
		assertTrue(mSize.getHeight() == 0); 
		assertTrue(mSize.getWidth() == w1);
	}

	@Test
	public void testEqualsAndHashCode() {
		Size oSize = new Size(w1, h1);
		assertTrue(mSize.equals(oSize));
		assertTrue(mSize.hashCode() == oSize.hashCode());
		
		oSize.setWidth(h1);
		oSize.setHeight(w1);
		assertTrue(!mSize.equals(oSize));
	}

}
