package com.eolhing.droidshooter.GameEffects;

import java.util.LinkedList;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.eolhing.droidshooter.GameEntities.Bullet;
import com.eolhing.droidshooter.GameEntities.Effect;
import com.eolhing.droidshooter.GameEntities.Enemy;
import com.eolhing.droidshooter.GameEntities.Ship;
import com.eolhing.droidshooter.Tools.AnimatedValue;
import com.eolhing.droidshooter.Tools.TextureManager;

import java.util.ListIterator;

public class BombEffect extends Effect
{
	float width;
	float height;

	Circle area;
	float maxRadiusDistance;
	AnimatedValue spreadingSpeed;
	float power;
	Vector2 launchPosition;
	Sprite bombSprite;

	LinkedList<Enemy> mEnemies;
	LinkedList<Bullet> mEnemiesBullets;

	public BombEffect(float width, float height, Ship launcher, LinkedList<Enemy> enemies, LinkedList<Bullet> enemiesBullets)
	{
		super();
		mEnemies = enemies;
		mEnemiesBullets = enemiesBullets;

		launchPosition = launcher.getPosition();
		maxRadiusDistance = Math.max(Math.max(launchPosition.x, width - launchPosition.x), Math.max(launchPosition.y, height - launchPosition.y));
		area = new Circle(launchPosition.x, launchPosition.y, 0.f);
		spreadingSpeed = new AnimatedValue(AnimatedValue.INTERPOLATION, 10.f, 1000.f, 2000);
		power = 100.f;
		bombSprite = new Sprite(TextureManager.bombEffect);
		bombSprite.setPosition(launchPosition.x - bombSprite.getWidth() / 2, launchPosition.y - bombSprite.getHeight() / 2);

		clock.reset();
	}

	@Override public void update()
	{
		super.update();

		float time = clock.getTime() / 1000.f;

		area.radius = spreadingSpeed.getValue() * (float) (Math.exp(time) - 1.f);
		bombSprite.setScale(area.radius / (bombSprite.getWidth() / 2));

		// TODO Make bomb effect on enemies framerate independant (single hit,
		// hit over a certain period of time ?)
		ListIterator<Enemy> enemyIterator = mEnemies.listIterator();
		while (enemyIterator.hasNext())
		{
			Enemy enemy = enemyIterator.next();
			if (area.contains(enemy.getPosition()))
				enemy.addHealth(-100.f);
		}

		ListIterator<Bullet> bulletIterator = mEnemiesBullets.listIterator();
		while (bulletIterator.hasNext())
		{
			Bullet bullet = bulletIterator.next();
			if (area.contains(bullet.getPosition()))
				bullet.isAlive = false;
		}

		if (area.radius >= maxRadiusDistance)
			state = FINISHED;
	}

	@Override public void draw(SpriteBatch batch)
	{
		super.draw(batch);

		bombSprite.draw(batch, 0.5f);
	}
}
