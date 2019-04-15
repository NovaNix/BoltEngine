package Geometry;

import Vectors.ReferencedVector2f;
import Vectors.Vector2f;

public class Ray extends Line
{

	Vector2f RayStart;
	Vector2f RayPoint;

	float Angle;

	public Ray(Vector2f Start, float Angle)
	{
		super(Start, GenerateRayPoint(Start, 1f, Angle));

		this.RayStart = Start;

		this.RayPoint = GenerateRayPoint(Start, 1f, Angle);

		this.Angle = Angle;
	}

	public static Vector2f GenerateRayPoint(Vector2f Start, float Distance, float Angle)
	{
		float RotatedAngle = Angle;

		int PointQuadrent = 0;

		if (Angle % 90f != 0f)
		{
			if (0f < Angle && Angle < 90f)
			{
				PointQuadrent = 1;
			}

			else if (90f < Angle && Angle < 180f)
			{
				RotatedAngle -= 90;
				PointQuadrent = 2;
			}

			else if (180f < Angle && Angle < 270f)
			{
				RotatedAngle -= 180;
				PointQuadrent = 3;
			}

			else if (270f < Angle && Angle < 360f)
			{
				RotatedAngle -= 270;
				PointQuadrent = 4;
			}
		}

		else
		{
			if (Angle == 0f || Angle == 360f)
			{
				return new ReferencedVector2f(Start, new Vector2f(Distance, 0));
			}

			else if (Angle == 90f)
			{
				return new ReferencedVector2f(Start, new Vector2f(0, Distance));
			}

			else if (Angle == 180f)
			{
				return new ReferencedVector2f(Start, new Vector2f(-Distance, 0));
			}

			else if (Angle == 270f)
			{
				return new ReferencedVector2f(Start, new Vector2f(0, -Distance));
			}
		}

		double XTranslation = Distance * Math.cos(Math.toRadians(RotatedAngle));
		double YTranslation = Distance * Math.sin(Math.toRadians(RotatedAngle));

		switch (PointQuadrent)
		{
			case 1:
				return new ReferencedVector2f(Start, new Vector2f((float) XTranslation, (float) YTranslation));
			case 2:
				return new ReferencedVector2f(Start, new Vector2f((float) -XTranslation, (float) YTranslation));
			case 3:
				return new ReferencedVector2f(Start, new Vector2f((float) -XTranslation, (float) -YTranslation));
			case 4:
				return new ReferencedVector2f(Start, new Vector2f((float) XTranslation, (float) -YTranslation));
		}

		return null;
	}

	@Override
	public Vector2f GetIntersectionWith(Line Tested)
	{
		if (!LinesAreParallel(Tested))
		{
			if (!IsVertical() && !Tested.IsVertical())
			{
				float XCollision = (Tested.GetYIntercept() - YIntercept) / (Slope - Tested.GetSlope());
				float YCollision = (Slope * XCollision) + YIntercept;

				Vector2f Collision = new Vector2f(XCollision, YCollision);

				if (PointOnLine(Collision) && Tested.PointOnLine(Collision))
				{
					return Collision;
				}

				else
				{
					return null;
				}
			}

			else if (IsVertical() && !Tested.IsVertical())
			{
				float XCollision = PointOnLine.GetX();
				float YCollision = (Tested.GetSlope() * XCollision) + Tested.GetYIntercept();

				Vector2f Collision = new Vector2f(XCollision, YCollision);

				if (PointOnLine(Collision) && Tested.PointOnLine(Collision))
				{
					return Collision;
				}

				else
				{
					return null;
				}
			}

			else if (!IsVertical() && Tested.IsVertical())
			{
				float XCollision = Tested.GetPointOnLine().GetX();
				float YCollision = (Slope * XCollision) + YIntercept;

				Vector2f Collision = new Vector2f(XCollision, YCollision);

				if (PointOnLine(Collision) && Tested.PointOnLine(Collision))
				{
					return Collision;
				}

				else
				{
					return null;
				}
			}

			else
			{
				return null;
			}
		}

		else
		{
			return null;
		}
	}

	public boolean PointingNorth()
	{
		return 0 < Angle && Angle < 180;
	}

	public boolean PointingSouth()
	{
		return 180 < Angle && Angle < 360;
	}

	public boolean PointingEast()
	{
		return 270 < Angle || Angle < 90;
	}

	public boolean PointingWest()
	{
		return 90 < Angle && Angle < 270;
	}

	@Override
	public boolean PointOnLine(Vector2f Point)
	{
		if (IsVertical())
		{
			if (PointingNorth())
			{
				return (Point.AboveRange(RayStart.GetY()) || Point.GetY() == RayStart.GetY()) && Point.GetX() == PointOnLine.GetX();
			}

			else if (PointingSouth())
			{
				return (Point.BelowRange(RayStart.GetY()) || Point.GetY() == RayStart.GetY()) && Point.GetX() == PointOnLine.GetX();
			}
		}

		else
		{
			if (PointingEast())
			{
				// Point.Print();
				// RayStart.Print();

				// System.out.println(Slope);
				// System.out.println(YIntercept);

				return (Point.AboveDomain(RayStart.GetX()) || Point.GetX() == RayStart.GetX()) && Point.GetY() == (Slope * Point.GetX()) + YIntercept;
			}

			else if (PointingWest())
			{
				return (Point.BelowDomain(RayStart.GetX()) || Point.GetX() == RayStart.GetX()) && Point.GetY() == (Slope * Point.GetX()) + YIntercept;

			}
		}

		return false;
	}

}
