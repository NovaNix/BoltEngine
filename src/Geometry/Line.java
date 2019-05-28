package Geometry;

import Utils.BoltUtils;
import Vectors.Vector2f;

public class Line
{

	protected Vector2f PointOnLine;

	protected float Slope;
	protected float YIntercept;

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

	// Returns a horizontal line at the specified Y coord
	public static Line MakeHorizontalLine(float Y)
	{
		return new Line(new Vector2f(0, Y), new Vector2f(1, Y));
	}

	// Returns a vertical line at the specified X coord
	public static Line MakeVerticalLine(float X)
	{
		return new Line(new Vector2f(X, 0), new Vector2f(X, 1));
	}

	// Returns the Y coord for the specified x coord
	public float GetYFromX(float X)
	{
		return (Slope * X) + YIntercept;
	}

	// Returns the intersection with another line. Returns null if the lines are
	// parallel
	public Vector2f GetIntersectionWith(Line Tested)
	{
		Vector2f Collision = null;

		float XCollision = 0;
		float YCollision = 0;

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

	// Returns all intersections between this line and all lines in the Lines list
	public Vector2f[] GetIntersectionsWith(Line[] Lines)
	{
		Vector2f[] Collisions = new Vector2f[Lines.length];

		for (int i = 0; i < Lines.length; i++)
		{
			Collisions[i] = GetIntersectionWith(Lines[i]);
		}

		return BoltUtils.RemoveNulls(Collisions);
	}

	// Returns if a point is on this line
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

	// Returns if this line is horizontal
	public boolean IsHorizontal()
	{
		return Slope == 0;
	}

	// Returns if this line is vertical
	public boolean IsVertical()
	{
		return Math.abs(Slope) == Double.POSITIVE_INFINITY;
	}

	// Returns if this line and the specified line are parallel
	public boolean LinesAreParallel(Line Side)
	{
		return (Slope == Side.GetSlope());
	}

	// Returns if the point is above this line. If this line is vertical, it will
	// return if the point has a higher x
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

	// Returns if the point is below this line. If this line is vertical, it will
	// return if the point has a lower x
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

	// Returns if the two points are on the same side of the line
	public boolean OnSameSide(Vector2f Check1, Vector2f Check2)
	{
		return (PointAbove(Check1) && PointAbove(Check2)) || (PointBelow(Check1) && PointBelow(Check2));
	}

	// Returns the auto-generated point on the line
	public Vector2f GetPointOnLine()
	{
		return PointOnLine;
	}

	// Returns the slope of the line
	public float GetSlope()
	{
		return Slope;
	}

	// Returns the Y-Intercept of the line
	public float GetYIntercept()
	{
		return YIntercept;
	}
}
