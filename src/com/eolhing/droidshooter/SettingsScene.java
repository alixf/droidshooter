package com.eolhing.droidshooter;

import java.io.PrintStream;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.eolhing.droidshooter.Tools.AnimatedValue;
import com.eolhing.droidshooter.Tools.Clock;
import com.eolhing.droidshooter.Tools.TextureManager;
import com.eolhing.droidshooter.GUI.Button;

public class SettingsScene extends Scene
{
	int returnScene;

	public static boolean particlesEnabled = true;
	public static boolean musicEnabled = true;
	public static boolean soundEnabled = true;
	public static boolean vibrateEnabled = true;
	public static boolean controlOffsetEnabled = true;

	Texture texture;
	TextureRegion background;
	Sprite graphicsSprite;
	Sprite audioSprite;
	Sprite gameplaySprite;
	Sprite particlesSprite;
	Sprite musicSprite;
	Sprite soundSprite;
	Sprite vibrateSprite;
	Sprite controlOffsetSprite;
	Sprite cancelSprite;
	Sprite applySprite;
	NinePatch switchOnGlass;
	NinePatch switchOffGlass;
	NinePatch switchBackground;

	Button particlesButton;
	Button musicButton;
	Button soundButton;
	Button vibrateButton;
	Button controlOffsetButton;
	Button cancelButton;
	Button applyButton;

	long animationDuration;
	AnimatedValue switchesXPosition1;
	AnimatedValue switchesXPosition2;
	AnimatedValue cancelXPosition;
	AnimatedValue applyXPosition;
	AnimatedValue categoriesAlpha;
	boolean endAnimation;

	SpriteBatch batch;

	Clock clock;

	public SettingsScene(int width, int height, Game.Data gameData)
	{
		super(width, height, gameData);

		returnScene = NONE;

		texture = TextureManager.getInstance().getTexture("menu.png");
		background = new TextureRegion(texture, 0, 0, 480, 800);
		particlesSprite = new Sprite(texture, 94, 841, 103, 24);
		musicSprite = new Sprite(texture, 197, 841, 72, 23);
		soundSprite = new Sprite(texture, 269, 841, 94, 24);
		vibrateSprite = new Sprite(texture, 363, 841, 91, 24);
		controlOffsetSprite = new Sprite(texture, 454, 841, 176, 24);
		cancelSprite = new Sprite(texture, 113, 865, 138, 70);
		applySprite = new Sprite(texture, 251, 865, 132, 70);
		graphicsSprite = new Sprite(texture, 0, 940, 148, 36);
		audioSprite = new Sprite(texture, 0, 910, 113, 30);
		gameplaySprite = new Sprite(texture, 0, 976, 165, 36);
		switchOnGlass = new NinePatch(new TextureRegion(texture, 70, 840, 24, 45), 11, 12, 12, 12);
		switchOffGlass = new NinePatch(new TextureRegion(texture, 46, 840, 24, 45), 11, 12, 12, 12);
		switchBackground = new NinePatch(new TextureRegion(texture, 1, 840, 45, 70), 22, 22, 22, 22);
		graphicsSprite.setPosition(167, 800 - 163 - 37);
		audioSprite.setPosition(184, 800 - 271 - 31);
		gameplaySprite.setPosition(158, 800 - 515 - 38);

		readSettings();

		particlesButton = new Button(particlesEnabled, particlesSprite, switchBackground, switchOnGlass, switchOffGlass, new Rectangle(9, 800 - 200 - 71, 463, 71));
		musicButton = new Button(musicEnabled, musicSprite, switchBackground, switchOnGlass, switchOffGlass, new Rectangle(9, 800 - 302 - 71, 463, 71));
		soundButton = new Button(soundEnabled, soundSprite, switchBackground, switchOnGlass, switchOffGlass, new Rectangle(9, 800 - 373 - 71, 463, 71));
		vibrateButton = new Button(vibrateEnabled, vibrateSprite, switchBackground, switchOnGlass, switchOffGlass, new Rectangle(9, 800 - 444 - 71, 463, 71));
		controlOffsetButton = new Button(controlOffsetEnabled, controlOffsetSprite, switchBackground, switchOnGlass, switchOffGlass, new Rectangle(9, 800 - 553 - 71, 463, 71));
		cancelButton = new Button(cancelSprite, switchBackground, switchOnGlass, switchOffGlass, new Rectangle(0, 800 - 714 - 71, 138, 71));
		applyButton = new Button(applySprite, switchBackground, switchOnGlass, switchOffGlass, new Rectangle(348, 800 - 714 - 71, 132, 71));
		applyButton.drawBackground = false;
		cancelButton.drawBackground = false;

		batch = new SpriteBatch();

		animationDuration = 500;
		switchesXPosition1 = new AnimatedValue(AnimatedValue.INTERPOLATION, width, 9, animationDuration);
		switchesXPosition2 = new AnimatedValue(AnimatedValue.INTERPOLATION, -463, 9, animationDuration);
		cancelXPosition = new AnimatedValue(AnimatedValue.INTERPOLATION, -cancelButton.rectangle.width, 0, animationDuration);
		applyXPosition = new AnimatedValue(AnimatedValue.INTERPOLATION, width, width - applyButton.rectangle.width, animationDuration);
		categoriesAlpha = new AnimatedValue(AnimatedValue.INTERPOLATION, 0.f, 1.f, animationDuration);
		endAnimation = false;

		resize(width, height);

		clock = new Clock();
	}

	@Override public void resize(int width, int height)
	{
		super.resize(width, height);
	}

	@Override public int draw()
	{
		// Update
		particlesButton.setPosition(switchesXPosition1.getValue(), particlesButton.getY());
		musicButton.setPosition(switchesXPosition2.getValue(), musicButton.getY());
		soundButton.setPosition(switchesXPosition1.getValue(), soundButton.getY());
		vibrateButton.setPosition(switchesXPosition2.getValue(), vibrateButton.getY());
		controlOffsetButton.setPosition(switchesXPosition1.getValue(), controlOffsetButton.getY());
		cancelButton.setPosition(cancelXPosition.getValue(), cancelButton.getY());
		applyButton.setPosition(applyXPosition.getValue(), applyButton.getY());
		float categoriesAlphaValue = categoriesAlpha.getValue();

		// Draw
		batch.begin();
		batch.draw(background, 0, 0);

		graphicsSprite.draw(batch, categoriesAlphaValue);
		particlesButton.draw(batch);
		audioSprite.draw(batch, categoriesAlphaValue);
		musicButton.draw(batch);
		soundButton.draw(batch);
		vibrateButton.draw(batch);
		gameplaySprite.draw(batch, categoriesAlphaValue);
		controlOffsetButton.draw(batch);
		cancelButton.draw(batch);
		applyButton.draw(batch);

		batch.end();

		return endAnimation && clock.getTime() > animationDuration ? returnScene : NONE;
	}

	private void startEndAnimation()
	{
		endAnimation = true;
		clock.reset();
		switchesXPosition1.reverse(true);
		switchesXPosition2.reverse(true);
		cancelXPosition.reverse(true);
		applyXPosition.reverse(true);
		categoriesAlpha.reverse(true);
		returnScene = MENU;
	}

	@Override public boolean touchDown(int x, int y, int pointer, int button)
	{
		y = height - y;

		particlesButton.touchDown(x, y);
		musicButton.touchDown(x, y);
		soundButton.touchDown(x, y);
		vibrateButton.touchDown(x, y);
		controlOffsetButton.touchDown(x, y);
		if (cancelButton.touchDown(x, y))
			startEndAnimation();
		if (applyButton.touchDown(x, y))
		{
			particlesEnabled = particlesButton.switchValue;
			soundEnabled = soundButton.switchValue;
			musicEnabled = musicButton.switchValue;
			vibrateEnabled = vibrateButton.switchValue;
			controlOffsetEnabled = controlOffsetButton.switchValue;
			saveSettings();
			startEndAnimation();
		}

		return false;
	}

	@Override public boolean keyDown(int keyCode)
	{
		if (keyCode == Keys.BACK || keyCode == Keys.ESCAPE)
			startEndAnimation();

		return true;
	}

	static public boolean saveSettings()
	{
		FileHandle handle = Gdx.files.external(".droidshooter/settings.txt");
		PrintStream ps = new PrintStream(handle.write(false));
		ps.print("particles:" + Boolean.toString(particlesEnabled) + "\n");
		ps.print("music:" + Boolean.toString(musicEnabled) + "\n");
		ps.print("sound:" + Boolean.toString(soundEnabled) + "\n");
		ps.print("vibrate:" + Boolean.toString(vibrateEnabled) + "\n");
		ps.print("controlloffset:" + Boolean.toString(controlOffsetEnabled));
		return true;
	}

	static public boolean readSettings()
	{
		FileHandle handle = Gdx.files.external(".droidshooter/settings.txt");
		if (!handle.exists())
		{
			FileHandle directory = Gdx.files.external(".droidshooter");
			directory.mkdirs();
			saveSettings();
		}
		String content = handle.readString();

		for (String option : content.split("\n"))
		{
			String[] optionFields = option.split(":");
			String name = optionFields[0];
			String value = optionFields[1];

			if (name.equals("particles"))
				particlesEnabled = Boolean.parseBoolean(value);
			else if (name.equals("music"))
				musicEnabled = Boolean.parseBoolean(value);
			else if (name.equals("sound"))
				soundEnabled = Boolean.parseBoolean(value);
			else if (name.equals("vibrate"))
				vibrateEnabled = Boolean.parseBoolean(value);
			else if (name.equals("controloffset"))
				controlOffsetEnabled = Boolean.parseBoolean(value);
		}

		return true;
	}
}
