package com.eolhing.droidshooter.GameFactories;

import java.util.LinkedList;
import com.badlogic.gdx.math.Vector2;
import com.eolhing.droidshooter.GameEntities.Bullet;
import com.eolhing.droidshooter.GameEntities.Effect;
import com.eolhing.droidshooter.GameEntities.PlayerShip;
import com.eolhing.droidshooter.GameEntities.Ship;
import com.eolhing.droidshooter.Tools.TextureManager;

public class PlayerShipFactory
{
	// Enemy Types
	public static final int TIM = 0;
	public static final int LARA = 1;
	public static final int KORBEN = 2;
	public static final int AIVI = 3;

	static public PlayerShip getShip(int type, LinkedList<Bullet> bullets, Vector2 position, LinkedList<? extends Ship> targets)
	{
		PlayerShip ship = new PlayerShip(bullets, position);

		switch (type)
		{
		case TIM:
			// Characteristics
			ship.speed = 450.f;
			// Look
			ship.setTextureRegion(TextureManager.ship);
			// Weapons
			ship.weapons.add(WeaponFactory.getWeapon(WeaponFactory.FURYCANON, ship, targets));
			// Powers
			ship.powerType1 = Effect.HEAL;
			ship.powerType2 = Effect.BOMB;
			ship.powerType3 = Effect.SHIELD;
			ship.powerType4 = Effect.EXTRALIVES;
			break;

		case LARA:
			// Characteristics
			ship.speed = 500.f;
			// Look
			ship.setTextureRegion(TextureManager.ship);
			// Weapons
			ship.weapons.add(WeaponFactory.getWeapon(WeaponFactory.FURYCANON, ship, targets));
			// Powers
			ship.powerType1 = Effect.LARAPOWER1;
			ship.powerType2 = Effect.SLOWTEMPO;
			ship.powerType3 = Effect.LARAPOWER3;
			ship.powerType4 = Effect.NYANCAT;
			break;

		case KORBEN:
			// Characteristics
			ship.speed = 400.f;
			// Look
			ship.setTextureRegion(TextureManager.ship);
			// Weapons
			ship.weapons.add(WeaponFactory.getWeapon(WeaponFactory.ALMLAUNCHER, ship, targets));
			// Powers
			ship.powerType1 = Effect.BUG;
			ship.powerType2 = Effect.HACK;
			ship.powerType3 = Effect.BABYKORBEN;
			ship.powerType4 = Effect.ELEET;
			break;

		case AIVI:
			// Characteristics
			ship.speed = 600.f;
			// Look
			ship.setTextureRegion(TextureManager.ship);
			// Weapons
			ship.weapons.add(WeaponFactory.getWeapon(WeaponFactory.RAYCANON, ship, targets));
			ship.weapons.add(WeaponFactory.getWeapon(WeaponFactory.INVERTEDRAYCANON, ship, targets));
			// Powers
			ship.powerType1 = Effect.TANGO;
			ship.powerType2 = Effect.QUICKSTEP;
			ship.powerType3 = Effect.FOXTROT;
			ship.powerType4 = Effect.WALTZ;
			break;

		default:
			break;
		}

		return ship;
	}
}