package com.eolhing.droidshooter.GameEffects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.eolhing.droidshooter.ArcadeScene;
import com.eolhing.droidshooter.GameEntities.Effect;
import com.eolhing.droidshooter.GameEntities.PlayerShip;
import com.eolhing.droidshooter.Tools.TextureManager;

public class NyanCatEffect extends Effect
{
	long duration;
	long lastFrameTime;
	Animation animation;
	PlayerShip ship;
	Vector2 catPosition;
	Vector2 berserkCatPosition;
	float berserkStartTime;
	public ArcadeScene scene;

	public NyanCatEffect(ArcadeScene scene, PlayerShip ship)
	{
		super();
		duration = 5000;
		lastFrameTime = 0;
		this.scene = scene;

		// Hide the ship
		this.ship = ship;
		ship.autoPilot = true;
		Vector2 shipPosition = ship.getPosition();
		shipPosition.y = -ship.getRect().height;
		ship.moveTarget = shipPosition;

		berserkCatPosition = new Vector2();
		berserkStartTime = 0.f;

		animation = new Animation(0.07f,
		        TextureManager.nyanCatTexture1,
		        TextureManager.nyanCatTexture2,
		        TextureManager.nyanCatTexture3,
		        TextureManager.nyanCatTexture4,
		        TextureManager.nyanCatTexture5,
		        TextureManager.nyanCatTexture6,
		        TextureManager.nyanCatTexture7,
		        TextureManager.nyanCatTexture8,
		        TextureManager.nyanCatTexture9,
		        TextureManager.nyanCatTexture10,
		        TextureManager.nyanCatTexture11,
		        TextureManager.nyanCatTexture12);

		catPosition = new Vector2(240.f, -TextureManager.nyanCatTexture1.getRegionHeight());

		clock.reset();
	}

	@Override public void update()
	{
		super.update();

		float delta = (clock.getTime() - lastFrameTime) / 1000.f;
		long time = clock.getTime();
		lastFrameTime = time;

		if (time <= 500) // First phase : nyan cat going up
		{
			catPosition.y += delta * 300.f;
			berserkCatPosition.set(catPosition);
			berserkStartTime = time;
		}
		else if (time <= duration - 500) // Second phase : berserk
		{
			catPosition.x = berserkCatPosition.x + (float) Math.sin((time - berserkStartTime) / 1000.f * 3.f) * 200.f;
			catPosition.y = berserkCatPosition.y + (float) Math.sin((time - berserkStartTime) / 1000.f * 8.f) * 25.f;
		}
		else if (time <= duration) // Third phase : nyan cat going down
		{
			catPosition.y -= delta * 300.f;
		}
		else if (time > duration) // end
		{
			state = FINISHED;
			ship.autoPilot = false;
			Vector2 shipPosition = ship.getPosition();
			shipPosition.y = 100.f;
			ship.moveTarget = shipPosition;
		}
	}

	@Override public void draw(SpriteBatch batch)
	{
		super.draw(batch);
		batch.draw(animation.getKeyFrame(clock.getTime() / 1000.f, true), catPosition.x, catPosition.y);
	}
}
