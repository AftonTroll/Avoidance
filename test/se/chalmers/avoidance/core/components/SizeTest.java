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

package se.chalmers.avoidance.core.components;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SizeTest extends FloatTest {

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
		assertFloatEquals(size.getWidth() - 1);
		assertFloatEquals(size.getHeight() - 1);
	}

	@Test
	public void testSizeFloatFloat() {
		Size size = new Size(w1, h1);
		assertTrue(size != null);
		assertFloatEquals(size.getWidth() - w1);
		assertFloatEquals(size.getHeight() - h1);
	}

	@Test
	public void testSetSize() {
		mSize.setSize(w2, h2);
		assertFloatEquals(mSize.getWidth() - w2);
		assertFloatEquals(mSize.getHeight() - h2);
	}
	
	@Test
	public void testWidthSetterAndGetter() {
		// positive value
		mSize.setWidth(w2);
		assertFloatEquals(mSize.getWidth() - w2);
		if (h1 - w2 >= TOLERANCE)
			assertFloatEquals(mSize.getHeight() - h1);
		
		// negative value
		mSize.setWidth(-1);
		assertFloatEquals(mSize.getWidth()); 
		assertFloatEquals(mSize.getHeight() - h1);
		
		// zero value
		mSize.setWidth(0);
		assertFloatEquals(mSize.getWidth()); 
		assertFloatEquals(mSize.getHeight() - h1);
		
	}

	@Test
	public void testWidthHeightSetterAndGetter() {
		// positive value
		mSize.setHeight(h2);
		assertFloatEquals(mSize.getHeight() - h2);
		if (w1 - h2 >= TOLERANCE)
			assertFloatEquals(mSize.getWidth() - w1);
		
		// negative value
		mSize.setHeight(-1);
		assertFloatEquals(mSize.getHeight());
		assertFloatEquals(mSize.getWidth() - w1);
		
		//zero value
		mSize.setHeight(0);
		assertFloatEquals(mSize.getHeight());
		assertFloatEquals(mSize.getWidth() - w1);
		
	}

	@Test
	public void testAddWidth() {
		// add positive
		mSize.addWidth(w2);
		assertFloatEquals(mSize.getWidth() - (w1 + w2));
		assertFloatEquals(mSize.getHeight() - h1); //height unchanged
		
		//-> add negative
		float special = -1; 
		float width = mSize.getWidth() + special;
		mSize.addWidth(special);
		assertFloatEquals(mSize.getWidth() - width); //still positive width
		assertFloatEquals(mSize.getHeight() - h1);
		
		//-> goes towards 0
		mSize.addWidth(-mSize.getWidth());
		assertFloatEquals(mSize.getWidth());
		assertFloatEquals(mSize.getHeight() - h1);
		
		//-> goes below 0
		mSize.addWidth(special);
		assertFloatEquals(mSize.getWidth()); 
		assertFloatEquals(mSize.getHeight() - h1);
	}

	@Test
	public void testAddHeight() {
		// add positive
		mSize.addHeight(h2);
		assertFloatEquals(mSize.getHeight() - (h1 + h2));
		assertFloatEquals(mSize.getWidth() - w1); //height unchanged
		
		//-> add negative
		float special = -1; 
		float height = mSize.getHeight() + special;
		mSize.addHeight(special);
		assertFloatEquals(mSize.getHeight() - height); //still positive width
		assertFloatEquals(mSize.getWidth() - w1);
		
		//-> goes towards 0
		mSize.addHeight(-mSize.getHeight());
		assertFloatEquals(mSize.getHeight());
		assertFloatEquals(mSize.getWidth() - w1);
		
		//-> goes below 0
		mSize.addHeight(special);
		assertFloatEquals(mSize.getHeight()); 
		assertFloatEquals(mSize.getWidth() - w1);
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
