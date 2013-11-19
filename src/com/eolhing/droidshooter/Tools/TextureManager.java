package com.eolhing.droidshooter.Tools;

import java.util.Hashtable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureManager
{
	private static final TextureManager sInstance = new TextureManager();

	public static Texture sprites = new Texture(Gdx.files.internal("res/drawable/sprites.png"));

	public static final TextureRegion ship = new TextureRegion(sprites, 0, 0, 39, 28);
	public static final TextureRegion crossParticle = new TextureRegion(sprites, 59, 0, 9, 9);
	public static final TextureRegion healthFill = new TextureRegion(sprites, 68, 0, 14, 1);
	public static final TextureRegion ninePatch = new TextureRegion(sprites, 82, 0, 16, 16);
	public static final TextureRegion healthGauge = new TextureRegion(sprites, 98, 0, 74, 304);
	public static final TextureRegion powerGauge = new TextureRegion(sprites, 172, 0, 70, 304);
	public static final TextureRegion bombEffect = new TextureRegion(sprites, 242, 0, 128, 128);
	public static final TextureRegion powerFill = new TextureRegion(sprites, 68, 1, 14, 1);
	public static final TextureRegion rainbowRay = new TextureRegion(sprites, 59, 9, 1, 10);
	public static final TextureRegion ray = new TextureRegion(sprites, 60, 9, 1, 7);
	public static final TextureRegion blueBullet = new TextureRegion(sprites, 39, 0, 20, 15);
	public static final TextureRegion redBullet = new TextureRegion(sprites, 39, 15, 20, 15);
	public static final TextureRegion greenBullet = new TextureRegion(sprites, 39, 30, 20, 15);
	public static final TextureRegion ninepatch = new TextureRegion(sprites, 82, 16, 16, 16);
	public static final TextureRegion droid = new TextureRegion(sprites, 0, 28, 23, 17);
	public static final TextureRegion circleParticle = new TextureRegion(sprites, 23, 28, 16, 17);

	public static final TextureRegion nyanCatTexture1 = new TextureRegion(sprites, 0, 416, 38, 96);
	public static final TextureRegion nyanCatTexture2 = new TextureRegion(sprites, 38, 412, 38, 100);
	public static final TextureRegion nyanCatTexture3 = new TextureRegion(sprites, 76, 412, 40, 100);
	public static final TextureRegion nyanCatTexture4 = new TextureRegion(sprites, 116, 412, 40, 100);
	public static final TextureRegion nyanCatTexture5 = new TextureRegion(sprites, 156, 412, 41, 100);
	public static final TextureRegion nyanCatTexture6 = new TextureRegion(sprites, 197, 412, 40, 100);
	public static final TextureRegion nyanCatTexture7 = new TextureRegion(sprites, 237, 412, 39, 100);
	public static final TextureRegion nyanCatTexture8 = new TextureRegion(sprites, 276, 412, 38, 100);
	public static final TextureRegion nyanCatTexture9 = new TextureRegion(sprites, 314, 412, 40, 100);
	public static final TextureRegion nyanCatTexture10 = new TextureRegion(sprites, 354, 412, 40, 100);
	public static final TextureRegion nyanCatTexture11 = new TextureRegion(sprites, 394, 412, 41, 100);
	public static final TextureRegion nyanCatTexture12 = new TextureRegion(sprites, 435, 412, 40, 100);

	public static final TextureRegion snakeHead = new TextureRegion(sprites, 42, 45, 26, 26);
	public static final TextureRegion snakeBody = new TextureRegion(sprites, 22, 45, 20, 26);
	public static final TextureRegion snakeTail = new TextureRegion(sprites, 0, 45, 22, 26);

	public static final TextureRegion blackBox = new TextureRegion(sprites, 68, 2, 14, 14);

	Hashtable<String, Texture> mTextures;

	public static TextureManager getInstance()
	{
		return sInstance;
	}

	private TextureManager()
	{
		mTextures = new Hashtable<String, Texture>();
	}

	public Texture getTexture(String filePath)
	{
		if (!mTextures.containsKey(filePath))
			mTextures.put(filePath, new Texture(Gdx.files.internal("res/drawable/" + filePath)));

		return mTextures.get(filePath);
	}

	public void removeTexture(String filePath)
	{
		if (mTextures.containsKey(filePath))
			mTextures.remove(filePath);
	}

	public void reloadAll()
	{
		sprites = new Texture(Gdx.files.internal("res/drawable/sprites.png"));
	}
}