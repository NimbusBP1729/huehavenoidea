package com.jimbusgames.html;

import com.jimbusgames.core.HueHaveNoIdea;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class HueHaveNoIdeaHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new HueHaveNoIdea();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
