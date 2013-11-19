package com.eolhing.droidshooter.GameFactories;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eolhing.droidshooter.GameEntities.Bullet;
import com.eolhing.droidshooter.GameEntities.Ship;
import com.eolhing.droidshooter.GamePhysics.Physics;
import com.eolhing.droidshooter.Tools.AnimatedValue;
import com.eolhing.droidshooter.Tools.TextureManager;

import java.util.Random;

public class BulletFactory
{
	// Bullet Types
	public static final int BLUEBULLET = 0;
	public static final int REDBULLET = 1;
	public static final int GREENBULLET = 2;
	public static final int FLOWERBULLET = 3;
	public static final int RAY = 4;
	public static final int MISSILE = 5;

	private static Random random = new Random();

	public static Bullet getBullet(int type, Ship originShip, Ship targetShip, float x, float y, float direction, float speed, float power)
	{
		Bullet bullet = new Bullet(originShip, targetShip, x, y, direction, speed, power);

		switch (type)
		{
		case REDBULLET:
			bullet.setTextureRegion(TextureManager.redBullet);
			bullet.rotationSpeed = new AnimatedValue((float) Math.PI);
			bullet.collisionType = Physics.BSPHERE;
			bullet.collisionSphere.x = x;
			bullet.collisionSphere.y = y;
			bullet.collisionSphere.radius = 10.f;
			break;

		case GREENBULLET:
			bullet.setTextureRegion(TextureManager.greenBullet);
			bullet.rotationSpeed = new AnimatedValue((float) Math.PI);
			bullet.collisionType = Physics.BSPHERE;
			bullet.collisionSphere.x = x;
			bullet.collisionSphere.y = y;
			bullet.collisionSphere.radius = 10.f;
			break;

		case BLUEBULLET:
			bullet.setTextureRegion(TextureManager.blueBullet);
			bullet.rotationSpeed = new AnimatedValue((float) Math.PI);
			bullet.collisionType = Physics.BSPHERE;
			bullet.collisionSphere.x = x;
			bullet.collisionSphere.y = y;
			bullet.collisionSphere.radius = 10.f;
			break;

		case FLOWERBULLET:
			TextureRegion t;
			switch (random.nextInt(3))
			{
			case 0:
				t = TextureManager.blueBullet;
				break;
			case 1:
				t = TextureManager.greenBullet;
				break;
			case 2:
				t = TextureManager.redBullet;
				break;
			default:
				t = TextureManager.blueBullet;
				break;
			}
			bullet.setTextureRegion(t);
			bullet.direction = new AnimatedValue(AnimatedValue.LINEAR, direction, (float) Math.PI / 6);
			bullet.collisionType = Physics.BSPHERE;
			bullet.collisionSphere.x = x;
			bullet.collisionSphere.y = y;
			bullet.collisionSphere.radius = 10.f;
			break;

		case RAY:
			bullet.speed = new AnimatedValue(100.f);
			bullet.setTextureRegion(TextureManager.ray);
			bullet.timeBetweenHits = 50;
			bullet.hitsLeft = 0;
			bullet.scaleX(1000.f);
			bullet.syncWithWeapon = true;
			bullet.collisionType = Physics.RAY;
			bullet.collisionRay.x = x;
			bullet.collisionRay.y = y;
			bullet.collisionRay.angle = direction;
			bullet.collisionRay.thickness = 2.5f;
			break;

		case MISSILE:
			bullet.setTextureRegion(TextureManager.blueBullet);
			bullet.rotationSpeed = new AnimatedValue((float) Math.PI * 2);
			bullet.collisionType = Physics.BSPHERE;
			bullet.collisionSphere.x = x;
			bullet.collisionSphere.y = y;
			bullet.collisionSphere.radius = 10.f;
		default:
			break;
		}

		return bullet;
	}
}