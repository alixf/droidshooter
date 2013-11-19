package com.eolhing.droidshooter.GameEntities;

import java.util.LinkedList;
import java.util.Queue;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.eolhing.droidshooter.Tools.Clock;
import com.eolhing.droidshooter.Tools.ParticleEffectManager;

public class Enemy extends Ship
{
	public static class Data
	{
		public Data(int type, Vector2 position, int trajectory)
		{
			this.type = type;
			this.position = position;
			this.trajectory = trajectory;
			this.directionAligned = false;
		}

		public Data(int type, Vector2 position, int trajectory,
		        boolean directionAligned)
		{
			this.type = type;
			this.position = position;
			this.trajectory = trajectory;
			this.directionAligned = directionAligned;
		}

		public int type;
		public Vector2 position;
		public int trajectory;
		public boolean directionAligned;
	}

	public Queue<Vector2> mTrajectory;

	long mDestroyTime;
	Clock mDestroyClock;

	public Enemy(LinkedList<Bullet> bullets, Vector2 position)
	{
		super(bullets, position);

		// Set enemy on auto-fire
		speed = 0.f;
		sprite.rotate(-90);

		mTrajectory = new LinkedList<Vector2>();

		mDestroyClock = new Clock();
		mDestroyTime = 333;
	}

	public Queue<Vector2> getTrajectory()
	{
		return mTrajectory;
	}

	public void setTrajectory(Queue<Vector2> trajectory)
	{
		mTrajectory = trajectory;
	}

	@Override public void update()
	{
		if (getX() == moveTarget.x && getY() == moveTarget.y)
		{
			if (mTrajectory.size() > 0)
				moveTarget = mTrajectory.poll();
			else if (!(new Rectangle(0, 0, 480, 800).contains(getX(), getY())))
				// TODO : get screen size properly
				state = DESTROYED;
		}

		// If enemy is below the terrain limit, destroy it
		if (sprite.getY() < -50)
			state = DESTROYED;

		super.update();

		if (state == DESTROYING)
		{
			if (mDestroyClock.getTime() > mDestroyTime)
				state = DESTROYED;
			else
			{
				float x = (float) (mDestroyClock.getTime()) / mDestroyTime;
				sprite.setColor(1.f, 1.f, 1.f, 1.f - x);
				float scaleFactor = x <= 0.5f ? x * 2 + 1 : -4 * x + 4;
				sprite.setScale(scaleFactor);
			}
		}

		collisionSphere.x = getX();
		collisionSphere.y = getY();
	}

	@Override public void destroy()
	{
		state = DESTROYING;
		particleEffectContainer.addParticleEffect(ParticleEffectManager.getInstance().getParticleEffect("explosion.particles"), getX(), getY());
		mDestroyClock.reset();
	}
}