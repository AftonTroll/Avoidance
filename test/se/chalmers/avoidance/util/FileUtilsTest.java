package se.chalmers.avoidance.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class FileUtilsTest {
	
	private static final String w1 = "Hej";
	private static final String w2 = "då";
	private static final String w3 = "Afton";
	private static final String w4 = "du";
	private static final String w5 = "är";
	private static final String w6 = "allt ";
	private static final String w7 = "enriktig baddare";
	
	private static final int m1 = 1;
	private static final int m2 = 4;
	private static final int m3 = 7;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	private static void assertListEquals(List<?> list, List<?> list2) 
			throws AssertionError {
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
		
		FileUtils.addToFile(w1, path);
		FileUtils.addToFile(w2, path);
		readList = FileUtils.readFromFile(path);
		assertTrue(readList.get(0).equals(w1));
		assertTrue(readList.get(1).equals(w2));
		
		
		FileUtils.addToFile(w5, path);
		FileUtils.addToFile(w3, path);
		FileUtils.addToFile(w4, path);
		readList = FileUtils.readFromFile(path);
		assertTrue(readList.get(0).equals(w1));
		assertTrue(readList.get(1).equals(w2));
		assertTrue(readList.get(2).equals(w5));
		assertTrue(readList.get(3).equals(w3));
		assertTrue(readList.get(4).equals(w4));
		
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
	private static void assertListIsSorted(List<Integer> list) throws AssertionError {
		for (int i = 0; i < list.size() - 1; i++) {
			assertTrue(list.get(i) >= list.get(i+1));
		}
	}

	@Test
	public void testCreateMultiLineStringListOfStringInt() {
		//null reference
		assertTrue("".equalsIgnoreCase(FileUtils.createMultiLineString(null, m1)));

		//no line
		List<String> list = new ArrayList<String>();
		assertTrue("".equalsIgnoreCase(FileUtils.createMultiLineString(list, m1)));

		//1 line
		list.add(w1);
		assertTrue(w1.equalsIgnoreCase(FileUtils.createMultiLineString(list, m1)));

		//3 lines
		list.add(w2);
		list.add(w3);
		String temp = w1 + "\n" + w2 + "\n" + w3;
		assertTrue(temp.equalsIgnoreCase(FileUtils.createMultiLineString(list, m2)));
		assertTrue(w1.equalsIgnoreCase(FileUtils.createMultiLineString(list, m1)));
		assertTrue(!temp.equalsIgnoreCase(FileUtils.createMultiLineString(list, m1))); 
		assertTrue(!temp.equalsIgnoreCase(FileUtils.createMultiLineString(list, m2))); //stops working

		// 7 lines
		list.add(w2);
		list.add(w3); //add those 2 again, since they got removed
		list.add(w4);
		list.add(w5);
		list.add(w6);
		list.add(w7);
		temp += "\n" + w4 + "\n" + w5 + "\n" + w6 + "\n" + w7;
		assertTrue(temp.equalsIgnoreCase(FileUtils.createMultiLineString(list, m3)));
		assertTrue(!temp.equalsIgnoreCase(FileUtils.createMultiLineString(list, m2)));
		assertTrue(!temp.equalsIgnoreCase(FileUtils.createMultiLineString(list, m3))); //stops working
		assertTrue(!temp.equalsIgnoreCase(FileUtils.createMultiLineString(list, m1)));
	}

	@Test
	public void testCreateMultiLineStringListOfString() {
		//null reference
		assertTrue("".equalsIgnoreCase(FileUtils.createMultiLineString(null)));
		
		//no line
		List<String> list = new ArrayList<String>();
		assertTrue("".equalsIgnoreCase(FileUtils.createMultiLineString(list)));

		//1 line
		list.add(w1);
		assertTrue(w1.equalsIgnoreCase(FileUtils.createMultiLineString(list)));
			
		//3 lines
		list.add(w2);
		list.add(w3);
		String temp = w1 + "\n" + w2 + "\n" + w3;
		assertTrue(temp.equalsIgnoreCase(FileUtils.createMultiLineString(list)));
		
		// 7 lines
		list.add(w4);
		list.add(w5);
		list.add(w6);
		list.add(w7);
		temp += "\n" + w4 + "\n" + w5 + "\n" + w6 + "\n" + w7;
		assertTrue(temp.equalsIgnoreCase(FileUtils.createMultiLineString(list)));
	}

}
