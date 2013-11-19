package com.eolhing.droidshooter.GamePhysics;

import com.badlogic.gdx.math.Rectangle;

public class Physics
{
	public static final int BBOX = 0;
	public static final int BSPHERE = 1;
	public static final int POINT = 2;
	public static final int LINE = 3;
	public static final int RAY = 4;

	public static class Point
	{
		public float x;
		public float y;

		public Point(float x, float y)
		{
			this.x = x;
			this.y = y;
		}
	}

	public static class Sphere
	{
		public float x;
		public float y;
		public float radius;

		public Sphere(float x, float y, float radius)
		{
			this.x = x;
			this.y = y;
			this.radius = radius;
		}
	}

	public static class Line
	{
		public float x;
		public float y;
		public float angle;
		public float thickness;

		public Line(float x, float y, float angle, float thickness)
		{
			this.x = x;
			this.y = y;
			this.angle = angle;
			this.thickness = thickness;
		}
	}

	public static class Ray
	{
		public float x;
		public float y;
		public float angle;
		public float thickness;

		public Ray(float x, float y, float angle, float thickness)
		{
			this.x = x;
			this.y = y;
			this.angle = angle;
			this.thickness = thickness;
		}
	}

	public static boolean collide(Collidable a, Collidable b)
	{
		switch (a.collisionType)
		{
		case BBOX:
			switch (b.collisionType)
			{
			case BBOX:
				return boxBoxCollision(a.GetBBox(), b.GetBBox());
			case BSPHERE:
				return boxSphereCollision(a.GetBBox(), b.GetSphere());
			case POINT:
				return boxPointCollision(a.GetBBox(), b.GetPoint());
			case LINE:
				return boxLineCollision(a.GetBBox(), b.GetLine());
			case RAY:
				return boxRayCollision(a.GetBBox(), b.GetRay());
			default:
				break;
			}
			break;
		case BSPHERE:
			switch (b.collisionType)
			{
			case BBOX:
				return boxSphereCollision(b.GetBBox(), a.GetSphere());
			case BSPHERE:
				return sphereSphereCollision(a.GetSphere(), b.GetSphere());
			case POINT:
				return spherePointCollision(a.GetSphere(), b.GetPoint());
			case LINE:
				return sphereLineCollision(a.GetSphere(), b.GetLine());
			case RAY:
				return sphereRayCollision(a.GetSphere(), b.GetRay());
			default:
				break;
			}
			break;
		case POINT:
			switch (b.collisionType)
			{
			case BBOX:
				return boxPointCollision(b.GetBBox(), a.GetPoint());
			case BSPHERE:
				return spherePointCollision(b.GetSphere(), a.GetPoint());
			case POINT:
				return pointPointCollision(a.GetPoint(), b.GetPoint());
			case LINE:
				return pointLineCollision(a.GetPoint(), b.GetLine());
			case RAY:
				return pointRayCollision(a.GetPoint(), b.GetRay());
			default:
				break;
			}
			break;
		case LINE:
			switch (b.collisionType)
			{
			case BBOX:
				return boxLineCollision(b.GetBBox(), a.GetLine());
			case BSPHERE:
				return sphereLineCollision(b.GetSphere(), a.GetLine());
			case POINT:
				return pointLineCollision(b.GetPoint(), a.GetLine());
			case LINE:
				return lineLineCollision(a.GetLine(), b.GetLine());
			case RAY:
				return lineRayCollision(a.GetLine(), b.GetRay());
			default:
				break;
			}
			break;
		case RAY:
			switch (b.collisionType)
			{
			case BBOX:
				return boxRayCollision(b.GetBBox(), a.GetRay());
			case BSPHERE:
				return sphereRayCollision(b.GetSphere(), a.GetRay());
			case POINT:
				return pointRayCollision(b.GetPoint(), a.GetRay());
			case LINE:
				return lineRayCollision(b.GetLine(), a.GetRay());
			case RAY:
				return rayRayCollision(a.GetRay(), b.GetRay());
			default:
				break;
			}
			break;
		default:
			break;
		}

		return false;
	}

	// Generic math functions
	public static float linePointDistance(float x, float y, float r, float x0,
	        float y0)
	{
		final double a = Math.sin(r);
		final double b = -Math.cos(r);
		final double c = y * Math.cos(r) - x * Math.sin(r);
		return (float) (Math.abs(a * x0 + b * y0 + c) / Math
		        .sqrt(a * a + b * b));
	}

	public static float pointPointDistance(float x0, float y0, float x1,
	        float y1)
	{
		return (float) Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
	}

	// Collisions functions
	public static boolean boxBoxCollision(Rectangle a, Rectangle b)
	{
		return a.overlaps(b);
	}

	public static boolean boxSphereCollision(Rectangle a, Sphere b)
	{
		return false; // TODO Implements box-sphere collision
	}

	public static boolean boxPointCollision(Rectangle a, Point b)
	{
		return a.contains(b.x, b.y);
	}

	public static boolean boxLineCollision(Rectangle a, Line b)
	{
		return false; // TODO Implements box-line collision
	}

	public static boolean boxRayCollision(Rectangle a, Ray b)
	{
		// TODO ray correction
		return false; // TODO Implements box-ray collision
	}

	public static boolean sphereSphereCollision(Sphere a, Sphere b)
	{
		return pointPointDistance(a.x, a.y, b.x, b.y) < a.radius + b.radius;
	}

	public static boolean spherePointCollision(Sphere a, Point b)
	{
		return pointPointDistance(a.x, a.y, b.x, b.y) <= a.radius;
	}

	public static boolean sphereLineCollision(Sphere a, Line b)
	{
		return linePointDistance(b.x, b.y, b.angle, a.x, a.y) <= b.thickness
		        + a.radius;
	}

	public static boolean sphereRayCollision(Sphere a, Ray b)
	{
		// TODO ray correction
		return linePointDistance(b.x, b.y, b.angle, a.x, a.y) <= b.thickness
		        + a.radius;
	}

	public static boolean pointPointCollision(Point a, Point b)
	{
		return a.x == b.x && a.y == b.y;
	}

	public static boolean pointLineCollision(Point a, Line b)
	{
		return linePointDistance(b.x, b.y, b.angle, a.x, a.y) <= b.thickness;
	}

	public static boolean pointRayCollision(Point a, Ray b)
	{
		// TODO ray correction
		return linePointDistance(b.x, b.y, b.angle, a.x, a.y) <= b.thickness;
	}

	public static boolean lineLineCollision(Line a, Line b)
	{
		return a.angle % Math.PI != b.angle % Math.PI
		        || (a.x != b.x || a.y != b.y);
	}

	public static boolean lineRayCollision(Line a, Ray b)
	{
		// TODO ray correction
		return a.angle % Math.PI != b.angle % Math.PI
		        || (a.x != b.x || a.y != b.y);
	}

	public static boolean rayRayCollision(Ray a, Ray b)
	{
		// TODO ray correction
		return a.angle % Math.PI != b.angle % Math.PI
		        || (a.x != b.x || a.y != b.y);
	}
}
