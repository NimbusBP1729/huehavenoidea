package com.jimbusgames.core.screens;

import static com.jimbusgames.core.HueHaveNoIdea.SCREEN_HEIGHT;
import static com.jimbusgames.core.HueHaveNoIdea.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.jimbusgames.core.HueHaveNoIdea;

public class GameplayScreen implements Screen {

	protected static final float MAX_ERROR = 1;
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private float goalColor = 0.7f;
	private float currentColor;
	private boolean doIncrement;
	private float eps = 0.01f;
	private boolean stopped;
	private BitmapFont font;
	private Stage stage;
	private long level = 1;
	private long highestLevel;
	private float error = 0;
	private Json json;
	private FileHandle jsonFile;
	private boolean gameOver = false;

	public GameplayScreen(HueHaveNoIdea game) {
		batch = new SpriteBatch();
		stopped = false;
		cam = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
		cam.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		cam.zoom = 1;
		cam.update(true);
		batch.setProjectionMatrix(cam.combined);
		
		font = Assets.getFont("aharoni");
		
		json = new Json();
		json.setOutputType(OutputType.json);
		try{
			jsonFile = Gdx.files.external("jimbus/games/hue/highscores.json");
			JsonValue highScoreVal = new JsonReader().parse(jsonFile);
			highestLevel = Long.valueOf(highScoreVal.getLong("longValue"));
		}catch(Exception e){
		}

		
		stage = new Stage(cam.viewportWidth, cam.viewportHeight, true);
		stage.addListener(new ClickListener(){

			@Override
			public void clicked (InputEvent event, float x, float y) {
				if(stopped){
					stopped = false;
					error += calculateDiff();
					level++;
					currentColor = MathUtils.random();
					goalColor = MathUtils.random();
					if (level > highestLevel) {
						highestLevel = level;
						try {
							JsonValue type = new JsonValue(highestLevel);
							type.setName("high_score");
							jsonFile.writeString(json.prettyPrint(type), false);
						} catch (Exception e) {
						}
					}
					if(error >= MAX_ERROR){
						this.failure();
					}
				}else{
					stopped = true;
				}
			}

			private void failure() {
				gameOver = true;
				stage.addListener(new ClickListener(){
					@Override
					public void clicked (InputEvent event, float x, float y) {
						gameOver = false;
						stopped = false;
						stage.removeListener(this);
						reset();
					}
				});
			}
		});
				
		currentColor = MathUtils.random();
	}
	
	public void reset(){
		this.level = 1;
		this.error = 0;
	}

	@Override
	public void render(float delta) {
		//updates
		//cam.update(true);
		if (!stopped) {
			if (currentColor <= 0) {
				doIncrement = true;
			} else if (currentColor >= 1) {
				doIncrement = false;
			}

			if (doIncrement) {
				currentColor += eps;
			} else {
				currentColor -= eps;
			}

			currentColor = MathUtils.clamp(currentColor, 0, 1);
		}
		
		//drawing
		Gdx.gl.glClearColor(0, 0, 0, 0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.setColor(Color.WHITE);
		batch.draw(Assets.loadTexture("background.png"), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		batch.setColor(0, 0, 0, 0.5f);
		TextureRegion setting = Assets.loadTexture("white.png");
		int settingW = 3*Math.min(SCREEN_WIDTH, SCREEN_HEIGHT)/6;
		int settingH = SCREEN_HEIGHT/2;
		batch.draw(setting, SCREEN_WIDTH/2-settingW/2, SCREEN_HEIGHT/6, settingW, settingH);
		
		batch.setColor(0, 0, currentColor, 1);
		TextureRegion current = Assets.loadTexture("white.png");
		int currentW = Math.min(SCREEN_WIDTH, SCREEN_HEIGHT)/4;
		int currentH = currentW;
		batch.draw(current, SCREEN_WIDTH/2-currentW/2, SCREEN_HEIGHT/2-currentH/2, currentW, currentH);

		batch.setColor(0, 0, goalColor, 1);
		TextureRegion goal = Assets.loadTexture("white.png");
		int goalW = Math.min(SCREEN_WIDTH, SCREEN_HEIGHT)/8;
		int goalH = goalW;
		batch.draw(goal, SCREEN_WIDTH/2-goalW/2, SCREEN_HEIGHT/4-goalH/2, goalW, goalH);

		font.setColor(Color.BLUE);
		font.drawMultiLine(batch, "Level: "+level+"\n"+
				"Error: "+ error+"\n"+
				"Highest Level: "+ highestLevel+"\n"
				, 0, SCREEN_HEIGHT, SCREEN_WIDTH, HAlignment.LEFT);
		
		TextureRegion good = Assets.loadTexture("white.png");
		TextureRegion bad = Assets.loadTexture("white.png");
		batch.setColor(Color.BLUE);
		batch.draw(good, 0.70f*SCREEN_WIDTH, 0.95f*SCREEN_HEIGHT, SCREEN_WIDTH/4,SCREEN_HEIGHT/100);
		batch.setColor(Color.RED);
		batch.draw(bad,  0.70f*SCREEN_WIDTH, 0.95f*SCREEN_HEIGHT, Math.min(error,1)*SCREEN_WIDTH/4,SCREEN_HEIGHT/100);

		if(stopped){
			float diff = calculateDiff();
			font.drawMultiLine(batch, "Click to advance", 0, 6.5f*SCREEN_HEIGHT/8,SCREEN_WIDTH,HAlignment.CENTER);
			font.drawMultiLine(batch, "Hmmm..."+diff, 0, 3*SCREEN_HEIGHT/4,SCREEN_WIDTH,HAlignment.CENTER);
		}
		
		if(gameOver){
			font.drawMultiLine(batch, "Game Over", 0, SCREEN_HEIGHT/2,SCREEN_WIDTH,HAlignment.CENTER);
		}
		
		batch.setColor(Color.WHITE);
		batch.end();

	}

	private float calculateDiff() {
		float diff = Math.abs(goalColor-currentColor);
		diff = MathUtils.round(diff * 10000) / 10000.0f;
		return diff;
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
		// TODO Auto-generated method stub

	}

}