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

package se.chalmers.avoidance.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;

/**
 * Util class for saving and reading from a file.
 * 
 * @author Florian Minges
 */
public class FileUtils {
	
	//TODO Make completely static
	private static Context context;
	public static final String PATH = "highscore.txt";
	
	private FileUtils() {}
	
	/**
	 * Sets the context of this class.<p>
	 * This means that reading from and writing to files are made to private files
	 * associated with this Context's application package.
	 * 
	 * @param context the context
	 */
	public static void setContext(Context context) {
		FileUtils.context = context;
	}

	/**
	 * Reads a file, line by line, and returns the result as a <code>List</code>.
	 * 
	 * @param path the path to the file
	 * @return a <code>List</code> of all lines that are saved in the file
	 */
	public static List<String> readFromFile(String path) {
		List<String> returnList = new ArrayList<String>();
		try {
			FileInputStream fis = context != null ? context.openFileInput(path) : 
				new FileInputStream(path);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
			String row;
			while ((row = bufferedReader.readLine()) != null) {
				returnList.add(row);
			}
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	
	/**
	 * Saves a list of <code>Strings</code> to a file
	 * 
	 * @param list the list to save
	 * @param path the file path to store the list at
	 */
	public static void saveToFile(List<String> list, String path) {
		// Put the output into a string
		String output = createMultiLineString(list);
		
		try {
			// Save the file
			FileOutputStream fos = context != null ? 
					context.openFileOutput(path, Context.MODE_PRIVATE) : 
						new FileOutputStream(path);
			fos.write(output.toString().getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a multi line string with a sorted list of integers (including rank).<p>
	 * Sorts a list of integers that are stored as strings in a list. Adds a rank as
	 * prefix and puts it all into a multi line string which is returned.
	 * 
	 * @param list a <code>List<String></code> containing Integers on string-format
	 * @param maxLines the maximum number of lines
	 * @return a sorted and ranked multi line string
	 */
	public static String sortList(List<String> list, int maxLines) {
		List<Integer> numbers = getSortedIntegers(list, maxLines);
		List<String> rankedList = getRankedList(numbers);
		
		String sortedList = createMultiLineString(rankedList);
		return sortedList;
	}
	
	
	/**
	 * Returns a list of sorted integers, going from lowest to highest.<p>
	 * Treats the supplied <code>List</code> as integers on string-format.
	 * Returns null if list contains other data than plain numbers on
	 * string-format.
	 * 
	 * @param list a list of strings representing numbers
	 * @param maxLines the maximum number of lines/elements to return
	 * @return a list of sorted integers, going from lowest to highest
	 */
	private static List<Integer> getSortedIntegers(List<String> list, int maxLines) {
		List<Integer> returnList = new ArrayList<Integer>();
		
		for (String string : list) {
			try {
				returnList.add(Integer.valueOf(string));
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				return null;
			}
		}
		
		Collections.sort(returnList);
		if (returnList.size() > maxLines) {
			return returnList.subList(returnList.size() - 5, returnList.size());
		}
		return returnList;
	}
	
	/**
	 * Returns a list of strings, with a rank as prefix for the integers.
	 * The last element in the supplied list will appear first in the
	 * returned list.
	 * 
	 * @param list a sorted list of <code>Integers</code>
	 * @return a list of strings with info about rank and value
	 */
	private static List<String> getRankedList(List<Integer> list) {
		List<String> returnList = new ArrayList<String>();
		int size = list.size();
		for (int i = 1; i <= size; i++) {
			String s = i + ")  " + list.get(size - i);
			returnList.add(s);
		}
		
		return returnList;
	}
	
	/**
	 * Creates a multi line string of the given list,
	 * with a given maximum number of lines.
	 * 
	 * @param list the list to put in a string
	 * @param maxLines the maximum number of lines/strings to use
	 * @return a multi line string of the given list
	 */
	public static String createMultiLineString(List<String> list, int maxLines) {
		if (list == null)
			return "";
		StringBuilder output = new StringBuilder();
		// limit the list size to maxLines
		if (list.size() > maxLines) {
			list = list.subList(list.size() - maxLines, list.size());
		} 
		
		// create highscoreList
		int lines = list.size();
		for (int i = 0; i < lines ; i++) {
			output.append(list.get(i));
			if (i != lines - 1) {
				output.append("\n");
			}
		}
		
		return output.toString();
	}

	/**
	 * Creates a multi line string of the given list.
	 * 
	 * @param list the list to put in a string
	 * @return a multi line string of the given list
	 */
	public static String createMultiLineString(List<String> list) {
		if (list == null)
			return "";
		return createMultiLineString(list, list.size());
	}
	
}
