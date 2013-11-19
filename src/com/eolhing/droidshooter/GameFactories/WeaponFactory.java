package com.eolhing.droidshooter.GameFactories;

import java.util.LinkedList;
import java.util.List;

import com.eolhing.droidshooter.GameEntities.Ship;
import com.eolhing.droidshooter.GameEntities.Weapon;
import com.eolhing.droidshooter.Tools.AnimatedValue;
import com.eolhing.droidshooter.Tools.SoundManager;

public class WeaponFactory
{
	// Player's Weapons
	public static final int DOUBLECANON = 1;
	public static final int TRIPLECANON = 2;
	public static final int FURYCANON = 3;
	public static final int CIRCLECANON = 4;
	public static final int ARROWSCANON = 5;
	public static final int LARAWEAPON3 = 6;
	public static final int ALMLAUNCHER = 7;
	public static final int QALMLAUNCHER = 8;
	public static final int FALMLAUNCHER = 9;
	public static final int RAYCANON = 10;
	public static final int INVERTEDRAYCANON = 23;
	public static final int SRAYCANON = 11;
	public static final int CRAYCANON = 12;
	// Enemy's Weapons
	public static final int CANON = 13;
	public static final int BICANON = 14;
	public static final int TRICANON = 15;
	public static final int BIDENTCANON = 16;
	public static final int TRIDENTCANON = 17;
	public static final int FLOWERCANON6 = 18;
	public static final int FLOWERCANON8 = 19;
	public static final int FLOWERCANON12 = 20;
	public static final int SPIRALCANON = 21;
	public static final int DOUBLESPIRALCANON = 22;

	public static Weapon getWeapon(int type, Ship ownerShip)
	{
		return getWeapon(type, ownerShip, null);
	}

	public static Weapon getWeapon(int type, Ship ownerShip, LinkedList<? extends Ship> targets)
	{
		Weapon weapon = new Weapon(ownerShip, targets);
		weapon.targets = targets;
		weapon.shootSound = SoundManager.getInstance().getSound("shoot.wav");

		List<Weapon.Shot> shots = weapon.shots;

		switch (type)
		{
		// Player's Weapons
		case DOUBLECANON:
			weapon.reloadTime = 100;
			weapon.shootTowardsTarget = false;
			shots.add(new Weapon.Shot(BulletFactory.BLUEBULLET, -10.f, 0.f, 0.f, 750.f, 100.f, false));
			shots.add(new Weapon.Shot(BulletFactory.BLUEBULLET, 10.f, 0.f, 0.f, 750.f, 100.f, false));
			break;

		case TRIPLECANON:
			weapon.reloadTime = 100;
			weapon.shootTowardsTarget = false;
			shots.add(new Weapon.Shot(BulletFactory.BLUEBULLET, -10.f, 0.f, 0.f, 750.f, 100.f, false));
			shots.add(new Weapon.Shot(BulletFactory.BLUEBULLET, 0.f, 0.f, 0.f, 750.f, 100.f, false));
			shots.add(new Weapon.Shot(BulletFactory.BLUEBULLET, 10.f, 0.f, 0.f, 750.f, 100.f, false));
			break;

		case FURYCANON:
		{
			weapon.reloadTime = 100;
			weapon.shootTowardsTarget = false;
			// TODO Sin trajectory
			Weapon.Shot shot = new Weapon.Shot(BulletFactory.BLUEBULLET, -15.f, -10.f, 0.f, 750.f, 100.f, false);
			shots.add(shot);
			shot = new Weapon.Shot(BulletFactory.BLUEBULLET, 15.f, -10.f, 0.f, 750.f, 100.f, false);
			shots.add(shot);
			shots.add(new Weapon.Shot(BulletFactory.BLUEBULLET, -10.f, 0.f, 0.f, 750.f, 100.f, false));
			shots.add(new Weapon.Shot(BulletFactory.BLUEBULLET, 10.f, 0.f, 0.f, 750.f, 100.f, false));
			shots.add(new Weapon.Shot(BulletFactory.BLUEBULLET, 0.f, 10.f, 0.f, 750.f, 100.f, false));
		}
			break;

		case CIRCLECANON:
			// TODO Implement Weapon
			break;

		case ARROWSCANON:
			// TODO Implement Weapon
			break;

		case LARAWEAPON3:
			// TODO Implement Weapon
			break;

		case ALMLAUNCHER:
			weapon.reloadTime = 100;
			weapon.shootTowardsTarget = false;
			weapon.directionOffset = new AnimatedValue(AnimatedValue.RANDOM, 0.f, (float) Math.PI * 2.f);
			shots.add(new Weapon.Shot(BulletFactory.MISSILE, 0.f, 10.f, 0.f, 300.f, 200.f, true));
			break;

		case QALMLAUNCHER:
			weapon.reloadTime = 75;
			weapon.shootTowardsTarget = false;
			shots.add(new Weapon.Shot(BulletFactory.MISSILE, 0.f, 10.f, 0.f, 300.f, 250.f, true));
			break;

		case FALMLAUNCHER:
			weapon.reloadTime = 80;
			weapon.shootTowardsTarget = false;
			shots.add(new Weapon.Shot(BulletFactory.MISSILE, 0.f, 10.f, 0.f, 300.f, 200.f, true));
			break;

		case RAYCANON:
			weapon.persistant = true;
			weapon.shootTowardsTarget = false;
			weapon.directionOffset = new AnimatedValue(AnimatedValue.SINUSOIDAL, -(float) Math.PI / 8, (float) Math.PI / 8, 1500);
			shots.add(new Weapon.Shot(BulletFactory.RAY, 0.f, 0.f, 0.f, 100.f, 200.f, false));
			break;

		case INVERTEDRAYCANON:
			weapon.persistant = true;
			weapon.shootTowardsTarget = false;
			weapon.directionOffset = new AnimatedValue(AnimatedValue.SINUSOIDAL, (float) Math.PI / 8, -(float) Math.PI / 8, 1500);
			shots.add(new Weapon.Shot(BulletFactory.RAY, 0.f, 0.f, 0.f, 100.f, 200.f, false));
			break;

		case SRAYCANON:
			// TODO Implement Weapon
			break;

		case CRAYCANON:
			// TODO Implement Weapon
			break;

		// Enemy's Weapons
		case CANON:
			weapon.shootTowardsTarget = true;
			shots.add(new Weapon.Shot(BulletFactory.REDBULLET, 0.f, 10.f, 0.f, 300.f, 100.f, false));
			break;

		case BICANON:
			weapon.shootTowardsTarget = true;
			shots.add(new Weapon.Shot(BulletFactory.REDBULLET, -10.f, 0.f, 0.f, 750.f, 100.f, false));
			shots.add(new Weapon.Shot(BulletFactory.REDBULLET, 10.f, 0.f, 0.f, 750.f, 100.f, false));
			break;

		case TRICANON:
			weapon.shootTowardsTarget = true;
			shots.add(new Weapon.Shot(BulletFactory.REDBULLET, -10.f, 0.f, 0.f, 750.f, 100.f, false));
			shots.add(new Weapon.Shot(BulletFactory.REDBULLET, 0.f, 0.f, 0.f, 750.f, 100.f, false));
			shots.add(new Weapon.Shot(BulletFactory.REDBULLET, 10.f, 0.f, 0.f, 750.f, 100.f, false));

		case BIDENTCANON:
			weapon.shootTowardsTarget = true;
			shots.add(new Weapon.Shot(BulletFactory.REDBULLET, 0.f, 10.f, (float) Math.PI / 9.f, 300.f, 100.f, false));
			shots.add(new Weapon.Shot(BulletFactory.REDBULLET, 0.f, 10.f, (float) -Math.PI / 9.f, 300.f, 100.f, false));
			break;

		case TRIDENTCANON:
			weapon.shootTowardsTarget = true;
			for (int i = -1; i <= 1; ++i)
				shots.add(new Weapon.Shot(BulletFactory.REDBULLET, 0.f, 10.f, i * (float) Math.PI / 9.f, 300.f, 100.f, false));
			break;

		case FLOWERCANON6:
			for (int i = 1; i <= 6; ++i)
				shots.add(new Weapon.Shot(BulletFactory.FLOWERBULLET, 0.f, 10.f, i * (float) Math.PI / 3.f, 200.f, 100.f, false));
			break;

		case FLOWERCANON8:
			for (int i = 1; i <= 8; ++i)
				shots.add(new Weapon.Shot(BulletFactory.FLOWERBULLET, 0.f, 10.f, i * (float) Math.PI / 4.f, 200.f, 100.f, false));
			break;

		case FLOWERCANON12:
			for (int i = 1; i <= 12; ++i)
				shots.add(new Weapon.Shot(BulletFactory.FLOWERBULLET, 0.f, 10.f, i * (float) Math.PI / 6.f, 200.f, 100.f, false));
			break;

		case SPIRALCANON:
		{
			weapon.reloadTime = 25;
			Weapon.Shot shot = new Weapon.Shot(BulletFactory.REDBULLET, 0.f, 10.f, 0.f, 200.f, 100.f, false);
			shot.directionOffset = new AnimatedValue(AnimatedValue.LINEAR, 150, (float) Math.PI * 2);
			shots.add(shot);
		}
			break;

		case DOUBLESPIRALCANON:
		{
			weapon.reloadTime = 25;
			Weapon.Shot shot = new Weapon.Shot(BulletFactory.REDBULLET, 0.f, 10.f, 0.f, 200.f, 100.f, false);
			shot.directionOffset = new AnimatedValue(AnimatedValue.LINEAR, 0, (float) Math.PI * 2);
			shots.add(shot);
			shot = new Weapon.Shot(BulletFactory.REDBULLET, 0.f, 10.f, 0.f, 200.f, 100.f, false);
			shot.directionOffset = new AnimatedValue(AnimatedValue.LINEAR, (float) Math.PI / 2, (float) Math.PI);
			shots.add(shot);
		}
			break;

		default:
			break;
		}

		return weapon;
	}
}