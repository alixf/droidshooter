package com.eolhing.droidshooter.GameEffects;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eolhing.droidshooter.GameEntities.Bullet;
import com.eolhing.droidshooter.GameEntities.Effect;
import com.eolhing.droidshooter.GameEntities.Ship;

public class ShieldEffect extends Effect
{
	long duration;
	Ship ship;
	LinkedList<Bullet> bullets;

	public ShieldEffect(Ship ship, LinkedList<Bullet> bullets)
	{
		super();
		duration = 4000;
		this.ship = ship;
		this.bullets = bullets;
		clock.reset();
	}

	@Override public void update()
	{
		super.update();

		if (clock.getTime() >= duration)
			state = FINISHED;
	}

	@Override public void draw(SpriteBatch batch)
	{
		super.draw(batch);
	}
}
