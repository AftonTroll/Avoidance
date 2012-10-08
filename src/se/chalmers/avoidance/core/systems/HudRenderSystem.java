/* 
 * Copyright (c) 2012 Jakob Svensson
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

package se.chalmers.avoidance.core.systems;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import se.chalmers.avoidance.core.components.Score;
import se.chalmers.avoidance.core.components.Time;
import se.chalmers.avoidance.util.ScreenResolution;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

/**
 * System for handling rendering of the heads-up display * 
 * 
 * @author Jakob Svensson
 *
 */
public class HudRenderSystem extends EntityProcessingSystem{
	
	private Font font;
	private Text scoreText;
	private Text timeText;
	private Scene scene;
	private VertexBufferObjectManager vbom;
	private ComponentMapper<Time> timeMapper;
	private ComponentMapper<Score> scoreMapper;
	
	/**
	 * Construct a new HudRenderSystem
	 * 
	 * @param scene The scene of the game
	 * @param vbom A VertexBufferObjectManager
	 * @param font The font of the score text
	 */
	public HudRenderSystem(Scene scene, VertexBufferObjectManager vbom, Font font) {
		super(Aspect.getAspectForAll(Time.class,Score.class));
		this.font = font;	
		this.scene = scene;
		this.vbom = vbom;
	}
	
	/**
	 * This method is called when the system is initialized
	 */
	@Override
	protected void initialize(){
		timeMapper = world.getMapper(Time.class);
		scoreMapper = world.getMapper(Score.class);
		scoreText = new Text(ScreenResolution.getWidthResolution()*4/5, 30, this.font, "Score :", "Score: XXXXX".length(), vbom);
		timeText = new Text(30, 30, this.font, "Time :", "Time: XXXXX".length(), vbom);
		scene.attachChild(scoreText);
		scene.attachChild(timeText);
		scoreText.setZIndex(100);
		timeText.setZIndex(100);
	}
	
	/**
	 * Determines if the system should be processed or not
	 * 
	 * @return true if system should be processed, false if not	
	 */
	@Override
	protected boolean checkProcessing() {
		return true;
	}
	
	/**
	 * Processes entities
	 * 
	 * @param entity the entity to be processed
	 */
	@Override
	protected void process(Entity entity) {
		Time time = timeMapper.get(entity);
		Score score = scoreMapper.get(entity);
		time.updateTime(world.getDelta());
		timeText.setText("Time: "+Math.round(time.getTime())/60+"m "+ Math.round(time.getTime())%60+"s");
		scoreText.setText("Score :"+(Math.round(time.getTime())*10+score.getScore()));
	}

}
