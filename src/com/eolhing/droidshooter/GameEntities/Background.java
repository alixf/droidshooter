package com.eolhing.droidshooter.GameEntities;

import java.util.LinkedList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eolhing.droidshooter.Tools.Clock;
import com.eolhing.droidshooter.Tools.TextureManager;

public class Background
{
	class Layer
	{
		public Sprite sprite;
		public float z;

		public Layer(TextureRegion region, float z)
		{
			sprite = new Sprite(region);
			sprite.getTexture().setWrap(TextureWrap.ClampToEdge, TextureWrap.Repeat);
			sprite.setOrigin(0.f, 0.f);
			this.z = z;
		}
	}

	float mWidth;
	float mHeight;

	Clock mClock;
	LinkedList<Layer> mLayers;

	float mScrollSpeed;
	float mScrollDirection;

	public Background()
	{
		mScrollSpeed = 100;
		mScrollDirection = (float) -Math.PI / 2;

		mLayers = new LinkedList<Layer>();
		mLayers.add(new Layer(new TextureRegion(TextureManager.getInstance().getTexture("backgrounds.png"), 0, 0, 240, 1024), 10000));
		mLayers.add(new Layer(new TextureRegion(TextureManager.getInstance().getTexture("backgrounds.png"), 240, 0, 240, 1024), 1000));

		mClock = new Clock();
	}

	public void update()
	{
		float time = mClock.getTime() / 1000.f;
		mClock.reset();

		for (Layer layer : mLayers)
			layer.sprite.scroll((float) (time * mScrollSpeed / layer.z * Math.cos(mScrollDirection)), (float) (time * mScrollSpeed / layer.z * Math.sin(mScrollDirection)));
	}

	public void draw(SpriteBatch batch)
	{
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		for (Layer layer : mLayers)
			layer.sprite.draw(batch);
	}

	public void setScreenSize(int width, int height)
	{
		mWidth = width;
		mHeight = height;

		for (Layer layer : mLayers)
			layer.sprite.setScale(mWidth / layer.sprite.getRegionWidth());
	}

	public void setTimeFactor(float factor)
	{
		mClock.setFactor(factor);
	}
}
