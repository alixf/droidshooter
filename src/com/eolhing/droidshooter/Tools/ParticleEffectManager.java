package com.eolhing.droidshooter.Tools;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ParticleEffectManager
{
	private static final ParticleEffectManager sInstance = new ParticleEffectManager();

	Hashtable<String, ParticleEffect> mParticleEffects;

	public static ParticleEffectManager getInstance()
	{
		return sInstance;
	}

	private ParticleEffectManager()
	{
		mParticleEffects = new Hashtable<String, ParticleEffect>();

		ParticleEffect particles = new ParticleEffect();
		particles.load(Gdx.files.internal("res/particles/explosion.particles"), Gdx.files.internal("res/drawable"));
		particles.getEmitters().get(0).setSprite(new Sprite(TextureManager.circleParticle));
		mParticleEffects.put("explosion.particles", particles);
		/*
		 * particles = new ParticleEffect();
		 * particles.load(Gdx.files.internal("res/particles/heal.particles"),
		 * Gdx.files.internal("res/drawable"));
		 * particles.getEmitters().get(0).setSprite(new
		 * Sprite(TextureManager.plusParticleTexture));
		 * particles.getEmitters().get(0).setSprite(new
		 * Sprite(TextureManager.circleParticleTexture));
		 * mParticleEffects.put("heal.particles", particles);
		 */
	}

	public ParticleEffect getParticleEffect(String filePath)
	{
		if (!mParticleEffects.containsKey(filePath))
		{
			ParticleEffect particleEffect = new ParticleEffect();
			particleEffect.load(Gdx.files.internal("res/particles/" + filePath), Gdx.files.internal("res/drawable"));
			mParticleEffects.put(filePath, particleEffect);
			return new ParticleEffect(particleEffect);
		}

		return new ParticleEffect(mParticleEffects.get(filePath));
	}

	public void removeParticleEffect(String filePath)
	{
		if (mParticleEffects.containsKey(filePath))
			mParticleEffects.remove(filePath);
	}
}
