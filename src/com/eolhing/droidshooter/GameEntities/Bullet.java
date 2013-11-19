package com.eolhing.droidshooter.GameEntities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eolhing.droidshooter.GamePhysics.Collidable;
import com.eolhing.droidshooter.Tools.AnimatedValue;
import com.eolhing.droidshooter.Tools.Clock;

public class Bullet extends Collidable
{
	public boolean isAlive;

	// Bullet parameters
	public AnimatedValue direction;
	public AnimatedValue speed;
	public AnimatedValue power;
	public AnimatedValue spin;
	public AnimatedValue rotationSpeed;
	public long lifeTime;

	// Collide
	public boolean canCollide;
	public long timeBetweenHits;
	public int hitsLeft;

	// IA
	public Ship originShip;
	public Ship targetShip;
	public Weapon originWeapon;

	// Drawable
	private Sprite mSprite;
	private Vector2 mOrigin;

	// Ray Stuff
	public boolean syncWithWeapon;

	// Clocks
	private Clock clock;
	private Clock lifeClock;
	private Clock collideClock;

	public Bullet(Ship originShip, Ship targetShip, float x, float y, float direction, float speed, float power)
	{
		super();
		isAlive = true;

		// Bullet parameters
		this.direction = new AnimatedValue(direction);
		this.speed = new AnimatedValue(speed);
		this.power = new AnimatedValue(power);
		spin = new AnimatedValue(0.f);
		rotationSpeed = new AnimatedValue(0.f);
		lifeTime = 0;

		// Collide
		canCollide = true;
		timeBetweenHits = 0;
		hitsLeft = 1;

		// IA
		this.originShip = originShip;
		this.targetShip = targetShip;

		// Drawable
		mSprite = new Sprite();
		mOrigin = new Vector2();
		mSprite.setPosition(x, y);

		// Ray Stuff
		syncWithWeapon = false;

		// Clocks
		clock = new Clock();
		lifeClock = new Clock();
		collideClock = new Clock();
	}

	public float getX()
	{
		return mSprite.getX() + mOrigin.x;
	}

	public float getY()
	{
		return mSprite.getY() + mOrigin.y;
	}

	public Vector2 getPosition()
	{
		return new Vector2(mSprite.getX() + mOrigin.x, mSprite.getY()
		        + mOrigin.y);
	}

	public void setPosition(Vector2 position)
	{
		mSprite.setPosition(position.x - mOrigin.x, position.y - mOrigin.y);
	}

	public void setPosition(float x, float y)
	{
		mSprite.setPosition(x - mOrigin.x, y - mOrigin.y);
	}

	public void setTextureRegion(TextureRegion textureRegion)
	{
		mSprite.setRegion(textureRegion);
		mOrigin.set(textureRegion.getRegionWidth() / 2, textureRegion.getRegionHeight() / 2);
		mSprite.setBounds(mSprite.getX() - mOrigin.x, mSprite.getY() - mOrigin.y, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
		mSprite.setOrigin(mSprite.getRegionWidth() / 2, mSprite.getRegionHeight() / 2);
	}

	public Rectangle getRect()
	{
		return mSprite.getBoundingRectangle();
	}

	public void update()
	{
		float deltaTime = (float) (clock.getTime()) / 1000.f;
		clock.reset();

		// Get values from animated values
		float currentDirection = direction.getValue();
		float currentSpeed = speed.getValue();
		float speedTime = currentSpeed * deltaTime;
		float currentSpin = spin.getValue();

		// Move bullet
		float finalDirection = 0.f;
		if (targetShip != null && targetShip.state == Ship.ALIVE) // Move
				                                                  // following
				                                                  // target
		{
			// TODO rewrite code, bullets currently only rotate clockwise, they
			// should take the smaller rotation path possible
			float targetDirection = (float) Math.atan2(targetShip.getY() - getY(), targetShip.getX() - getX());
			float currentRotationSpeed = rotationSpeed.getValue();

			if (Math.abs(targetDirection - currentDirection) <= currentRotationSpeed * deltaTime)
				finalDirection = targetDirection;
			else
			{
				finalDirection = currentDirection + (currentRotationSpeed * deltaTime * (targetDirection > currentDirection ? 1.f : -1.f));
				direction = new AnimatedValue(finalDirection);
				currentDirection = finalDirection;
			}
		}
		else
			// Move following direction
			finalDirection = syncWithWeapon ? originWeapon.directionOffset.getValue() + originShip.getDirection() : currentDirection;

		// Set bullet's position
		if (!syncWithWeapon)
			setPosition(getX() + (float) Math.cos(finalDirection) * speedTime, getY() + (float) Math.sin(finalDirection) * speedTime);
		else
			setPosition(originShip.getX(), originShip.getY());

		// Set bullet's rotation
		mSprite.setRotation((float) ((finalDirection + currentSpin) * 180.f / Math.PI));

		// Update collision checkers
		collisionSphere.x = getX();
		collisionSphere.y = getY();
		collisionRay.x = getX();
		collisionRay.y = getY();
		collisionRay.angle = finalDirection;

		// update alive and collision states
		if (!canCollide && collideClock.getTime() >= timeBetweenHits)
			canCollide = true;
		if ((lifeTime > 0 && lifeClock.getTime() > lifeTime) || (syncWithWeapon && !originWeapon.firing))
			isAlive = false;
	}

	public void hit()
	{
		if (hitsLeft == 1)
			isAlive = false;
		else if (hitsLeft > 1)
			hitsLeft--;
		canCollide = false;
		collideClock.reset();
	}

	public void draw(SpriteBatch batch)
	{
		mSprite.draw(batch);
	}

	public void setTimeFactor(float factor)
	{
		clock.setFactor(factor);
		lifeClock.setFactor(factor);
		collideClock.setFactor(factor);
		direction.setTimeFactor(factor);
		speed.setTimeFactor(factor);
		power.setTimeFactor(factor);
		spin.setTimeFactor(factor);
		rotationSpeed.setTimeFactor(factor);
	}

	public void scaleX(float scale)
	{
		mSprite.setScale(scale, mSprite.getScaleY());
	}
}