package com.eolhing.droidshooter.GameEntities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eolhing.droidshooter.Tools.Clock;
import com.eolhing.droidshooter.Tools.TextureManager;

public class HUD
{
	float speed;

	// Health Gauge
	float mHealthValue;
	float mHealthValueDisplayed;
	Sprite mHealthGaugeSprite;
	Rectangle mHealthFillRect;
	Sprite mHealthFillSprite;

	// Power Gauge
	float mPowerValue;
	float mPowerValueDisplayed;
	Sprite mPowerGaugeSprite;
	Rectangle mPowerFillRect;
	Sprite mPowerFillSprite;

	Rectangle effectCastRect;

	// Effect stuff
	public PlayerShip playerShip;

	// Clock
	Clock clock;

	public HUD()
	{
		mHealthValue = 1.f;
		mHealthValueDisplayed = 1.f;
		mHealthGaugeSprite = new Sprite(TextureManager.healthGauge);

		mHealthFillRect = new Rectangle();
		mHealthFillSprite = new Sprite(TextureManager.healthFill);

		mPowerValue = 0.f;
		mPowerValueDisplayed = 0.f;
		mPowerGaugeSprite = new Sprite(TextureManager.powerGauge);

		mPowerFillRect = new Rectangle();
		mPowerFillSprite = new Sprite(TextureManager.powerFill);

		speed = 1.f;

		effectCastRect = new Rectangle();

		clock = new Clock();
	}

	public void setHealth(float health)
	{
		mHealthValue = health;
	}

	public void setPower(float power)
	{
		mPowerValue = power;
	}

	public void setScreenSize(int width, int height)
	{
		mPowerGaugeSprite.setPosition((float) width - mPowerGaugeSprite.getWidth(), 0.f);
		mHealthFillRect.set(mHealthGaugeSprite.getX() + 8, mHealthGaugeSprite.getY() + 32, 14, 244);
		mPowerFillRect.set(mPowerGaugeSprite.getX() + 48, mPowerGaugeSprite.getY() + 32, 14, 244);
		mHealthFillSprite.setBounds(mHealthFillRect.x, mHealthFillRect.y, mHealthFillRect.width, mHealthFillRect.height * mHealthValueDisplayed);
		mPowerFillSprite.setBounds(mPowerFillRect.x, mPowerFillRect.y, mPowerFillRect.width, mPowerFillRect.height * mPowerValueDisplayed);
		effectCastRect.set(mPowerGaugeSprite.getX() + 40, mPowerGaugeSprite.getY() + 32, 30, 264);
	}

	public void update()
	{
		float time = clock.getTime() / 1000.f;
		clock.reset();

		if (mHealthValueDisplayed != mHealthValue)
		{
			if (speed * time >= Math.abs(mHealthValue - mHealthValueDisplayed))
				mHealthValueDisplayed = mHealthValue;
			else
				mHealthValueDisplayed += speed * time * (mHealthValue < mHealthValueDisplayed ? -1.f : 1.f);

			mHealthFillSprite.setBounds(mHealthFillRect.x, mHealthFillRect.y, mHealthFillRect.width, mHealthFillRect.height * mHealthValueDisplayed);
		}

		if (mPowerValueDisplayed != mPowerValue)
		{
			if (speed * time >= Math.abs(mPowerValue - mPowerValueDisplayed))
				mPowerValueDisplayed = mPowerValue;
			else
				mPowerValueDisplayed += speed * time * (mPowerValue < mPowerValueDisplayed ? -1.f : 1.f);

			mPowerFillSprite.setBounds(mPowerFillRect.x, mPowerFillRect.y, mPowerFillRect.width, mPowerFillRect.height * mPowerValueDisplayed);
		}
	}

	public void draw(SpriteBatch batch)
	{
		mHealthFillSprite.draw(batch);
		mPowerFillSprite.draw(batch);
		mHealthGaugeSprite.draw(batch);
		mPowerGaugeSprite.draw(batch);
	}

	public boolean onTouchDown(int x, int y, int pointer, int button)
	{
		if (mPowerFillRect.contains(x, y))
		{
			playerShip.castEffect();
			return true;
		}
		return false;
	}

	public void setTimeFactor(float factor)
	{
		clock.setFactor(factor);
	}
}