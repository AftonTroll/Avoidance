/*
* Copyright (c) 2012 Filip Brynfors
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

import java.util.HashMap;
import java.util.Map;

import org.andengine.audio.music.Music;
import org.andengine.audio.sound.Sound;

/**
 * A manager that stores and plays music and sounds
 * 
 * @author Filip Brynfors
 *
 */
public class AudioManager {
	private static Map<String, Music> musicMap = new HashMap<String, Music>();
	private static Map<String, Sound> soundMap = new HashMap<String, Sound>();
	private static Music currentMusic;
	
	//Hidden constructor for the singleton pattern
	private AudioManager(){
	}
	
	/**
	 * Adds a new music to the manager
	 * @param name the name of the music
	 * @param music the music
	 */
	public static void addMusic(String name, Music music){
		musicMap.put(name, music);
	}
	
	/**
	 * Adds a new sound to the manager
	 * @param name the name of the sound
	 * @param sound the sound
	 */
	public static void addSound(String name, Sound sound){
		soundMap.put(name, sound);
	}
	
	/**
	 * Removes a music from the manager
	 * @param name the name of the music
	 */
	public static void removeMusic(String name){
		musicMap.remove(name);
	}
	
	/**
	 * Removes a sound from the manager
	 * @param name the name of the sound
	 */
	public static void removeSound(String name){
		soundMap.remove(name);
	}
	
	/**
	 * Plays the music with the given name
	 * @param name the name of the music
	 */
	public static void playMusic(String name){		
		//Pause the current music
		if(currentMusic != null){
			currentMusic.pause();
		}
		
		currentMusic = musicMap.get(name);
		//Reset and resume if the music has been played already, else just play it
		if(currentMusic != null){
			if(currentMusic.isPlaying()){
				currentMusic.seekTo(0);
				currentMusic.resume();
			} else {
				currentMusic.play();
			}
		}
	}
	
	/**
	 * Plays the sound with the given name
	 * @param name the name of the sound
	 */
	public static void playSound(String name){
		if(soundMap.containsKey(name)){
			soundMap.get(name).play();	
		}
	}
	
	public static void pause(){
		if(currentMusic != null){
			currentMusic.pause();
		}
	}
	
	public static void resume(){
		if(currentMusic != null){
			currentMusic.resume();
		}
	}
	
}
