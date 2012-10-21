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
 * Util class for saving and reading from a file.<p>
 * 
 * May contain some additional methods that are used
 * for simplifying what we write/read from files in
 * this application.
 * 
 * @author Florian Minges
 */
public class FileUtils {

	private static Context context;
	
	/**
	 * A default file path for storing the high score.
	 */
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
	 * Saves a string to a file.
	 * @param output the string to save
	 * @param path the file path to store the string in
	 */
	public static void saveToFile(String output, String path) {
		if (output == null) {
			output = "";
		}
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
	 * Adds a string to the end of a file, and saves it.
	 * @param output the string to add
	 * @param path the file path to add the string to
	 */
	public static void addToFile(String output, String path) {
		List<String> file = readFromFile(path);
		StringBuilder builder = new StringBuilder();
		builder.append(createMultiLineString(file));
		if (builder.length() != 0) {
			builder.append("\n");
		}
		builder.append(output);
		saveToFile(builder.toString(), path);
	}
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
//	Below comes some code that initially may seem unrelated to a FileUtils-
//	class in general. But as it is used together in this software, I have 
//	chosen to place these methods here as well.
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a list of sorted integers, going from highest to lowest.<p>
	 * Treats the supplied <code>List</code> as integers on string-format.
	 * Returns null if list contains other data than plain numbers on
	 * string-format.
	 * 
	 * @param list a list of strings representing numbers
	 * @return a list of sorted integers, going from highest to lowest
	 */
	public static List<Integer> getSortedIntegers(List<String> list) {
		List<Integer> returnList = new ArrayList<Integer>();

		//convert to integers
		for (int i = 0; i < list.size(); i++) {
			try {
				returnList.add(Integer.valueOf(list.get(i)));
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				return null;
			}
		}
		
		//sort list highest to lowest
		Collections.sort(returnList);
		Collections.reverse(returnList);
		
		return returnList;
	}
	
	
	/**
	 * Creates a multi line string of the given list,
	 * with a given maximum number of lines. <p>
	 * Accepts any type of object.
	 * 
	 * WARNING! The supplied list will be trimmed to the size
	 * of the number of maxLines given in the argument. That is,
	 * if you keep the reference to the list, beware that its size
	 * might have changed.
	 * 
	 * @param listOfObjects the objects to put in a string
	 * @param maxLines the maximum number of lines/strings to use
	 * @return a multi line string of the given list
	 */
	public static String createMultiLineString(List<?> listOfObjects, int maxLines) {
		
		if (listOfObjects == null || listOfObjects.isEmpty()) {
			return "";
		}
		
		StringBuilder output = new StringBuilder();
		
		// limit the list size to maxLines
		Utils.trimList(listOfObjects, maxLines); //<- modifies the list-origin
		
		// create the multi line string
		int lines = listOfObjects.size();
		for (int i = 0; i < lines ; i++) {
			output.append(listOfObjects.get(i).toString());
			if (i != lines - 1) {
				output.append("\n");
			}
		}
		
		return output.toString();
	}

	/**
	 * Creates a multi line string of the given list. <p>
	 * Accepts a <code>List</code> of any type.
	 * 
	 * @param list the objects to put in a string
	 * @return a multi line string of the given list
	 */
	public static String createMultiLineString(List<?> list) {
		if (list == null) {
			return "";
		}
		return createMultiLineString(list, list.size());
	}
	
}
