package com.eolhing.droidshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class Scene extends InputAdapter
{
	// Scene Types
	public static final int NONE = 0;
	public static final int MENU = 1;
	public static final int ARCADE = 2;
	public static final int PILOT = 3;
	public static final int STORY = 4;
	public static final int SETTINGS = 5;
	public static final int CREDITS = 6;
	public static final int QUIT = 7;

	int width;
	int height;
	Game.Data gameData;

	public Scene(int width, int height, Game.Data gameData)
	{
		Gdx.input.setInputProcessor(this);
		this.width = width;
		this.height = height;
		this.gameData = gameData;
	}

	public void resize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	public int draw()
	{
		return NONE;
	}
}
