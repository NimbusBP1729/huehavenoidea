package com.jimbusgames.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader.BitmapFontParameter;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	private static AssetManager manager;

	public static TextureRegion loadTexture(String file) {
		TextureParameter param = new TextureParameter();
		param.magFilter = TextureFilter.Linear;
		param.minFilter = TextureFilter.Linear;
		manager.load(file, Texture.class, param);
		manager.finishLoading();
		TextureRegion texture = new TextureRegion(manager.get(file, Texture.class));
		texture.flip(false, true);
		return texture;
	}
	
	public static void dispose() {
		manager.dispose();
	}
	
	public static void init() {
		manager = new AssetManager();
	}

	public static BitmapFont getFont(String file) {
		String fullName = "fonts/"+file+".fnt";
		BitmapFontParameter param = new BitmapFontParameter();
		param.magFilter = TextureFilter.Linear;
		param.minFilter = TextureFilter.Linear;
		manager.load(fullName, BitmapFont.class, param);
		manager.finishLoading();
		return manager.get(fullName, BitmapFont.class);
	}
}
