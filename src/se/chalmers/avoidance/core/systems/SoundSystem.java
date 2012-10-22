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

package se.chalmers.avoidance.core.systems;

import se.chalmers.avoidance.core.components.Sound;
import se.chalmers.avoidance.util.AudioManager;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

/**
 * System that handles the playing of sounds.
 * 
 * @author Filip Brynfors
 *
 */
public class SoundSystem extends EntityProcessingSystem {
	private ComponentMapper<Sound> soundMapper;
	
	/**
	 * Constructs a new SoundSystem.
	 */
	public SoundSystem() {
		super(Aspect.getAspectForAll(Sound.class));
	}
	
	/**
	 * This method is called when the system is initialized.
	 */
	@Override
	protected void initialize(){
		soundMapper = world.getMapper(Sound.class);
	}

	/**
	 * This method is called when the sound is to be updated.
	 * Plays all sounds that are supposed to be played.
	 * @param entity an entity with the Sound component
	 */
	@Override
	protected void process(Entity entity) {
		Sound sound = soundMapper.get(entity);
		if(sound.isPlaying()){
			AudioManager.getInstance().playSound(sound.getName());
			sound.setPlaying(false);
		}
	}

}
