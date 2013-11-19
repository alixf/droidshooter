package com.eolhing.droidshooter.GameFactories;

import java.util.LinkedList;
import com.badlogic.gdx.math.Vector2;
import com.eolhing.droidshooter.GameEntities.Bullet;
import com.eolhing.droidshooter.GameEntities.Enemy;
import com.eolhing.droidshooter.GameEntities.Ship;
import com.eolhing.droidshooter.GamePhysics.Physics;
import com.eolhing.droidshooter.Tools.TextureManager;

public class EnemyFactory
{
	// Enemy Types
	public static final int DROID = 0;
	public static final int FASTDROID = 1;
	public static final int BOSS = 2;
	public static final int SNAKEHEAD = 3;
	public static final int SNAKEBODY = 4;
	public static final int SNAKETAIL = 5;

	static public Enemy getEnemy(int type, LinkedList<Bullet> bullets, Vector2 position, LinkedList<? extends Ship> targets)
	{
		Enemy enemy = new Enemy(bullets, position);

		switch (type)
		{
		case DROID:
			enemy.speed = 75.f;
			enemy.setTextureRegion(TextureManager.droid);
			enemy.collisionType = Physics.BSPHERE;
			enemy.collisionSphere.x = position.x;
			enemy.collisionSphere.y = position.y;
			enemy.collisionSphere.radius = 10.f;
			enemy.weapons.add(WeaponFactory.getWeapon(WeaponFactory.CANON, enemy, targets));
			break;

		case FASTDROID:
			enemy.speed = 150.f;
			enemy.setTextureRegion(TextureManager.droid);
			enemy.collisionType = Physics.BSPHERE;
			enemy.collisionSphere.radius = 10.f;
			enemy.weapons.add(WeaponFactory.getWeapon(WeaponFactory.FLOWERCANON12, enemy, targets));
			break;

		case BOSS:
			enemy.speed = 100.f;
			enemy.setTextureRegion(TextureManager.droid);
			enemy.collisionType = Physics.BSPHERE;
			enemy.collisionSphere.radius = 10.f;
			enemy.weapons.add(WeaponFactory.getWeapon(WeaponFactory.DOUBLESPIRALCANON, enemy, targets));
			enemy.health = 10000;
			break;

		case SNAKEHEAD:
			enemy.speed = 200.f;
			enemy.setTextureRegion(TextureManager.snakeHead);
			enemy.collisionType = Physics.BSPHERE;
			enemy.collisionSphere.radius = 10.f;
			enemy.health = 3000;
			enemy.weapons.add(WeaponFactory.getWeapon(WeaponFactory.BICANON, enemy, targets));
			break;

		case SNAKEBODY:
			enemy.speed = 200.f;
			enemy.setTextureRegion(TextureManager.snakeBody);
			enemy.collisionType = Physics.BSPHERE;
			enemy.collisionSphere.radius = 10.f;
			enemy.health = 1000;
			enemy.weapons.add(WeaponFactory.getWeapon(WeaponFactory.BICANON, enemy, targets));
			break;

		case SNAKETAIL:
			enemy.speed = 200.f;
			enemy.setTextureRegion(TextureManager.snakeTail);
			enemy.collisionType = Physics.BSPHERE;
			enemy.collisionSphere.radius = 10.f;
			enemy.health = 2000;
			enemy.weapons.add(WeaponFactory.getWeapon(WeaponFactory.BICANON, enemy, targets));
			break;

		default:
			break;
		}

		enemy.setFiring(true);

		return enemy;
	}
}