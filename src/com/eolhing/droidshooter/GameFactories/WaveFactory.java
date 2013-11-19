package com.eolhing.droidshooter.GameFactories;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;
import com.eolhing.droidshooter.GameEntities.Enemy;

public class WaveFactory
{
	public static LinkedList<Enemy.Data> getWave(int level, int width,
	        int height)
	{
		LinkedList<Enemy.Data> wave = new LinkedList<Enemy.Data>();
		float midWidth = (float) width / 2.f;

		switch ((level - 1) % 14)
		{
		case 0:
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(midWidth - 40.f, height), TrajectoryFactory.STRAIGHTDOWN));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(midWidth, height + 15.f), TrajectoryFactory.STRAIGHTDOWN));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(midWidth + 40.f, height), TrajectoryFactory.STRAIGHTDOWN));
			break;
		case 1:
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(midWidth - 80.f, height + 30.f), TrajectoryFactory.STRAIGHTDOWN));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(midWidth - 40.f, height + 15.f), TrajectoryFactory.STRAIGHTDOWN));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(midWidth, height), TrajectoryFactory.STRAIGHTDOWN));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(midWidth + 40.f, height + 15.f), TrajectoryFactory.STRAIGHTDOWN));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(midWidth + 80.f, height + 30.f), TrajectoryFactory.STRAIGHTDOWN));
			break;
		case 2:
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(-20.f, height - 50.f), TrajectoryFactory.STRAIGHTDOWNRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(width + 20.f, height - 50.f), TrajectoryFactory.STRAIGHTDOWNLEFT));
			break;
		case 3:
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(-20.f, height - 50.f), TrajectoryFactory.STRAIGHTDOWNRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(width + 20.f, height - 50.f), TrajectoryFactory.STRAIGHTDOWNLEFT));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(-20.f, height - 100.f), TrajectoryFactory.STRAIGHTDOWNRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(width + 20.f, height - 100.f), TrajectoryFactory.STRAIGHTDOWNLEFT));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(-20.f, height - 150.f), TrajectoryFactory.STRAIGHTDOWNRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(width + 20.f, height - 150.f), TrajectoryFactory.STRAIGHTDOWNLEFT));
			break;
		case 4:
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(-20.f, height - 200.f), TrajectoryFactory.STRAIGHTRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(width + 20.f, height - 200.f), TrajectoryFactory.STRAIGHTLEFT));
			break;
		case 5:
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(-120.f, height - 200.f), TrajectoryFactory.STRAIGHTRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(width + 120.f, height - 200.f), TrajectoryFactory.STRAIGHTLEFT));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(-70.f, height - 150.f), TrajectoryFactory.STRAIGHTRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(width + 70.f, height - 150.f), TrajectoryFactory.STRAIGHTLEFT));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(-20.f, height - 100.f), TrajectoryFactory.STRAIGHTRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.DROID, new Vector2(width + 20.f, height - 100.f), TrajectoryFactory.STRAIGHTLEFT));
			break;
		case 6:
			wave.add(new Enemy.Data(EnemyFactory.BOSS, new Vector2(width / 2,
			        height), TrajectoryFactory.GOTOTOP));
			break;
		case 7:
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(midWidth - 40.f, height), TrajectoryFactory.STRAIGHTDOWN));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(midWidth, height + 15.f), TrajectoryFactory.STRAIGHTDOWN));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(midWidth + 40.f, height), TrajectoryFactory.STRAIGHTDOWN));
			break;
		case 8:
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(midWidth - 80.f, height + 30.f), TrajectoryFactory.STRAIGHTDOWN));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(midWidth - 40.f, height + 15.f), TrajectoryFactory.STRAIGHTDOWN));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(midWidth, height), TrajectoryFactory.STRAIGHTDOWN));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(midWidth + 40.f, height + 15.f), TrajectoryFactory.STRAIGHTDOWN));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(midWidth + 80.f, height + 30.f), TrajectoryFactory.STRAIGHTDOWN));
			break;
		case 9:
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(-20.f, height - 50.f), TrajectoryFactory.STRAIGHTDOWNRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(width + 20.f, height - 50.f), TrajectoryFactory.STRAIGHTDOWNLEFT));
			break;
		case 10:
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(-20.f, height - 50.f), TrajectoryFactory.STRAIGHTDOWNRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(width + 20.f, height - 50.f), TrajectoryFactory.STRAIGHTDOWNLEFT));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(-20.f, height - 100.f), TrajectoryFactory.STRAIGHTDOWNRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(width + 20.f, height - 100.f), TrajectoryFactory.STRAIGHTDOWNLEFT));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(-20.f, height - 150.f), TrajectoryFactory.STRAIGHTDOWNRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(width + 20.f, height - 150.f), TrajectoryFactory.STRAIGHTDOWNLEFT));
			break;
		case 11:
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(-20.f, height - 200.f), TrajectoryFactory.STRAIGHTRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(width + 20.f, height - 200.f), TrajectoryFactory.STRAIGHTLEFT));
			break;
		case 12:
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(-120.f, height - 200.f), TrajectoryFactory.STRAIGHTRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(width + 120.f, height - 200.f), TrajectoryFactory.STRAIGHTLEFT));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(-70.f, height - 150.f), TrajectoryFactory.STRAIGHTRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(width + 70.f, height - 150.f), TrajectoryFactory.STRAIGHTLEFT));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(-20.f, height - 100.f), TrajectoryFactory.STRAIGHTRIGHT));
			wave.add(new Enemy.Data(EnemyFactory.FASTDROID, new Vector2(width + 20.f, height - 100.f), TrajectoryFactory.STRAIGHTLEFT));
			break;
		case 13:
			wave.add(new Enemy.Data(EnemyFactory.SNAKEHEAD, new Vector2(-20.f, height - 100.f), TrajectoryFactory.SNAKE, true));
			wave.add(new Enemy.Data(EnemyFactory.SNAKEBODY, new Vector2(-42.f, height - 100.f), TrajectoryFactory.SNAKE, true));
			wave.add(new Enemy.Data(EnemyFactory.SNAKEBODY, new Vector2(-62.f, height - 100.f), TrajectoryFactory.SNAKE, true));
			wave.add(new Enemy.Data(EnemyFactory.SNAKEBODY, new Vector2(-82.f, height - 100.f), TrajectoryFactory.SNAKE, true));
			wave.add(new Enemy.Data(EnemyFactory.SNAKEBODY, new Vector2(-102.f, height - 100.f), TrajectoryFactory.SNAKE, true));
			wave.add(new Enemy.Data(EnemyFactory.SNAKEBODY, new Vector2(-122.f, height - 100.f), TrajectoryFactory.SNAKE, true));
			wave.add(new Enemy.Data(EnemyFactory.SNAKEBODY, new Vector2(-142.f, height - 100.f), TrajectoryFactory.SNAKE, true));
			wave.add(new Enemy.Data(EnemyFactory.SNAKEBODY, new Vector2(-162.f, height - 100.f), TrajectoryFactory.SNAKE, true));
			wave.add(new Enemy.Data(EnemyFactory.SNAKETAIL, new Vector2(-182.f, height - 100.f), TrajectoryFactory.SNAKE, true));
			break;
		}

		return wave;
	}
}
