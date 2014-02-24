package com.jimbusgames.core.screens;

import static com.jimbusgames.core.HueHaveNoIdea.SCREEN_HEIGHT;
import static com.jimbusgames.core.HueHaveNoIdea.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jimbusgames.core.Assets;
import com.jimbusgames.core.HueHaveNoIdea;

public class InfoScreen implements Screen {
	Stage stage;
	private SpriteBatch batch = new SpriteBatch();
	private BitmapFont font;
	private OrthographicCamera cam;
	
	public InfoScreen(final HueHaveNoIdea game){
		batch = new SpriteBatch();
		cam = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
		cam.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		cam.zoom = 1;
		cam.update(true);
		batch.setProjectionMatrix(cam.combined);
		
		font = Assets.getFont("aharoni");

		stage = new Stage(SCREEN_WIDTH, SCREEN_HEIGHT, true);

		Skin skin = game.getSkin();
		
		TextButton startButton = new TextButton("BACK", skin);
		startButton.addListener(new ClickListener(){
			@Override
			public void clicked (InputEvent event, float x, float y) {
				game.setScreen(new StartScreen(game));
			}
		});
		
		Table table = new Table();
		//table.debug();
        table.setFillParent(true);
		table.add(startButton);
		
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
		font.setColor(Color.BLUE);
		font.drawWrapped(batch, "Click the screen when the big square is the same color as the little square"
				, 0, 0.4f*SCREEN_HEIGHT, SCREEN_WIDTH, HAlignment.CENTER);
		
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
