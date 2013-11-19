package com.eolhing.droidshooter.GameEffects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eolhing.droidshooter.GameEntities.Effect;
import com.eolhing.droidshooter.GameEntities.ParticlesContainer;
import com.eolhing.droidshooter.GameEntities.Ship;
import com.eolhing.droidshooter.Tools.ParticleEffectManager;

public class HealEffect extends Effect
{
	Ship ship;

	public HealEffect(Ship ship, ParticlesContainer particleContainer)
	{
		super();
		this.ship = ship;
		particleContainer.addParticleEffect(ParticleEffectManager.getInstance().getParticleEffect("heal.particles"), ship.getX(), ship.getY());
		clock.reset();
	}

	@Override public void update()
	{
		super.update();

		ship.addHealth(ship.maxHealth);
		state = FINISHED;
	}

	@Override public void draw(SpriteBatch batch)
	{
		super.draw(batch);
	}
}
