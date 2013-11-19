package com.eolhing.droidshooter.GameEntities;

import java.util.Random;
import java.util.LinkedList;
import com.badlogic.gdx.audio.Sound;
import com.eolhing.droidshooter.SettingsScene;
import com.eolhing.droidshooter.GameFactories.BulletFactory;
import com.eolhing.droidshooter.GamePhysics.Physics;
import com.eolhing.droidshooter.Tools.AnimatedValue;
import com.eolhing.droidshooter.Tools.Clock;

public class Weapon
{
	// Target Type
	public static final int RANDOMTARGET = 1;
	public static final int FURTHESTTARGET = 2;
	public static final int CLOSESTTARGET = 3;
	public static final int STRONGESTTARGET = 4;
	public static final int WEAKESTERTARGET = 5;
	public static final int ABSOLUTESTRONGESTTARGET = 6;
	public static final int ABSOLUTEWEAKESTERTARGET = 7;

	private static final Random random = new Random();

	public static class Shot
	{
		public int bulletType;
		public boolean focusTarget;
		public AnimatedValue positionXOffset;
		public AnimatedValue positionYOffset;
		public AnimatedValue directionOffset;
		public AnimatedValue speed;
		public AnimatedValue power;

		public Shot(int bulletType, float positionXOffset, float positionYOffset, float directionOffset, float speed, float power, boolean focusTarget)
		{
			this.bulletType = bulletType;
			this.focusTarget = focusTarget;
			this.positionXOffset = new AnimatedValue(positionXOffset);
			this.positionYOffset = new AnimatedValue(positionYOffset);
			this.directionOffset = new AnimatedValue(directionOffset);
			this.speed = new AnimatedValue(speed);
			this.power = new AnimatedValue(power);
		}
	}

	// IA
	public Ship ownerShip;
	public LinkedList<? extends Ship> targets;
	public LinkedList<Shot> shots;

	// Logic
	public boolean firing;
	public boolean persistant;
	public boolean shootAllowed;
	public long reloadTime;
	public AnimatedValue positionXOffset;
	public AnimatedValue positionYOffset;
	public AnimatedValue directionOffset;
	public boolean shootTowardsTarget;
	public int targetType;

	// Clocks
	private Clock reloadClock;

	// Sounds
	public Sound shootSound;

	public Weapon(Ship ownerShip)
	{
		// IA
		this.ownerShip = ownerShip;
		shots = new LinkedList<Shot>();
		targets = new LinkedList<Ship>();

		// Logic
		firing = false;
		persistant = false;
		shootAllowed = true;
		reloadTime = 1000;
		positionXOffset = new AnimatedValue(0.f);
		positionYOffset = new AnimatedValue(0.f);
		directionOffset = new AnimatedValue(0.f);
		shootTowardsTarget = false;
		targetType = RANDOMTARGET;

		// Clocks
		reloadClock = new Clock();
	}

	public Weapon(Ship ownerShip, LinkedList<? extends Ship> targets)
	{
		// IA
		this.ownerShip = ownerShip;
		shots = new LinkedList<Shot>();
		this.targets = targets;

		// Logic
		firing = false;
		persistant = false;
		shootAllowed = true;
		reloadTime = 1000;
		positionXOffset = new AnimatedValue(0.f);
		positionYOffset = new AnimatedValue(0.f);
		directionOffset = new AnimatedValue(0.f);
		shootTowardsTarget = false;
		targetType = RANDOMTARGET;

		// Clocks
		reloadClock = new Clock();
	}

	public float getX()
	{
		return ownerShip.getX() + positionXOffset.getValue();
	}

	public float getY()
	{
		return ownerShip.getY() + positionYOffset.getValue();
	}

	public boolean update()
	{
		if (firing && ((!persistant && reloadClock.getTime() >= reloadTime) || (persistant && shootAllowed)))
		{
			// Choose target
			Ship target = chooseTarget();

			// Compute position
			float positionX = ownerShip.getX() + positionXOffset.getValue();
			float positionY = ownerShip.getY() + positionYOffset.getValue();

			// Compute shoot direction
			float shootDirection = 0.f;
			if (shootTowardsTarget && target != null)
				shootDirection = (float) Math.atan2(target.getPosition().y - positionY, target.getPosition().x - positionX);
			else
				shootDirection = ownerShip.getDirection() + directionOffset.getValue();

			// Create bullets from shots
			for (Shot shot : shots)
			{
				Bullet bullet = BulletFactory.getBullet(shot.bulletType, ownerShip, shot.focusTarget ? target : null, positionX + shot.positionXOffset.getValue(), positionY + shot.positionYOffset.getValue(), shootDirection + shot.directionOffset.getValue(), shot.speed.getValue(), shot.power.getValue());
				bullet.setTimeFactor(ownerShip.clock.getFactor());
				bullet.originWeapon = this;
				ownerShip.bullets.add(bullet);
			}

			// Play shoot sound
			if (SettingsScene.soundEnabled)
			{
				shootSound.stop();
				shootSound.play(0.1f);
			}

			// Reset reload clock
			reloadClock.reset();
			shootAllowed = false;
			return true;
		}
		return false;
	}

	public void setFiring(boolean firing)
	{
		this.firing = firing;
		directionOffset.reset();
		if (!firing)
			shootAllowed = true;
	}

	public void setTimeFactor(float factor)
	{
		reloadClock.setFactor(factor);
		directionOffset.setTimeFactor(factor);
		positionXOffset.setTimeFactor(factor);
		positionYOffset.setTimeFactor(factor);
		for (Shot shot : shots)
		{
			shot.directionOffset.setTimeFactor(factor);
			shot.speed.setTimeFactor(factor);
			shot.power.setTimeFactor(factor);
		}
	}

	private Ship chooseTarget()
	{
		Ship target = null;

		if (targets != null && targets.size() > 0)
		{
			switch (targetType)
			{
			case RANDOMTARGET:
				target = targets.get(Math.abs(random.nextInt() % targets.size()));
				break;

			case FURTHESTTARGET:
				target = targets.get(0);
				float maxDistance = Physics.pointPointDistance(getX(), getY(), targets.get(0).getX(), targets.get(0).getY());
				for (Ship ship : targets)
				{
					float distance = Physics.pointPointDistance(getX(), getY(), ship.getX(), ship.getY());
					if (distance > maxDistance)
						target = ship;
				}
				break;

			case CLOSESTTARGET:
				target = targets.get(0);
				float minDistance = Physics.pointPointDistance(getX(), getY(), targets.get(0).getX(), targets.get(0).getY());
				for (Ship ship : targets)
				{
					float distance = Physics.pointPointDistance(getX(), getY(), ship.getX(), ship.getY());
					if (distance < minDistance)
						target = ship;
				}
				break;

			case STRONGESTTARGET:
				target = targets.get(0);
				for (Ship ship : targets)
				{
					if (ship.health > target.health)
						target = ship;
				}
				break;

			case WEAKESTERTARGET:
				target = targets.get(0);
				for (Ship ship : targets)
				{
					if (ship.health < target.health)
						target = ship;
				}
				break;

			case ABSOLUTESTRONGESTTARGET:
				target = targets.get(0);
				for (Ship ship : targets)
				{
					if (ship.maxHealth > target.maxHealth)
						target = ship;
				}
				break;

			case ABSOLUTEWEAKESTERTARGET:
				target = targets.get(0);
				for (Ship ship : targets)
				{
					if (ship.maxHealth < target.maxHealth)
						target = ship;
				}
				break;
			}
		}

		return target;
	}
}