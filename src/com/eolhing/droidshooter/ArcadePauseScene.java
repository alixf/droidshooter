package com.eolhing.droidshooter;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eolhing.droidshooter.ArcadeScene.Score;
import com.eolhing.droidshooter.Tools.AnimatedValue;
import com.eolhing.droidshooter.Tools.Clock;
import com.eolhing.droidshooter.Tools.TextureManager;

public class ArcadePauseScene
{
	// Scene data
	float width;
	float height;
	Game.Data gameData;
	// Animation and display data
	boolean opened;
	boolean closeAnimation;
	int returnScene;
	public Score score;
	// Background
	private Sprite background;
	// Frame corners
	private Sprite topLeftCorner;
	private Sprite topRightCorner;
	private Sprite bottomLeftCorner;
	private Sprite bottomRightCorner;
	// Frame borders
	private NinePatch border;
	private Rectangle leftBorderRect;
	private Rectangle topBorderRect;
	private Rectangle rightBorderRect;
	private Rectangle bottomBorderRect;
	// Panels
	private Sprite titlePanel;
	private Sprite scorePanel;
	private Sprite resumePanel;
	private Sprite exitPanel;
	// Texts
	private Sprite pauseText;
	private Sprite scoreText;
	private Sprite resumeText;
	private Sprite exitText;
	// Animation
	long animationDuration;
	AnimatedValue backgroundAlpha;
	AnimatedValue leftBorderX;
	AnimatedValue rightBorderX;
	AnimatedValue topBorderY;
	AnimatedValue bottomBorderY;
	AnimatedValue topLeftCornerX;
	AnimatedValue topLeftCornerY;
	AnimatedValue topRightCornerX;
	AnimatedValue topRightCornerY;
	AnimatedValue bottomLeftCornerX;
	AnimatedValue bottomLeftCornerY;
	AnimatedValue bottomRightCornerX;
	AnimatedValue bottomRightCornerY;
	AnimatedValue titlePanelX;
	AnimatedValue scorePanelX;
	AnimatedValue resumePanelX;
	AnimatedValue exitPanelX;
	// Font
	private BitmapFont scoreFont;
	// Clock
	Clock clock;

	public ArcadePauseScene(int width, int height, Game.Data gameData, Score score)
	{
		// Scene data
		this.width = width;
		this.height = height;
		this.gameData = gameData;
		// Animation and display data
		opened = true;
		closeAnimation = false;
		returnScene = Scene.NONE;
		this.score = score;
		// Texture
		Texture texture = TextureManager.getInstance().getTexture("menu.png");
		// Background
		background = new Sprite(TextureManager.blackBox);
		background.setBounds(0, 0, width, height);
		// Frame corners
		topLeftCorner = new Sprite(texture, 886, 0, 125, 125);
		topRightCorner = new Sprite(texture, 886, 125, 124, 125);
		bottomLeftCorner = new Sprite(texture, 886, 250, 125, 124);
		bottomRightCorner = new Sprite(texture, 886, 374, 124, 124);
		topLeftCorner.setPosition(0.f, height - topLeftCorner.getRegionHeight());
		topRightCorner.setPosition(width - bottomRightCorner.getRegionWidth(), height - topLeftCorner.getRegionHeight());
		bottomLeftCorner.setPosition(0.f, 0.f);
		bottomRightCorner.setPosition(width - bottomRightCorner.getRegionWidth(), 0.f);
		// Frame borders
		border = new NinePatch(new TextureRegion(texture, 94, 865, 19, 19), 9, 9, 9, 9);
		TextureRegion panel = new TextureRegion(texture, 0, 800, 1, 88);
		leftBorderRect = new Rectangle(0.f - 4.f, 125.f - 4.f, 75.f + 8.f, height - 2 * 125.f + 7.f);
		topBorderRect = new Rectangle(125.f - 3.f, height - 75.f - 4.f, width - 2 * 125.f + 7.f, 75.f + 8.f);
		rightBorderRect = new Rectangle(width - 75.f - 4.f, 125.f - 4.f, 75.f + 8.f, height - 2 * 125.f + 7.f);
		bottomBorderRect = new Rectangle(125.f - 3.f, 0.f - 4.f, width - 2 * 125.f + 7.f, 75.f + 8.f);
		// Panels
		titlePanel = new Sprite(panel);
		scorePanel = new Sprite(panel);
		resumePanel = new Sprite(panel);
		exitPanel = new Sprite(panel);
		titlePanel.setBounds(75.f, 539.f, width - 2 * 75.f, panel.getRegionHeight());
		scorePanel.setBounds(75.f, 416.f, width - 2 * 75.f, panel.getRegionHeight());
		resumePanel.setBounds(75.f, 293.f, width - 2 * 75.f, panel.getRegionHeight());
		exitPanel.setBounds(75.f, 170.f, width - 2 * 75.f, panel.getRegionHeight());
		// Texts
		pauseText = new Sprite(texture, 165, 975, 115, 35);
		scoreText = new Sprite(texture, 280, 975, 83, 23);
		resumeText = new Sprite(texture, 383, 866, 168, 36);
		exitText = new Sprite(texture, 373, 949, 266, 37);
		pauseText.setPosition(titlePanel.getX() + (float) Math.ceil((titlePanel.getWidth() - pauseText.getWidth()) / 2.f),
		        titlePanel.getY() + (float) Math.ceil((titlePanel.getHeight() - pauseText.getHeight()) / 2.f));
		scoreText.setPosition(scorePanel.getX() + (float) Math.ceil((scorePanel.getWidth() - scoreText.getWidth()) / 2.f),
		        scorePanel.getY() + (float) Math.ceil((scorePanel.getHeight() - scoreText.getHeight()) / 2.f) + 15.f);
		resumeText.setPosition(resumePanel.getX() + (float) Math.ceil((resumePanel.getWidth() - resumeText.getWidth()) / 2.f),
		        resumePanel.getY() + (float) Math.ceil((resumePanel.getHeight() - resumeText.getHeight()) / 2.f));
		exitText.setPosition(exitPanel.getX() + (float) Math.ceil((exitPanel.getWidth() - exitText.getWidth()) / 2.f),
		        exitPanel.getY() + (float) Math.ceil((exitPanel.getHeight() - exitText.getHeight()) / 2.f));
		// Font
		scoreFont = new BitmapFont();
		// Animation
		animationDuration = 150;
		backgroundAlpha = new AnimatedValue(AnimatedValue.INTERPOLATION, 0.f, 0.75f, animationDuration);
		leftBorderX = new AnimatedValue(AnimatedValue.INTERPOLATION, leftBorderRect.x - leftBorderRect.width, leftBorderRect.x, animationDuration);
		rightBorderX = new AnimatedValue(AnimatedValue.INTERPOLATION, rightBorderRect.x + rightBorderRect.width, rightBorderRect.x, animationDuration);
		topBorderY = new AnimatedValue(AnimatedValue.INTERPOLATION, topBorderRect.y + topBorderRect.height, topBorderRect.y, animationDuration);
		bottomBorderY = new AnimatedValue(AnimatedValue.INTERPOLATION, bottomBorderRect.y - bottomBorderRect.height, bottomBorderRect.y, animationDuration);
		topLeftCornerX = new AnimatedValue(AnimatedValue.INTERPOLATION, topLeftCorner.getX() - topLeftCorner.getRegionWidth(), topLeftCorner.getX(), animationDuration);
		topLeftCornerY = new AnimatedValue(AnimatedValue.INTERPOLATION, topLeftCorner.getY() + topLeftCorner.getRegionHeight(), topLeftCorner.getY(), animationDuration);
		topRightCornerX = new AnimatedValue(AnimatedValue.INTERPOLATION, topRightCorner.getX() + topRightCorner.getRegionWidth(), topRightCorner.getX(), animationDuration);
		topRightCornerY = new AnimatedValue(AnimatedValue.INTERPOLATION, topRightCorner.getY() + topRightCorner.getRegionHeight(), topRightCorner.getY(), animationDuration);
		bottomLeftCornerX = new AnimatedValue(AnimatedValue.INTERPOLATION, bottomLeftCorner.getX() - bottomLeftCorner.getRegionWidth(), bottomLeftCorner.getX(), animationDuration);
		bottomLeftCornerY = new AnimatedValue(AnimatedValue.INTERPOLATION, bottomLeftCorner.getY() - bottomLeftCorner.getRegionHeight(), bottomLeftCorner.getY(), animationDuration);
		bottomRightCornerX = new AnimatedValue(AnimatedValue.INTERPOLATION, bottomRightCorner.getX() + bottomRightCorner.getRegionWidth(), bottomRightCorner.getX(), animationDuration);
		bottomRightCornerY = new AnimatedValue(AnimatedValue.INTERPOLATION, bottomRightCorner.getY() - bottomLeftCorner.getRegionHeight(), bottomRightCorner.getY(), animationDuration);
		titlePanelX = new AnimatedValue(AnimatedValue.INTERPOLATION, titlePanel.getX() - titlePanel.getWidth(), titlePanel.getX(), animationDuration);
		scorePanelX = new AnimatedValue(AnimatedValue.INTERPOLATION, scorePanel.getX() + scorePanel.getWidth(), scorePanel.getX(), animationDuration);
		resumePanelX = new AnimatedValue(AnimatedValue.INTERPOLATION, resumePanel.getX() + resumePanel.getWidth(), resumePanel.getX(), animationDuration);
		exitPanelX = new AnimatedValue(AnimatedValue.INTERPOLATION, exitPanel.getX() - exitPanel.getWidth(), exitPanel.getX(), animationDuration);
		// Clock
		clock = new Clock();
	}

	public int draw(SpriteBatch batch)
	{
		//
		// Update
		//
		topLeftCorner.setPosition(topLeftCornerX.getValue(),
		        topLeftCornerY.getValue());
		topRightCorner.setPosition(topRightCornerX.getValue(),
		        topRightCornerY.getValue());
		bottomLeftCorner.setPosition(bottomLeftCornerX.getValue(),
		        bottomLeftCornerY.getValue());
		bottomRightCorner.setPosition(bottomRightCornerX.getValue(),
		        bottomRightCornerY.getValue());
		leftBorderRect.setX(leftBorderX.getValue());
		topBorderRect.setY(topBorderY.getValue());
		rightBorderRect.setX(rightBorderX.getValue());
		bottomBorderRect.setY(bottomBorderY.getValue());
		titlePanel.setPosition(titlePanelX.getValue(), titlePanel.getY());
		scorePanel.setPosition(scorePanelX.getValue(), scorePanel.getY());
		resumePanel.setPosition(resumePanelX.getValue(), resumePanel.getY());
		exitPanel.setPosition(exitPanelX.getValue(), exitPanel.getY());
		pauseText.setPosition(titlePanel.getX() + (float) Math.ceil((titlePanel.getWidth() - pauseText.getWidth()) / 2.f),
		        titlePanel.getY() + (float) Math.ceil((titlePanel.getHeight() - pauseText.getHeight()) / 2.f));
		scoreText.setPosition(scorePanel.getX() + (float) Math.ceil((scorePanel.getWidth() - scoreText.getWidth()) / 2.f),
		        scorePanel.getY() + (float) Math.ceil((scorePanel.getHeight() - scoreText.getHeight()) / 2.f) + 15.f);
		resumeText.setPosition(resumePanel.getX() + (float) Math.ceil((resumePanel.getWidth() - resumeText.getWidth()) / 2.f),
		        resumePanel.getY() + (float) Math.ceil((resumePanel.getHeight() - resumeText.getHeight()) / 2.f));
		exitText.setPosition(exitPanel.getX() + (float) Math.ceil((exitPanel.getWidth() - exitText.getWidth()) / 2.f),
		        exitPanel.getY() + (float) Math.ceil((exitPanel.getHeight() - exitText.getHeight()) / 2.f));
		BitmapFont.TextBounds size = scoreFont.getBounds(Integer.toString(score.value));
		float scoreTextX = (width - size.width) / 2;
		float scoreTextY = scorePanel.getY() + (float) Math.ceil((scorePanel.getHeight() - size.height) / 2.f) - 5.f;
		//
		// Draw
		//
		background.draw(batch, backgroundAlpha.getValue());
		titlePanel.draw(batch);
		scorePanel.draw(batch);
		resumePanel.draw(batch);
		exitPanel.draw(batch);
		pauseText.draw(batch);
		scoreText.draw(batch);
		resumeText.draw(batch);
		exitText.draw(batch);
		border.draw(batch, leftBorderRect.x, leftBorderRect.y, leftBorderRect.width, leftBorderRect.height);
		border.draw(batch, topBorderRect.x, topBorderRect.y, topBorderRect.width, topBorderRect.height);
		border.draw(batch, rightBorderRect.x, rightBorderRect.y, leftBorderRect.width, rightBorderRect.height);
		border.draw(batch, bottomBorderRect.x, bottomBorderRect.y, bottomBorderRect.width, bottomBorderRect.height);
		topLeftCorner.draw(batch);
		topRightCorner.draw(batch);
		bottomLeftCorner.draw(batch);
		bottomRightCorner.draw(batch);
		scoreFont.draw(batch, Integer.toString(score.value), scoreTextX, scoreTextY);

		return (closeAnimation && clock.getTime() > animationDuration) ? returnScene : Scene.NONE;
	}

	public void open()
	{
		if (!opened)
			reverseAnimation();
		else
			resetAnimation();

		opened = true;
		returnScene = Scene.NONE;
		closeAnimation = false;
		clock.reset();
	}

	public void close()
	{
		if (opened)
			reverseAnimation();
		else
			resetAnimation();

		opened = false;
		returnScene = Scene.ARCADE;
		closeAnimation = true;
		clock.reset();
	}

	public boolean touchDown(int x, int y, int pointer, int button)
	{
		y = (int) height - y;
		if (resumePanel.getBoundingRectangle().contains(x, y))
		{
			close();
			returnScene = Scene.ARCADE;
			return true;
		}
		if (exitPanel.getBoundingRectangle().contains(x, y))
		{
			close();
			returnScene = Scene.MENU;
			return true;
		}

		return false;
	}

	private void resetAnimation()
	{
		backgroundAlpha.reset();
		leftBorderX.reset();
		rightBorderX.reset();
		topBorderY.reset();
		bottomBorderY.reset();
		topLeftCornerX.reset();
		topLeftCornerY.reset();
		topRightCornerX.reset();
		topRightCornerY.reset();
		bottomLeftCornerX.reset();
		bottomLeftCornerY.reset();
		bottomRightCornerX.reset();
		bottomRightCornerY.reset();
		titlePanelX.reset();
		scorePanelX.reset();
		resumePanelX.reset();
		exitPanelX.reset();
	}

	private void reverseAnimation()
	{
		backgroundAlpha.reverse(true);
		leftBorderX.reverse(true);
		rightBorderX.reverse(true);
		topBorderY.reverse(true);
		bottomBorderY.reverse(true);
		topLeftCornerX.reverse(true);
		topLeftCornerY.reverse(true);
		topRightCornerX.reverse(true);
		topRightCornerY.reverse(true);
		bottomLeftCornerX.reverse(true);
		bottomLeftCornerY.reverse(true);
		bottomRightCornerX.reverse(true);
		bottomRightCornerY.reverse(true);
		titlePanelX.reverse(true);
		scorePanelX.reverse(true);
		resumePanelX.reverse(true);
		exitPanelX.reverse(true);
	}
}