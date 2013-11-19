package com.eolhing.droidshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.eolhing.droidshooter.Tools.AnimatedValue;
import com.eolhing.droidshooter.Tools.Clock;
import com.eolhing.droidshooter.Tools.TextureManager;

public class MenuScene extends Scene
{
	int returnScene;

	Sprite background;
	TextureRegion title;
	TextureRegion arcadeButton;
	TextureRegion storyButton;
	TextureRegion settingsButton;
	TextureRegion quitButton;
	TextureRegion arcadeButtonHover;
	TextureRegion storyButtonHover;
	TextureRegion settingsButtonHover;
	TextureRegion quitButtonHover;

	boolean arcadeButtonDown;
	boolean storyButtonDown;
	boolean settingsButtonDown;
	boolean quitButtonDown;

	Rectangle arcadeButtonRect;
	Rectangle storyButtonRect;
	Rectangle settingsButtonRect;
	Rectangle quitButtonRect;

	boolean endAnimation;
	long animationDuration;
	AnimatedValue arcadeButtonX;
	AnimatedValue storyButtonX;
	AnimatedValue settingsButtonX;
	AnimatedValue quitButtonX;
	AnimatedValue titleAlpha;
	AnimatedValue backgroundAlpha;

	// Highscore
	private BitmapFont font;
	private String HSText;

	SpriteBatch batch;

	Clock clock;

	public MenuScene(int width, int height, Game.Data gameData)
	{
		super(width, height, gameData);
		Gdx.gl.glClearColor(0.f, 0.f, 0.f, 1.f);

		returnScene = NONE;

		Texture texture = TextureManager.getInstance().getTexture("menu.png");
		background = new Sprite(texture, 0, 0, 480, 800);
		title = new TextureRegion(texture, 480, 0, 405, 112);
		arcadeButton = new TextureRegion(texture, 480, 112, 405, 86);
		storyButton = new TextureRegion(texture, 480, 198, 405, 86);
		settingsButton = new TextureRegion(texture, 480, 284, 405, 86);
		quitButton = new TextureRegion(texture, 480, 370, 405, 86);
		arcadeButtonHover = new TextureRegion(texture, 480, 456, 405, 86);
		storyButtonHover = new TextureRegion(texture, 480, 542, 405, 86);
		settingsButtonHover = new TextureRegion(texture, 480, 628, 405, 86);
		quitButtonHover = new TextureRegion(texture, 480, 714, 405, 86);

		arcadeButtonDown = false;
		storyButtonDown = false;
		settingsButtonDown = false;
		quitButtonDown = false;

		arcadeButtonRect = new Rectangle(-8.f, 517.f, arcadeButton.getRegionWidth(), arcadeButton.getRegionHeight());
		storyButtonRect = new Rectangle(82.f, 417.f, storyButton.getRegionWidth(), storyButton.getRegionHeight());
		settingsButtonRect = new Rectangle(82.f, 127.f, settingsButton.getRegionWidth(), settingsButton.getRegionHeight());
		quitButtonRect = new Rectangle(-8.f, 27.f, quitButton.getRegionWidth(), quitButton.getRegionHeight());

		endAnimation = false;
		animationDuration = 500;
		arcadeButtonX = new AnimatedValue(AnimatedValue.INTERPOLATION, -414.f, -8.f, animationDuration);
		storyButtonX = new AnimatedValue(AnimatedValue.INTERPOLATION, 488.f, 82.f, animationDuration);
		settingsButtonX = new AnimatedValue(AnimatedValue.INTERPOLATION, 488.f, 82.f, animationDuration);
		quitButtonX = new AnimatedValue(AnimatedValue.INTERPOLATION, -414.f, -8.f, animationDuration);
		titleAlpha = new AnimatedValue(AnimatedValue.INTERPOLATION, 0.f, 1.f, animationDuration);
		backgroundAlpha = new AnimatedValue(1.f);

		font = new BitmapFont();
		int scores[] = ArcadeScene.readHighScores();
		HSText = scores.length > 0 ? Integer.toString(scores[0]) : "Aucun";

		batch = new SpriteBatch();

		resize(width, height);

		clock = new Clock();
	}

	@Override public void resize(int width, int height)
	{
		super.resize(width, height);
	}

	@Override public int draw()
	{
		arcadeButtonRect.setX(arcadeButtonX.getValue());
		storyButtonRect.setX(storyButtonX.getValue());
		settingsButtonRect.setX(settingsButtonX.getValue());
		quitButtonRect.setX(quitButtonX.getValue());

		batch.begin();
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		background.draw(batch, backgroundAlpha.getValue());
		batch.setColor(1.f, 1.f, 1.f, titleAlpha.getValue());
		batch.draw(title, 39, 669);
		batch.setColor(1.f, 1.f, 1.f, 1.f);
		batch.draw(arcadeButtonDown ? arcadeButtonHover : arcadeButton, arcadeButtonRect.x, arcadeButtonRect.y);
		batch.draw(storyButtonDown ? storyButtonHover : storyButton, storyButtonRect.x, storyButtonRect.y);
		batch.draw(settingsButtonDown ? settingsButtonHover : settingsButton, settingsButtonRect.x, settingsButtonRect.y);
		batch.draw(quitButtonDown ? quitButtonHover : quitButton, quitButtonRect.x, quitButtonRect.y);
		font.setColor(0.f, 0.f, 0.f, 75.f);
		font.draw(batch, HSText, arcadeButtonRect.x + 240 - font.getBounds(HSText).width, arcadeButtonRect.y + 24);
		font.setColor(0.7f, 0.7f, 0.7f, 1.f);
		font.draw(batch, HSText, arcadeButtonRect.x + 240 - font.getBounds(HSText).width, arcadeButtonRect.y + 23);

		batch.end();

		return endAnimation && clock.getTime() > animationDuration ? returnScene : NONE;
	}

	@Override public boolean touchDown(int x, int y, int pointer, int button)
	{
		y = height - y;

		boolean buttonClicked = true;
		boolean quit = false;
		if (arcadeButtonRect.contains(x, y))
			returnScene = PILOT;
		/*
		 * else if(storyButtonRect.contains(x,y)) returnScene = STORY;
		 */
		else if (settingsButtonRect.contains(x, y))
			returnScene = SETTINGS;
		else if (quitButtonRect.contains(x, y))
		{
			quit = true;
			returnScene = QUIT;
		}
		else
			buttonClicked = false;

		if (buttonClicked)
		{
			endAnimation = true;
			arcadeButtonX.reverse(true);
			storyButtonX.reverse(true);
			settingsButtonX.reverse(true);
			quitButtonX.reverse(true);
			titleAlpha.reverse(true);
			if (quit)
				backgroundAlpha = new AnimatedValue(AnimatedValue.INTERPOLATION, 1.f, 0.f, animationDuration);
			clock.reset();
		}

		return false;
	}
}
