package com.eolhing.droidshooter.GameEntities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eolhing.droidshooter.Tools.Clock;

public class Effect
{
	// Effect's state
	public static final int RUNNING = 0;
	public static final int FINISHED = 1;

	// Effect's Type
	public static final int NONE = 0;
	public static final int HEAL = 1;
	public static final int BOMB = 3;
	public static final int SHIELD = 2;
	public static final int EXTRALIVES = 5;
	public static final int LARAPOWER1 = 6;
	public static final int SLOWTEMPO = 7;
	public static final int LARAPOWER3 = 8;
	public static final int NYANCAT = 9;
	public static final int BUG = 10;
	public static final int HACK = 11;
	public static final int BABYKORBEN = 12;
	public static final int ELEET = 13;
	public static final int TANGO = 14;
	public static final int QUICKSTEP = 15;
	public static final int FOXTROT = 16;
	public static final int WALTZ = 17;

	public int state;
	protected Clock clock;

	public Effect()
	{
		state = RUNNING;
		clock = new Clock();
	}

	public void update()
	{
	}

	public void draw(SpriteBatch batch)
	{
	}

	public void setTimeFactor(float factor)
	{
		clock.setFactor(factor);
	}
}
