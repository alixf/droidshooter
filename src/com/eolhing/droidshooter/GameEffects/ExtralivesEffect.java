package com.eolhing.droidshooter.GameEffects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eolhing.droidshooter.GameEntities.Effect;
import com.eolhing.droidshooter.GameEntities.Ship;

public class ExtralivesEffect extends Effect
{
	Ship ship;
	int lifeCounter;
	long duration;

	public ExtralivesEffect(Ship ship)
	{ // TODO implement Effect
		super();

		this.ship = ship;
		lifeCounter = 0;
		duration = 5000;
	}

	@Override public void update()
	{
		if (clock.getTime() > duration)
		{
			state = FINISHED;
			if (ship.health < ship.maxHealth / 3.f)
				ship.health = ship.maxHealth / 3.f;
		}
		if (ship.state == Ship.DESTROYED)
		{
			ship.state = Ship.ALIVE;
			ship.health = ship.maxHealth / 3.f;
		}
	}

	@Override public void draw(SpriteBatch batch)
	{

	}
}
