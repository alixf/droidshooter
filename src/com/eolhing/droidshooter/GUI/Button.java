package com.eolhing.droidshooter.GUI;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Button
{
	public Sprite text;
	public NinePatch onGlass;
	public NinePatch offGlass;
	public NinePatch background;
	public Rectangle rectangle;
	public boolean switchBehavior;
	public boolean switchValue;
	public boolean drawBackground;

	public Button(boolean status, Sprite text, NinePatch background, NinePatch onGlass, NinePatch offGlass, Rectangle rectangle)
	{
		this.text = text;
		this.onGlass = onGlass;
		this.offGlass = offGlass;
		this.background = background;
		this.rectangle = rectangle;
		this.switchValue = status;
		switchBehavior = true;
		drawBackground = true;

		text.setPosition((float) Math.ceil(rectangle.x + (rectangle.width - text.getRegionWidth()) / 2), (float) Math.ceil(rectangle.y + (rectangle.height - text.getRegionHeight()) / 2));
	}

	public Button(Sprite text, NinePatch background, NinePatch onGlass, NinePatch offGlass, Rectangle rectangle)
	{
		this.text = text;
		this.onGlass = onGlass;
		this.offGlass = offGlass;
		this.background = background;
		this.rectangle = rectangle;
		switchBehavior = false;
		switchValue = false;

		text.setPosition((float) Math.ceil(rectangle.x + (rectangle.width - text.getRegionWidth()) / 2), (float) Math.ceil(rectangle.y + (rectangle.height - text.getRegionHeight()) / 2));
	}

	public float getX()
	{
		return rectangle.x;
	}

	public float getY()
	{
		return rectangle.y;
	}

	public void setPosition(float x, float y)
	{
		rectangle.x = x;
		rectangle.y = y;
		text.setPosition((float) Math.ceil(rectangle.x + (rectangle.width - text.getRegionWidth()) / 2), (float) Math.ceil(rectangle.y + (rectangle.height - text.getRegionHeight()) / 2));
	}

	public boolean touchDown(int x, int y)
	{
		if (rectangle.contains(x, y))
		{
			switchValue = !switchValue;
			return true;
		}
		return false;
	}

	public void draw(SpriteBatch batch)
	{
		if (drawBackground)
		{
			background.draw(batch, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
			if (!switchBehavior || switchValue)
				onGlass.draw(batch, rectangle.x + 14, rectangle.y + 12, rectangle.width - 2 * 14, rectangle.height - 2 * 12);
			else
				offGlass.draw(batch, rectangle.x + 14, rectangle.y + 12, rectangle.width - 2 * 14, rectangle.height - 2 * 12);
		}
		text.draw(batch);
	}
}