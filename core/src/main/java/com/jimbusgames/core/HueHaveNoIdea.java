package com.jimbusgames.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jimbusgames.core.screens.StartScreen;

public class HueHaveNoIdea extends Game {
	public static final int SCREEN_WIDTH = 1080/2;
	public static final int SCREEN_HEIGHT = 1920/2;
	
	private Screen lastScreen;
	private Skin skin;

	@Override
	public void create() {
		Assets.init();
		this.skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		BitmapFont font = Assets.getFont("aharoni");
		//clobber old one
		skin.add("default", font);
		this.setScreen(new StartScreen(this));
	}

	@Override
	public void dispose () {
		super.dispose();
	}
	
	@Override
	public void setScreen(Screen screen){
		this.lastScreen = this.getScreen();
		super.setScreen(screen);
	}

	public void goBack() {
		this.setScreen(this.lastScreen);
	}

	public Skin getSkin() {
		return skin;
	}
}
