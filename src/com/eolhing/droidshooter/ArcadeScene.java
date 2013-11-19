package com.eolhing.droidshooter;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eolhing.droidshooter.GameEffects.*;
import com.eolhing.droidshooter.GameEntities.*;
import com.eolhing.droidshooter.GameFactories.*;
import com.eolhing.droidshooter.GamePhysics.Physics;
import com.eolhing.droidshooter.Tools.Clock;
import com.eolhing.droidshooter.Tools.TextureManager;

public class ArcadeScene extends Scene implements EffectContainer, ParticlesContainer
{
	static public class Score
	{
		public int value;
	}

	// State values
	public static final int RUNNING = 0;
	public static final int PAUSE = 1;
	public static final int GAMEOVER = 2;

	private int returnScene;

	// Game Data
	public int state;
	private Rectangle terrainRect;
	private int mCurrentWave;

	// Render
	private SpriteBatch mSpriteBatch;

	// Game Entities
	private Background mBackground;
	private LinkedList<Bullet> mEnemyBullets;
	private LinkedList<Enemy> mEnemies;
	private LinkedList<Bullet> mBullets;
	private LinkedList<PlayerShip> mPlayers;
	private PlayerShip mShip;
	private LinkedList<Effect> mEffects;
	private LinkedList<ParticleEffect> mParticleEffects;

	// HUD
	private HUD mHUD;

	// FPS and Score
	private BitmapFont mFont;
	private String mFPSText;
	private Score mScore;

	// Music
	Music music;

	// Scenes
	ArcadeGameOverScene gameOverScene;
	ArcadePauseScene pauseScene;

	// Clock
	public Clock clock;
	public boolean spawnEnemies;

	public ArcadeScene(int width, int height, Game.Data gameData)
	{
		super(width, height, gameData);
		Gdx.gl.glClearColor(0.f, 0.f, 0.f, 1.f);

		readHighScores();

		returnScene = NONE;

		// Game Data
		state = RUNNING;
		terrainRect = new Rectangle();
		mCurrentWave = 13;

		// Render
		mSpriteBatch = new SpriteBatch();

		// Game Entities
		mBackground = new Background();
		mEnemyBullets = new LinkedList<Bullet>();
		mEnemies = new LinkedList<Enemy>();
		mBullets = new LinkedList<Bullet>();
		mPlayers = new LinkedList<PlayerShip>();
		mShip = PlayerShipFactory.getShip(gameData.pilotType, mBullets, new Vector2(0.f, 0.f), mEnemies);
		mEffects = new LinkedList<Effect>();
		mParticleEffects = new LinkedList<ParticleEffect>();

		// HUD
		mHUD = new HUD();

		// FPS and Score
		mFPSText = new String();
		mScore = new Score();
		mFont = new BitmapFont();
		mFont.setColor(1.f, 0.f, 0.f, 1.f);

		// Bind
		mShip.score = mScore;
		mShip.hud = mHUD;
		mShip.effectContainer = this;
		mShip.particleEffectContainer = this;
		mHUD.playerShip = mShip;

		// Size
		resize(width, height);

		// Ship
		mShip.setPosition(new Vector2(width / 2, -20.f));
		mShip.moveTarget.set(width / 2, 150.f);
		mPlayers.add(mShip);

		// Music
		if (SettingsScene.musicEnabled)
		{
			music = Gdx.audio.newMusic(Gdx.files.internal("res/music/instilltime.ogg"));
			music.setVolume(0.5f);
			music.setLooping(true);
			music.play();
		}

		// Scenes
		gameOverScene = new ArcadeGameOverScene(width, height, gameData, mScore);
		pauseScene = new ArcadePauseScene(width, height, gameData, mScore);

		// Clock
		clock = new Clock();

		spawnEnemies = true;
	}

	public void resize(int width, int height)
	{
		super.resize(width, height);
		TrajectoryFactory.setTerrainSize(width, height);

		mBackground.setScreenSize(width, height);
		mSpriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		mHUD.setScreenSize(width, height);
		terrainRect = new Rectangle(0, 0, width, height);
	}

	@Override public int draw()
	{
		float delta = clock.getTime() / 1000.f;
		clock.reset();

		//
		// Update ////////////////////////////////////////////////////
		//

		// FPS Display Text
		mFPSText = "DroidShooter v" + gameData.versionNumber + " - FPS : " + Gdx.graphics.getFramesPerSecond() + " - Score : " + mScore.value;

		// Iterators
		ListIterator<PlayerShip> playerIterator;
		ListIterator<Enemy> enemiesIterator;
		ListIterator<Bullet> bulletsIterator;
		ListIterator<Bullet> enemyBulletsIterator;

		if (state == RUNNING)
		{
			// Background
			mBackground.update();

			// Player's Ship
			mShip.update();

			// Enemies
			enemiesIterator = mEnemies.listIterator();
			while (enemiesIterator.hasNext())
			{
				Enemy enemy = enemiesIterator.next();
				enemy.update();
				if (enemy.state == Ship.DESTROYED)
					enemiesIterator.remove();
			}

			// Player's bullets
			bulletsIterator = mBullets.listIterator();
			while (bulletsIterator.hasNext())
			{
				Bullet bullet = bulletsIterator.next();
				bullet.update();

				if (bullet.canCollide)
				{
					enemiesIterator = mEnemies.listIterator();
					while (enemiesIterator.hasNext())
					{
						Enemy enemy = enemiesIterator.next();
						if (Physics.collide(bullet, enemy))
							enemy.collide(bullet);
					}
				}

				Rectangle bulletRect = bullet.getRect();
				if (!bullet.isAlive || !bulletRect.overlaps(terrainRect))
					bulletsIterator.remove();
			}

			// Enemies' bullets
			enemyBulletsIterator = mEnemyBullets.listIterator();
			while (enemyBulletsIterator.hasNext())
			{
				Bullet bullet = enemyBulletsIterator.next();
				bullet.update();

				if (bullet.canCollide)
				{
					playerIterator = mPlayers.listIterator();
					while (playerIterator.hasNext())
					{
						PlayerShip player = playerIterator.next();
						if (Physics.collide(bullet, player))
							player.collide(bullet);
					}
				}

				Rectangle bulletRect = bullet.getRect();
				if (!bullet.isAlive || !bulletRect.overlaps(terrainRect))
					enemyBulletsIterator.remove();
			}

			// Particle Effects
			ListIterator<ParticleEffect> particleEffectIterator = mParticleEffects.listIterator();
			while (particleEffectIterator.hasNext())
				particleEffectIterator.next().update(delta);

			// Effects
			ListIterator<Effect> effectIterator = mEffects.listIterator();
			while (effectIterator.hasNext())
			{
				Effect effect = effectIterator.next();
				effect.update();
				if (effect.state == Effect.FINISHED)
					effectIterator.remove();
			}

			mHUD.update();

			// If the ship has been destroyed, switch the state to Game Over and
			// register the score
			if (mShip.state == Ship.DESTROYED && state != GAMEOVER)
			{
				state = GAMEOVER;
				addHighScore(mScore.value);
				gameOverScene.open();
			}

			// If there is no more enemies, build a new wave
			if (mEnemies.size() <= 0 && spawnEnemies)
			{
				for (Enemy.Data enemyData : WaveFactory.getWave(++mCurrentWave, width, height))
				{
					Enemy enemy = EnemyFactory.getEnemy(enemyData.type, mEnemyBullets, enemyData.position, mPlayers);
					enemy.setTimeFactor(clock.getFactor());
					enemy.setTrajectory(TrajectoryFactory.getTrajectory(enemyData.trajectory, enemy.getPosition()));
					enemy.particleEffectContainer = this;
					enemy.directionAligned = enemyData.directionAligned;
					mEnemies.add(enemy);
				}
			}
		}

		//
		// Draw //////////////////////////////////////////////////////////
		//

		mSpriteBatch.begin();

		mBackground.draw(mSpriteBatch);
		for (Enemy enemy : mEnemies)
			enemy.draw(mSpriteBatch);
		for (Bullet bullet : mBullets)
			bullet.draw(mSpriteBatch);
		for (PlayerShip playerShip : mPlayers)
			playerShip.draw(mSpriteBatch);
		for (Bullet enemyBullet : mEnemyBullets)
			enemyBullet.draw(mSpriteBatch);
		for (ParticleEffect particleEffect : mParticleEffects)
			particleEffect.draw(mSpriteBatch);
		for (Effect effect : mEffects)
			effect.draw(mSpriteBatch);
		mHUD.draw(mSpriteBatch);

		if (state == PAUSE)
		{
			int result = pauseScene.draw(mSpriteBatch);
			if (result == ARCADE)
			{
				state = RUNNING;
				setTimeFactor(1.f);
			}
			else
				returnScene = result;
		}
		if (state == GAMEOVER)
		{
			int result = gameOverScene.draw(mSpriteBatch);
			if (result == ARCADE)
				restartGame();
			else
				returnScene = result;
		}

		// Draw FPS
		mFont.draw(mSpriteBatch, mFPSText, 4, height - 5.f);

		mSpriteBatch.end();

		if (returnScene != NONE && returnScene != ARCADE)
			quit();
		return returnScene;
	}

	public void pauseGame()
	{
		state = PAUSE;
		setTimeFactor(0.f);
		pauseScene.open();
	}

	public void unpauseGame()
	{
		pauseScene.close();
	}

	public void pause()
	{
	}

	public void resume()
	{
		TextureManager.getInstance().reloadAll();
	}

	public void dispose()
	{
	}

	public void restartGame()
	{
		readHighScores();
		// Game Data
		state = RUNNING;
		mCurrentWave = 0;

		// Game Entities
		mBackground = new Background();
		mEnemyBullets = new LinkedList<Bullet>();
		mEnemies = new LinkedList<Enemy>();
		mBullets = new LinkedList<Bullet>();
		mPlayers = new LinkedList<PlayerShip>();
		mShip = PlayerShipFactory.getShip(gameData.pilotType, mBullets,
		        new Vector2(0.f, 0.f), mEnemies);
		mEffects = new LinkedList<Effect>();
		mParticleEffects = new LinkedList<ParticleEffect>();

		// HUD
		mHUD = new HUD();
		mScore.value = 0;

		// Bind
		mShip.score = mScore;
		mShip.hud = mHUD;
		mShip.effectContainer = this;
		mShip.particleEffectContainer = this;
		mHUD.playerShip = mShip;

		// Size
		resize(width, height);

		// Ship
		mShip.setPosition(new Vector2(width / 2, -20.f));
		mShip.moveTarget.set(width / 2, 150.f);
		mPlayers.add(mShip);

		clock = new Clock();
	}

	public void quit()
	{
		if (SettingsScene.musicEnabled)
			music.stop();
		returnScene = MENU;
	}

	@Override public boolean scrolled(int amount)
	{
		return false;
	}

	@Override public boolean touchDragged(int x, int y, int pointer)
	{
		if ((state == RUNNING) && pointer == 0)
		{
			if (!mShip.autoPilot)
				mShip.moveTarget.set(x, height - y
				        + (SettingsScene.controlOffsetEnabled ? 100.f : 0.f));
			return true;
		}
		return false;
	}

	@Override public boolean touchUp(int x, int y, int pointer, int button)
	{
		if ((state == RUNNING) && pointer == 0)
		{
			if (!mShip.autoPilot)
				mShip.setFiring(false);
			return true;
		}
		return false;
	}

	@Override public boolean touchMoved(int x, int y)
	{
		return false;
	}

	@Override public boolean touchDown(int x, int y, int pointer, int button)
	{
		if (state == RUNNING)
		{
			if (!mHUD.onTouchDown(x, height - y, pointer, button) && pointer == 0)
				mShip.setFiring(true);
			return true;
		}
		else if (state == PAUSE)
			return pauseScene.touchDown(x, y, pointer, button);
		else if (state == GAMEOVER)
			return gameOverScene.touchDown(x, y, pointer, button);

		return false;
	}

	@Override public boolean keyDown(int keyCode)
	{
		if (keyCode == Keys.MENU || keyCode == Keys.SPACE || keyCode == Keys.BACK || keyCode == Keys.ESCAPE)
		{
			if (state == RUNNING)
				pauseGame();
			else if (state == PAUSE)
				unpauseGame();
		}

		return true;
	}

	@Override public void addEffect(int effectType)
	{
		switch (effectType)
		{
		case Effect.HEAL:
			mEffects.add(new HealEffect(mShip, this));
			break;
		case Effect.BOMB:
			mEffects.add(new BombEffect(width, height, mShip, mEnemies, mEnemyBullets));
			break;
		case Effect.SHIELD:
			mEffects.add(new ShieldEffect(mShip, mEnemyBullets));
			break;
		case Effect.EXTRALIVES:
			mEffects.add(new ExtralivesEffect(mShip));
			break;

		case Effect.LARAPOWER1:
			mEffects.add(new Lara1Effect());
			break;
		case Effect.SLOWTEMPO:
			mEffects.add(new SlowTempoEffect());
			break;
		case Effect.LARAPOWER3:
			mEffects.add(new Lara3Effect());
			break;
		case Effect.NYANCAT:
			mEffects.add(new NyanCatEffect(this, mShip));
			break;

		case Effect.BUG:
			mEffects.add(new BugEffect());
			break;
		case Effect.HACK:
			mEffects.add(new HackEffect());
			break;
		case Effect.BABYKORBEN:
			mEffects.add(new BabyKorbenEffect());
			break;
		case Effect.ELEET:
			mEffects.add(new EleetEffect());
			break;

		case Effect.TANGO:
			mEffects.add(new TangoEffect());
			break;
		case Effect.QUICKSTEP:
			mEffects.add(new QuickstepEffect());
			break;
		case Effect.FOXTROT:
			mEffects.add(new FoxtrotEffect());
			break;
		case Effect.WALTZ:
			mEffects.add(new WaltzEffect());
			break;

		default:
			break;
		}
	}

	@Override public void addParticleEffect(ParticleEffect particleEffect, float x, float y)
	{
		if (SettingsScene.particlesEnabled)
		{
			mParticleEffects.add(particleEffect);
			particleEffect.setPosition(x, y);
			particleEffect.start();
		}
	}

	public void setTimeFactor(float factor)
	{
		clock.setFactor(factor);

		mBackground.setTimeFactor(factor);
		for (Enemy enemy : mEnemies)
			enemy.setTimeFactor(factor);
		for (Bullet bullet : mBullets)
			bullet.setTimeFactor(factor);
		for (PlayerShip playerShip : mPlayers)
			playerShip.setTimeFactor(factor);
		for (Bullet enemyBullet : mEnemyBullets)
			enemyBullet.setTimeFactor(factor);
		for (Effect effect : mEffects)
			effect.setTimeFactor(factor);
		mHUD.setTimeFactor(factor);
	}

	static public void addHighScore(int score)
	{
		FileHandle handle = Gdx.files.external(".droidshooter/highscores.txt");
		if (!handle.exists())
		{
			FileHandle directory = Gdx.files.external(".droidshooter");
			directory.mkdirs();
			handle.write(false);
		}

		PrintStream ps = new PrintStream(handle.write(true));
		ps.print(Integer.toString(score) + "\n");
	}

	static public int[] readHighScores()
	{
		FileHandle handle = Gdx.files.external(".droidshooter/highscores.txt");

		int scores[] = new int[0];

		if (handle.exists())
		{
			String content = handle.readString();
			String[] scoreStrings = content.split("\n");
			scores = new int[scoreStrings.length];
			for (int i = 0; i < scoreStrings.length; ++i)
			{
				scores[i] = Integer.parseInt(scoreStrings[i]);
			}
		}

		// Sort and reverse array
		Arrays.sort(scores);
		for (int i = 0; i * 2 < scores.length; ++i)
		{
			int tmp = scores[i];
			scores[i] = scores[scores.length - i - 1];
			scores[scores.length - i - 1] = tmp;
		}

		return scores;
	}
}
