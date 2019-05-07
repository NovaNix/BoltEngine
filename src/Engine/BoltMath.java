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

			int PreviousCorner;
			int NextCorner;

			if (i == 0)
			{
				PreviousCorner = Corners.length - 1;
				NextCorner = 1;
			}

			else if (i == Corners.length - 1)
			{
				PreviousCorner = i - 1;
				NextCorner = 0;
			}

			else
			{
				PreviousCorner = i - 1;
				NextCorner = i + 1;
			}

			Vector2f LastPoint = Corners[PreviousCorner];
			Vector2f CurrentPoint = Corners[i];
			Vector2f NextPoint = Corners[NextCorner];

			double aAngle = Math.toDegrees(theta(CurrentPoint, LastPoint));
			double cAngle = Math.toDegrees(theta(CurrentPoint, NextPoint));
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
				// startAngle = firstAngle;
				degs = secondAngle - firstAngle;
			}
			else
			{
				// startAngle = secondAngle;
				degs = firstAngle + (360 - secondAngle);
			}

			Angles[i] = (float) degs;

		}

		return Angles;
	}

	public static double theta(Vector2f a, Vector2f b)
	{
		double ang = Math.atan2(a.GetY() - b.GetY(), b.GetX() - a.GetX());
		if (ang < 0)
			ang += (Math.PI * 2);

		return ang;
	}

}
