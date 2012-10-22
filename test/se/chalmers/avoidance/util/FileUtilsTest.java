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
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Avoidance. If not, see <http://www.gnu.org/licenses/>.
*
*/
package se.chalmers.avoidance.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class FileUtilsTest {
	
	private static final String W1 = "Hej";
	private static final String W2 = "då";
	private static final String W3 = "Afton";
	private static final String W4 = "du";
	private static final String W5 = "är";
	private static final String W6 = "allt ";
	private static final String W7 = "enriktig baddare";
	
	private static final int M1 = 1;
	private static final int M2 = 4;
	private static final int M3 = 7;
	
	private static void assertListEquals(List<?> list, List<?> list2) {
		assertTrue(list.size() == list2.size());
		
		for (int i = 0; i < list.size(); i++) {
			assertTrue(list.get(i).equals(list2.get(i)));
		}
	}

	@Test
	public void testSaveAndAddAndReadFromFile() {
		String path = "test.txt";
		
		//store empty list
		List<String> list = new ArrayList<String>();
		FileUtils.saveToFile(FileUtils.createMultiLineString(list), path);
		List<String> readList = FileUtils.readFromFile(path);
		assertListEquals(list, readList);
		
		//add some elements
		list.add("1392");
		list.add("983");
		list.add("1198");
		list.add("1835");
		list.add("444");
		FileUtils.saveToFile(FileUtils.createMultiLineString(list), path);
		readList = FileUtils.readFromFile(path);
		assertListEquals(list, readList);
		
		//remove some elements
		list.remove(0);
		list.remove(1);
		FileUtils.saveToFile(FileUtils.createMultiLineString(list), path);
		readList = FileUtils.readFromFile(path);
		assertListEquals(list, readList);
		
		//add some more elements
		list.add("tja");
		list.add("339");
		list.add("hejhej");
		FileUtils.saveToFile(FileUtils.createMultiLineString(list), path);
		readList = FileUtils.readFromFile(path);
		assertListEquals(list, readList);
		
		//try to save null-reference
		FileUtils.saveToFile(null, path);
		readList = FileUtils.readFromFile(path);
		assertTrue(readList.isEmpty());
		
		
		//add strings
		
		FileUtils.addToFile(W1, path);
		FileUtils.addToFile(W2, path);
		readList = FileUtils.readFromFile(path);
		assertTrue(readList.get(0).equals(W1));
		assertTrue(readList.get(1).equals(W2));
		
		
		FileUtils.addToFile(W5, path);
		FileUtils.addToFile(W3, path);
		FileUtils.addToFile(W4, path);
		readList = FileUtils.readFromFile(path);
		assertTrue(readList.get(0).equals(W1));
		assertTrue(readList.get(1).equals(W2));
		assertTrue(readList.get(2).equals(W5));
		assertTrue(readList.get(3).equals(W3));
		assertTrue(readList.get(4).equals(W4));
		
	}

	@Test
	public void testGetSortedIntegers() {
		List<Integer> facit = new ArrayList<Integer>();
		facit.add(1);
		facit.add(993);
		facit.add(191);
		facit.add(88);
		facit.add(1176);
		facit.add(12);
		List<String> list = new ArrayList<String>();
		
		list.add(String.valueOf(facit.get(0)));
		assertListIsSorted(FileUtils.getSortedIntegers(list));
		
		list.add(String.valueOf(facit.get(1)));
		list.add(String.valueOf(facit.get(2)));
		assertListIsSorted(FileUtils.getSortedIntegers(list));
		
		list.add(String.valueOf(facit.get(3)));
		list.add(String.valueOf(facit.get(4)));
		list.add(String.valueOf(facit.get(5)));
		assertListIsSorted(FileUtils.getSortedIntegers(list));
	}
	
	/**
	 * Helper-method for telling if a list of <code>Integer</code> 
	 * is sorted from highest to lowest.
	 * @param list the list to check
	 * @throws AssertionError if it is not sorted from highest to lowest
	 */
	private static void assertListIsSorted(List<Integer> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			assertTrue(list.get(i) >= list.get(i+1));
		}
	}

	@Test
	public void testCreateMultiLineStringListOfStringInt() {
		//null reference
		assertTrue("".equalsIgnoreCase(FileUtils.createMultiLineString(null, M1)));

		//no line
		List<String> list = new ArrayList<String>();
		assertTrue("".equalsIgnoreCase(FileUtils.createMultiLineString(list, M1)));

		//1 line
		list.add(W1);
		assertTrue(W1.equalsIgnoreCase(FileUtils.createMultiLineString(list, M1)));

		//3 lines
		list.add(W2);
		list.add(W3);
		String temp = W1 + "\n" + W2 + "\n" + W3;
		assertTrue(temp.equalsIgnoreCase(FileUtils.createMultiLineString(list, M2)));
		assertTrue(W1.equalsIgnoreCase(FileUtils.createMultiLineString(list, M1)));
		assertTrue(!temp.equalsIgnoreCase(FileUtils.createMultiLineString(list, M1))); 
		assertTrue(!temp.equalsIgnoreCase(FileUtils.createMultiLineString(list, M2))); //stops working

		// 7 lines
		list.add(W2);
		list.add(W3); //add those 2 again, since they got removed
		list.add(W4);
		list.add(W5);
		list.add(W6);
		list.add(W7);
		temp += "\n" + W4 + "\n" + W5 + "\n" + W6 + "\n" + W7;
		assertTrue(temp.equalsIgnoreCase(FileUtils.createMultiLineString(list, M3)));
		assertTrue(!temp.equalsIgnoreCase(FileUtils.createMultiLineString(list, M2)));
		assertTrue(!temp.equalsIgnoreCase(FileUtils.createMultiLineString(list, M3))); //stops working
		assertTrue(!temp.equalsIgnoreCase(FileUtils.createMultiLineString(list, M1)));
	}

	@Test
	public void testCreateMultiLineStringListOfString() {
		//null reference
		assertTrue("".equalsIgnoreCase(FileUtils.createMultiLineString(null)));
		
		//no line
		List<String> list = new ArrayList<String>();
		assertTrue("".equalsIgnoreCase(FileUtils.createMultiLineString(list)));

		//1 line
		list.add(W1);
		assertTrue(W1.equalsIgnoreCase(FileUtils.createMultiLineString(list)));
			
		//3 lines
		list.add(W2);
		list.add(W3);
		String temp = W1 + "\n" + W2 + "\n" + W3;
		assertTrue(temp.equalsIgnoreCase(FileUtils.createMultiLineString(list)));
		
		// 7 lines
		list.add(W4);
		list.add(W5);
		list.add(W6);
		list.add(W7);
		temp += "\n" + W4 + "\n" + W5 + "\n" + W6 + "\n" + W7;
		assertTrue(temp.equalsIgnoreCase(FileUtils.createMultiLineString(list)));
	}

}
