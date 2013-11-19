package com.eolhing.droidshooter.GameEntities;

import java.util.LinkedList;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.eolhing.droidshooter.GamePhysics.Collidable;
import com.eolhing.droidshooter.GamePhysics.Physics;
import com.eolhing.droidshooter.Tools.Clock;

public class Ship extends Collidable
{
	public static final int ALIVE = 1;
	public static final int DESTROYING = 2;
	public static final int DESTROYED = 3;

	protected Clock clock;
	public int state;
	public boolean directionAligned;

	protected Sprite sprite;
	protected Vector2 origin;
	public Vector2 moveTarget;

	public float speed;

	public float maxHealth;
	public float health;
	public float maxPower;
	public float power;

	public LinkedList<Bullet> bullets;
	public LinkedList<Weapon> weapons;

	public EffectContainer effectContainer;
	public ParticlesContainer particleEffectContainer;

	float mAlpha;

	public Ship(LinkedList<Bullet> bullets, Vector2 position)
	{
		super();

		clock = new Clock();
		state = ALIVE;
		mAlpha = 1.f;
		directionAligned = false;

		sprite = new Sprite();
		origin = new Vector2();
		moveTarget = new Vector2();

		speed = 0.f;
		maxHealth = 500.f;
		health = maxHealth;
		maxPower = 100.f;
		power = 0.f;

		this.bullets = bullets;
		weapons = new LinkedList<Weapon>();

		setPosition(position);
		moveTarget.set(position);

		collisionType = Physics.POINT;

		clock.reset();
	}

	public float getX()
	{
		return sprite.getX() + origin.x;
	}

	public float getY()
	{
		return sprite.getY() + origin.y;
	}

	public Vector2 getPosition()
	{
		return new Vector2(sprite.getX() + origin.x, sprite.getY() + origin.y);
	}

	public void setPosition(Vector2 position)
	{
		if (moveTarget.x == sprite.getX() + origin.x && moveTarget.y == sprite.getY() + origin.y)
			moveTarget = position;
		sprite.setPosition(position.x - origin.x, position.y - origin.y);
	}

	public void setTextureRegion(TextureRegion textureRegion)
	{
		sprite.setRegion(textureRegion);
		origin.set(textureRegion.getRegionWidth() / 2, textureRegion.getRegionHeight() / 2);
		sprite.setBounds(sprite.getX() - origin.x, sprite.getY() - origin.y, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
		sprite.setOrigin(sprite.getRegionWidth() / 2, sprite.getRegionHeight() / 2);
	}

	public Rectangle getRect()
	{
		return sprite.getBoundingRectangle();
	}

	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch, mAlpha);
	}

	public void collide(Bullet bullet)
	{
		if (state != DESTROYED && state != DESTROYING)
		{
			health = Math.max(0.f, health - bullet.power.getValue());
			if (health <= 0)
			{
				destroy();
				bullet.originShip.onDestroyShip(bullet);
			}
			else
				bullet.originShip.onHitShip(bullet);
			bullet.hit();
		}
	}

	public void update()
	{
		float time = clock.getTime() / 1000.f;
		clock.reset();

		if (state == ALIVE)
		{
			// Move Ship
			if (getX() != moveTarget.x || getY() != moveTarget.y)
			{

				Vector2 moveOffset = new Vector2(moveTarget.x - getX(), moveTarget.y - getY());

				if (speed * time >= moveTarget.dst(getX(), getY()))
					setPosition(moveTarget);
				else
				{
					float angle = (float) (moveOffset.angle() * Math.PI / 180.f);
					setPosition(new Vector2((float) (getX() + Math.cos(angle) * speed * time), (float) (getY() + Math.sin(angle) * speed * time)));
					if (directionAligned)
						sprite.setRotation(angle * 180.f / (float) Math.PI);
				}
			}

			// Update weapons
			for (Weapon weapon : weapons)
				weapon.update();

			collisionPoint.x = getX();
			collisionPoint.y = getY();
		}
	}

	public void setFiring(boolean firing)
	{
		for (Weapon weapon : weapons)
			weapon.setFiring(firing);
	}

	public void onHitShip(Bullet bullet)
	{
	}

	public void onDestroyShip(Bullet bullet)
	{
	}

	public void destroy()
	{
		state = DESTROYED;
	}

	public void castEffect()
	{
	}

	float getDirection()
	{
		return sprite.getRotation() / 180 * (float) Math.PI;
	}

	public void addHealth(float healthOffset)
	{
		health = Math.min(health + healthOffset, maxHealth);
		if (health <= 0)
			state = DESTROYING;
	}

	public void setTimeFactor(float factor)
	{
		clock.setFactor(factor);
		for (Weapon weapon : weapons)
		{
			weapon.setTimeFactor(factor);
		}
	}
}