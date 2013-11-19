package com.eolhing.droidshooter.GamePhysics;

import com.badlogic.gdx.math.Rectangle;
import com.eolhing.droidshooter.GamePhysics.Physics.Ray;
import com.eolhing.droidshooter.GamePhysics.Physics.Line;
import com.eolhing.droidshooter.GamePhysics.Physics.Point;
import com.eolhing.droidshooter.GamePhysics.Physics.Sphere;

public class Collidable
{
	public int collisionType;

	public Point collisionPoint;
	public Rectangle collisionRect;
	public Sphere collisionSphere;
	public Ray collisionRay;
	public Line collisionLine;

	public Collidable()
	{
		collisionType = Physics.POINT;
		collisionPoint = new Point(0.f, 0.f);
		collisionRect = new Rectangle(0.f, 0.f, 0.f, 0.f);
		collisionSphere = new Sphere(0.f, 0.f, 0.f);
		collisionRay = new Ray(0.f, 0.f, 0.f, 0.f);
		collisionLine = new Line(0.f, 0.f, 0.f, 0.f);
	}

	public Rectangle GetBBox()
	{
		return collisionRect;
	}

	public Sphere GetSphere()
	{
		return collisionSphere;
	}

	public Point GetPoint()
	{
		return collisionPoint;
	}

	public Ray GetRay()
	{
		return collisionRay;
	}

	public Line GetLine()
	{
		return collisionLine;
	}
}