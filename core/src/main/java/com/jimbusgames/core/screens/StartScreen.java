package com.jimbusgames.core.screens;

import static com.jimbusgames.core.HueHaveNoIdea.SCREEN_HEIGHT;
import static com.jimbusgames.core.HueHaveNoIdea.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jimbusgames.core.Assets;
import com.jimbusgames.core.HueHaveNoIdea;

public class StartScreen implements Screen {
	Stage stage;
	private SpriteBatch batch = new SpriteBatch();
	private OrthographicCamera cam;
	
	public StartScreen(final HueHaveNoIdea game){
		batch = new SpriteBatch();
		cam = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
		cam.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		cam.zoom = 1;
		cam.update(true);
		batch.setProjectionMatrix(cam.combined);
		
		stage = new Stage(SCREEN_WIDTH, SCREEN_HEIGHT, true);


		Skin skin = game.getSkin();
		
		TextButton startButton = new TextButton("START", skin);
		startButton.addListener(new ClickListener(){
			@Override
			public void clicked (InputEvent event, float x, float y) {
				game.setScreen(new GameplayScreen(game));
			}
		});
		
		TextButton infoButton = new TextButton("HOW TO", skin);
		infoButton.addListener(new ClickListener(){
			@Override
			public void clicked (InputEvent event, float x, float y) {
				game.setScreen(new InfoScreen(game));
			}
		});
		
		Table table = new Table(game.getSkin());
		//table.debug();
        table.setFillParent(true);
		table.add("Hue Have\n No Idea","title",Color.BLUE).padBottom(SCREEN_HEIGHT/100);
		table.row();
		
		table.add(startButton).padBottom(SCREEN_HEIGHT/100);
		table.row();
		table.add(infoButton);
		
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		//updates
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		
		//drawing
		Gdx.gl.glClearColor(0, 0, 0, 0.01f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setColor(Color.WHITE);
		batch.draw(Assets.loadTexture("background.png"), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		batch.end();


		stage.draw();
		Table.drawDebug(stage);

	}

	@Override
	public void resize(int width, int height) {	
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
