package Engine;

import Geometry.Shapes.Polygon;
import Vectors.Vector2f;

public class BoltMath
{

	public static float[] GetAngles(Polygon Poly)
	{

		Vector2f[] Corners = Poly.GetCorners();

		float[] Angles = new float[Corners.length];

		for (int i = 0; i < Corners.length; i++)
		{

			int PreviousCorner = mod((i - 1), Corners.length);
			int NextCorner = (i + 1) % Corners.length;

			Vector2f LastPoint = Corners[PreviousCorner];
			Vector2f CurrentPoint = Corners[i];
			Vector2f NextPoint = Corners[NextCorner];

			Angles[i] = GetAngle(LastPoint, CurrentPoint, NextPoint, true);

		}

		return Angles;
	}

	public static float GetAngle(Vector2f Point1, Vector2f Middle, Vector2f Point2, boolean Clockwise)
	{
		double aAngle = Math.toDegrees(theta(Middle, Point1));
		double cAngle = Math.toDegrees(theta(Middle, Point2));
		double degs;

		double firstAngle;
		double secondAngle;

		if (aAngle < cAngle)
		{
			firstAngle = aAngle;
			secondAngle = cAngle;
		}

		else
		{
			firstAngle = cAngle;
			secondAngle = aAngle;
		}

		if (secondAngle - firstAngle < 180)
		{
			degs = secondAngle - firstAngle;
		}

		else
		{
			degs = firstAngle + (360 - secondAngle);
		}

		if (!Clockwise)
		{
			degs = 360 - degs;
		}

		return (float) degs;
	}

	public static double theta(Vector2f a, Vector2f b)
	{
		double ang = Math.atan2(a.GetY() - b.GetY(), b.GetX() - a.GetX());
		if (ang < 0)
			ang += (Math.PI * 2);

		return ang;
	}

	public static int mod(int a, int b)
	{
		int Answer = a % b;

		if (Answer < 0)
		{
			Answer = b + Answer;
		}

		return Answer;
	}

	public static float mod(float a, float b)
	{
		float Answer = a % b;

		if (Answer < 0)
		{
			Answer = b + Answer;
		}

		return Answer;
	}
}
