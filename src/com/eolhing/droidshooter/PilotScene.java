package com.eolhing.droidshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eolhing.droidshooter.Tools.AnimatedValue;
import com.eolhing.droidshooter.Tools.Clock;
import com.eolhing.droidshooter.Tools.TextureManager;

public class PilotScene extends Scene
{
	int returnScene;

	final int pilotCount = 4;
	int pilotIndex;
	long animationDuration;
	boolean endAnimation;

	TextureRegion panel;
	Sprite background;
	Sprite topPanel;
	Sprite bottomPanel;
	Sprite rightArrow;
	Sprite leftArrow;
	Sprite takeOff;
	Sprite pilotName[];
	Sprite shipName[];

	SpriteBatch batch;

	Clock clock;
	AnimatedValue topPanelY;
	AnimatedValue bottomPanelY;
	AnimatedValue backgroundAlpha;

	public PilotScene(int width, int height, Game.Data gameData)
	{
		super(width, height, gameData);
		Gdx.gl.glClearColor(1.f, 1.f, 1.f, 1.f);

		returnScene = NONE;
		pilotIndex = 0;
		animationDuration = 350;
		endAnimation = false;

		Texture texture = TextureManager.getInstance().getTexture("menu.png");
		panel = new TextureRegion(texture, 0, 800, 1, 88);
		background = new Sprite(texture, 0, 0, 480, 800);
		topPanel = new Sprite(panel);
		bottomPanel = new Sprite(panel);
		leftArrow = new Sprite(texture, 1, 800, 40, 40);
		rightArrow = new Sprite(texture, 41, 800, 40, 40);
		takeOff = new Sprite(texture, 81, 800, 201, 38);
		pilotName = new Sprite[pilotCount];
		pilotName[0] = new Sprite(texture, 282, 818, 47, 23);
		pilotName[1] = new Sprite(texture, 329, 818, 52, 23);
		pilotName[2] = new Sprite(texture, 381, 818, 89, 23);
		pilotName[3] = new Sprite(texture, 470, 818, 46, 23);
		shipName = new Sprite[pilotCount];
		shipName[0] = new Sprite(texture, 282, 800, 148, 18);
		shipName[1] = new Sprite(texture, 430, 800, 108, 18);
		shipName[2] = new Sprite(texture, 538, 800, 124, 18);
		shipName[3] = new Sprite(texture, 662, 800, 81, 18);

		batch = new SpriteBatch();

		clock = new Clock();

		resize(width, height);
	}

	@Override public void resize(int width, int height)
	{
		super.resize(width, height);

		bottomPanel.setBounds(0, 50, 480, 88);
		leftArrow.setPosition(12, 70);
		rightArrow.setPosition(428, 70);
		takeOff.setPosition(141, 72);

		topPanel.setBounds(0, 662, 480, 88);
		for (int i = 0; i < pilotCount; ++i)
		{
			pilotName[i].setPosition((float) Math.ceil((width - pilotName[i]
			        .getRegionWidth()) / 2), 706);
			shipName[i].setPosition((float) Math.ceil((width - shipName[i]
			        .getRegionWidth()) / 2), 675);
		}

		topPanelY = new AnimatedValue(AnimatedValue.INTERPOLATION, topPanel.getY() + 138, topPanel.getY(), animationDuration);
		bottomPanelY = new AnimatedValue(AnimatedValue.INTERPOLATION, bottomPanel.getY() - 138, bottomPanel.getY(), animationDuration);
		backgroundAlpha = new AnimatedValue(1.f);
	}

	@Override public int draw()
	{
		// Update
		if (clock.getTime() <= animationDuration)
		{
			float topPanelYValue = topPanelY.getValue();
			topPanel.setPosition(topPanel.getX(), topPanelYValue);
			for (int i = 0; i < pilotCount; ++i)
			{
				pilotName[i].setPosition(pilotName[i].getX(), topPanelYValue + 44);
				shipName[i].setPosition(shipName[i].getX(), topPanelYValue + 13);
			}

			float bottomPanelYValue = bottomPanelY.getValue();
			bottomPanel.setPosition(bottomPanel.getX(), bottomPanelYValue);
			leftArrow.setPosition(leftArrow.getX(), bottomPanelYValue + 20);
			rightArrow.setPosition(rightArrow.getX(), bottomPanelYValue + 20);
			takeOff.setPosition(takeOff.getX(), bottomPanelYValue + 22);
		}

		// Draw

		batch.begin();
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		background.draw(batch, backgroundAlpha.getValue());

		topPanel.draw(batch);
		pilotName[pilotIndex].draw(batch);
		shipName[pilotIndex].draw(batch);

		bottomPanel.draw(batch);
		rightArrow.draw(batch);
		leftArrow.draw(batch);
		takeOff.draw(batch);

		batch.end();

		return endAnimation && clock.getTime() > animationDuration ? returnScene : NONE;
	}

	@Override public boolean touchDown(int x, int y, int pointer, int button)
	{
		y = height - y;

		if (leftArrow.getBoundingRectangle().contains(x, y))
			pilotIndex = pilotIndex <= 0 ? pilotCount - 1 : pilotIndex - 1;
		else if (rightArrow.getBoundingRectangle().contains(x, y))
			pilotIndex = pilotIndex >= pilotCount - 1 ? 0 : pilotIndex + 1;
		else if (takeOff.getBoundingRectangle().contains(x, y))
		{
			gameData.pilotType = pilotIndex;
			returnScene = ARCADE;
			startEndAnimation(true);
		}

		return false;
	}

	@Override public boolean keyDown(int keyCode)
	{
		if (keyCode == Keys.BACK || keyCode == Keys.ESCAPE)
		{
			returnScene = MENU;
			startEndAnimation(false);
			return true;
		}

		return false;
	}

	public void startEndAnimation(boolean backgroundAnimation)
	{
		endAnimation = true;
		clock.reset();
		topPanelY.reverse(true);
		bottomPanelY.reverse(true);
		if (backgroundAnimation)
			backgroundAlpha = new AnimatedValue(AnimatedValue.INTERPOLATION, 1.f, 0.f, animationDuration);
	}
}
