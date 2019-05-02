package Geometry;

import Utils.BoltUtils;
import Vectors.Vector2f;

public class Line
{

	Vector2f PointOnLine;

	float Slope;
	float YIntercept;

	public Line(Vector2f Point, float Slope)
	{
		this.PointOnLine = Point;
		this.Slope = Slope;

		this.YIntercept = PointOnLine.GetY() - (Slope * PointOnLine.GetX());
	}

	public Line(Vector2f Point1, Vector2f Point2)
	{
		this.PointOnLine = Point1;

		this.Slope = (Point2.GetY() - Point1.GetY()) / (Point2.GetX() - Point1.GetX());
		this.YIntercept = PointOnLine.GetY() - (Slope * PointOnLine.GetX());
	}

	public Line(float Slope, float YIntercept)
	{
		this.Slope = Slope;
		this.YIntercept = YIntercept;

		this.PointOnLine = new Vector2f(0, YIntercept);
	}

	public static Line MakeHorizontalLine(float Y)
	{
		return new Line(new Vector2f(0, Y), new Vector2f(1, Y));
	}

	public static Line MakeVerticalLine(float X)
	{
		return new Line(new Vector2f(X, 0), new Vector2f(X, 1));
	}

	public float GetYFromX(float X)
	{
		return (Slope * X) + YIntercept;
	}

	public Vector2f GetIntersectionWith(Line Tested)
	{
		Vector2f Collision = null;
		
		float XCollision;
		float YCollision;
		
		if (!LinesAreParallel(Tested))
		{
			if (!IsVertical() && !Tested.IsVertical())
			{
				XCollision = (Tested.GetYIntercept() - YIntercept) / (Slope - Tested.GetSlope());
				YCollision = (Slope * XCollision) + YIntercept;
			}

			else if (IsVertical() && !Tested.IsVertical())
			{
				XCollision = PointOnLine.GetX();
				YCollision = (Tested.GetSlope() * XCollision) + Tested.GetYIntercept();
			}

			else if (!IsVertical() && Tested.IsVertical())
			{
				XCollision = Tested.GetPointOnLine().GetX();
				YCollision = (Slope * XCollision) + YIntercept;
			}
			
			Collision = new Vector2f(XCollision, YCollision);

			if (!PointOnLine(Collision) || !Tested.PointOnLine(Collision))
			{
				return null;
			}
			
		}

		return Collision;
	}

	public Vector2f[] GetIntersectionsWith(Line[] Lines)
	{
		Vector2f[] Collisions = new Vector2f[Lines.length];

		for (int i = 0; i < Lines.length; i++)
		{
			Collisions[i] = GetIntersectionWith(Lines[i]);
		}

		return BoltUtils.RemoveNulls(Collisions);
	}

	public boolean PointOnLine(Vector2f Point)
	{
		if (!IsVertical())
		{
			return Point.GetY() == (Slope * Point.GetX()) + YIntercept;
		}

		else
		{
			return Point.GetX() == PointOnLine.GetX();
		}
	}

	public boolean IsHorizontal()
	{
		return Slope == 0;
	}

	public boolean IsVertical()
	{
		return Math.abs(Slope) == Double.POSITIVE_INFINITY;
	}

	public boolean LinesAreParallel(Line Side)
	{
		return (Slope == Side.GetSlope());
	}

	public boolean PointAbove(Vector2f Point)
	{
		if (!IsVertical())
		{
			return Point.GetY() >= (Slope * Point.GetX()) + YIntercept;
		}

		else
		{
			return PointOnLine.GetX() <= Point.GetX();
		}
	}

	public boolean PointBelow(Vector2f Point)
	{
		if (!IsVertical())
		{
			return Point.GetY() <= (Slope * Point.GetX()) + YIntercept;
		}

		else 
		{
			return PointOnLine.GetX() >= Point.GetX();
		}
	}

	public boolean OnSameSide(Vector2f Check1, Vector2f Check2)
	{
		return (PointAbove(Check1) && PointAbove(Check2)) || (PointBelow(Check1) && PointBelow(Check2));
	}

	public Vector2f GetPointOnLine()
	{
		return PointOnLine;
	}

	public float GetSlope()
	{
		return Slope;
	}

	public float GetYIntercept()
	{
		return YIntercept;
	}
}
