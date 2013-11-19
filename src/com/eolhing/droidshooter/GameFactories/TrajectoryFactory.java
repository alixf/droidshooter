package com.eolhing.droidshooter.GameFactories;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.math.Vector2;

public class TrajectoryFactory
{
	// Screen size
	private static float terrainWidth = 0.f;
	private static float terrainHeight = 0.f;

	// Trajectory Types
	public static final int STRAIGHTDOWN = 0;
	public static final int STRAIGHTUP = 1;
	public static final int STRAIGHTRIGHT = 2;
	public static final int STRAIGHTLEFT = 3;
	public static final int STRAIGHTDOWNRIGHT = 4;
	public static final int STRAIGHTDOWNLEFT = 5;
	public static final int STRAIGHTUPRIGHT = 6;
	public static final int STRAIGTHUPLEFT = 7;
	public static final int GOTOTOP = 8;
	public static final int SNAKE = 9;

	public static Queue<Vector2> getTrajectory(int type, Vector2 initialPosition)
	{
		Queue<Vector2> trajectory = new LinkedList<Vector2>();

		switch (type)
		{
		case STRAIGHTDOWN:
			trajectory.offer(new Vector2(initialPosition.x, -50));
			break;

		case STRAIGHTUP:
			trajectory
			        .offer(new Vector2(initialPosition.x, terrainHeight + 50));
			break;

		case STRAIGHTRIGHT:
			trajectory.offer(new Vector2(terrainWidth + 50, initialPosition.y));
			break;

		case STRAIGHTLEFT:
			trajectory.offer(new Vector2(-50, initialPosition.y));
			break;

		case STRAIGHTDOWNRIGHT:
		{
			float minDistanceToEdge = Math.min(terrainWidth - initialPosition.x, initialPosition.y);
			trajectory.offer(new Vector2(initialPosition.x + minDistanceToEdge + 50, initialPosition.y - minDistanceToEdge - 50));
		}
			break;

		case STRAIGHTDOWNLEFT:
		{
			float minDistanceToEdge = Math.min(initialPosition.x, initialPosition.y);
			trajectory.offer(new Vector2(initialPosition.x - minDistanceToEdge - 50, initialPosition.y - minDistanceToEdge - 50));
		}
			break;

		case STRAIGHTUPRIGHT:
		{
			float minDistanceToEdge = Math.min(terrainWidth - initialPosition.x, terrainHeight - initialPosition.y);
			trajectory.offer(new Vector2(initialPosition.x + minDistanceToEdge + 50, initialPosition.y + minDistanceToEdge + 50));
		}
			break;

		case STRAIGTHUPLEFT:
		{
			float minDistanceToEdge = Math.min(initialPosition.x, terrainHeight - initialPosition.y);
			trajectory.offer(new Vector2(initialPosition.x - minDistanceToEdge - 50, initialPosition.y + minDistanceToEdge + 50));
		}
			break;

		case GOTOTOP:
			trajectory.offer(new Vector2(initialPosition.x, initialPosition.y - 100));
			break;

		case SNAKE:
			for (float y = initialPosition.y - 50.f; y >= 0.f; y -= 200.f)
			{
				for (float a = 1.f; a >= 0.f; a -= 0.05f)
					trajectory.offer(new Vector2(370.f + (float) Math.cos(a * Math.PI - Math.PI / 2) * 50.f, y + (float) Math.sin(a * Math.PI - Math.PI / 2) * 50.f));
				for (float a = 1.f; a >= 0.f; a -= 0.05f)
					trajectory.offer(new Vector2(110.f - (float) Math.cos(a * Math.PI - Math.PI / 2) * 50.f, y + (float) Math.sin(a * Math.PI - Math.PI / 2) * 50.f - 100.f));
			}
			break;

		default:
			break;
		}

		return trajectory;
	}

	public static void setTerrainSize(int width, int height)
	{
		terrainWidth = width;
		terrainHeight = height;
	}
}
