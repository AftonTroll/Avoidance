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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testSaveAndReadFromFile() {
		String path = "hej.txt";
		List<String> list = new ArrayList<String>();
		list.add("1392");
		list.add("983");
		list.add("1198");
		list.add("1835");
		list.add("444");
		FileUtils.saveToFile(list, path);
		List<String> newList = FileUtils.readFromFile(path);
		
		for (int i = 0; i < list.size(); i++) {
			assertTrue(list.get(i).equalsIgnoreCase(newList.get(i)));
		}
	}

	@Test
	public void testSortList() {
		
	}

	@Test
	public void testCreateMultiLineStringListOfStringInt() {
		
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
