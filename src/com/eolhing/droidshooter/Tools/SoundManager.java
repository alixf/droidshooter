package com.eolhing.droidshooter.Tools;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager
{
	private static final SoundManager sInstance = new SoundManager();

	Hashtable<String, Sound> mSounds;

	public static SoundManager getInstance()
	{
		return sInstance;
	}

	private SoundManager()
	{
		mSounds = new Hashtable<String, Sound>();
	}

	public Sound getSound(String filePath)
	{
		if (!mSounds.containsKey(filePath))
			mSounds.put(filePath, Gdx.audio.newSound(Gdx.files.internal("res/sound/" + filePath)));

		return mSounds.get(filePath);
	}

	public void removeSound(String filePath)
	{
		if (mSounds.containsKey(filePath))
			mSounds.remove(filePath);
	}
}