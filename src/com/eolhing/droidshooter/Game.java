package com.eolhing.droidshooter;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class Game implements ApplicationListener
{
	static public class Data
	{
		int pilotType;
		String versionNumber = "0.3";
	}

	int width;
	int height;
	Data data;
	Scene scene;

	@Override public void create()
	{
		Gdx.input.setCatchBackKey(true);

		data = new Data();
		SettingsScene.readSettings();
		// scene isn't initialized here but during the first resize
	}

	@Override public void dispose()
	{
	}

	@Override public void pause()
	{
	}

	@Override public void render()
	{
		switch (scene.draw())
		{
		case Scene.MENU:
			scene = new MenuScene(width, height, data);
			break;
		case Scene.ARCADE:
			scene = new ArcadeScene(width, height, data);
			break;
		case Scene.PILOT:
			scene = new PilotScene(width, height, data);
			break;
		case Scene.STORY:
			break; // TODO(v2.0) Implements Scene
		case Scene.SETTINGS:
			scene = new SettingsScene(width, height, data);
			break;
		case Scene.QUIT:
			Gdx.app.exit();
			break;
		default:
			break;
		}
	}

	@Override public void resize(int width, int height)
	{
		this.width = width;
		this.height = height;

		if (scene == null) // First resize
			scene = new MenuScene(this.width, this.height, data);
		else
			scene.resize(this.width, this.height);
	}

	@Override public void resume()
	{
	}
}
