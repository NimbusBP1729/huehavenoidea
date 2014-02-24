package com.jimbusgames.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jimbusgames.core.HueHaveNoIdea;

public class HueHaveNoIdeaDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = true;
		config.width = 1080/3;
		config.height = 1920/3;
		new LwjglApplication(new HueHaveNoIdea(), config);
	}
}
