package com.eolhing.droidshooter.GameEntities;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.eolhing.droidshooter.ArcadeScene;
import com.eolhing.droidshooter.SettingsScene;

public class PlayerShip extends Ship
{
	public HUD hud;
	public ArcadeScene.Score score;
	public boolean autoPilot;

	public int powerType1;
	public int powerType2;
	public int powerType3;
	public int powerType4;

	public float hitPowerGainFactor;
	public float destroyPowerGainFactor;

	public PlayerShip(LinkedList<Bullet> bullets, Vector2 position)
	{
		super(bullets, position);

		speed = 500.f;

		hud = null;
		score = null;

		sprite.rotate(90);
		setPosition(position);
		moveTarget.set(position);

		autoPilot = false;

		powerType1 = Effect.NONE;
		powerType2 = Effect.NONE;
		powerType3 = Effect.NONE;
		powerType4 = Effect.NONE;

		hitPowerGainFactor = 0.003f;
		destroyPowerGainFactor = 0.01f;

		clock.reset();
	}

	@Override public void collide(Bullet bullet)
	{
		if (SettingsScene.vibrateEnabled)
			Gdx.input.vibrate(350);
		super.collide(bullet);
		if (hud != null)
			hud.setHealth(health / maxHealth);
	}

	@Override public void onHitShip(Bullet bullet)
	{
		super.onHitShip(bullet);
		power = Math.min(power + bullet.power.getValue() * hitPowerGainFactor, maxPower);
		if (hud != null)
			hud.setPower(power / maxPower);
		if (score != null)
			score.value += 1;
	}

	@Override public void onDestroyShip(Bullet bullet)
	{
		super.onDestroyShip(bullet);
		power = Math.min(power + bullet.power.getValue() * destroyPowerGainFactor, maxPower);
		if (hud != null)
			hud.setPower(power / maxPower);
		if (score != null)
			score.value += 10;
	}

	@Override public void castEffect()
	{
		super.castEffect();

		float powerValue = power / maxPower;
		boolean enoughPower = powerValue >= 0.25f;
		int effectType = 0;

		if (powerValue >= 1.f)
			effectType = powerType4;
		else if (powerValue >= 0.75f)
			effectType = powerType3;
		else if (powerValue >= 0.5f)
			effectType = powerType2;
		else if (powerValue >= 0.25f)
			effectType = powerType1;

		if (enoughPower && effectContainer != null)
		{
			power = 0.f;
			effectContainer.addEffect(effectType);
			if (hud != null)
			{
				hud.setHealth(health / maxHealth);
				hud.setPower(power / maxPower);
			}
		}
	}

	@Override public void addHealth(float healthOffset)
	{
		super.addHealth(healthOffset);
		hud.setHealth(health / maxHealth);
	}
}