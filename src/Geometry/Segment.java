package Geometry;

import java.awt.Color;

import Rendering.Renderable;
import Rendering.Rendering;
import Rendering.Rendering.RenderingType;
import Vectors.Vector2f;

public class Segment extends Line implements Renderable
{

	Vector2f Point1;
	Vector2f Point2;

	public Segment(Vector2f Point1, Vector2f Point2)
	{
		super(Point1, Point2);

		this.Point1 = Point1;
		this.Point2 = Point2;
	}

	// Returns the segments in between the points. Loops the last point to the first
	// point
	public static Segment[] GenerateSegments(Vector2f[] Points)
	{
		Segment[] Sides = new Segment[Points.length];

		for (int i = 0; i < Points.length - 1; i++)
		{
			Sides[i] = new Segment(Points[i], Points[i + 1]);
		}

		Sides[Points.length - 1] = new Segment(Points[Points.length - 1], Points[0]);

		return Sides;
	}

	// Returns this segment as two segments broken on the specified point. If the
	// point is not on the line, it returns null
	public Segment[] BreakOnPoint(Vector2f Point)
	{
		if (PointOnLine(Point))
		{
			return new Segment[] { new Segment(Point1, Point), new Segment(Point2, Point) };
		}

		else
		{
			return null;
		}
	}

	// Returns this segment as two segments broken by the line. If the line is
	// parallel, it returns null
	public Segment[] BreakOnIntersection(Line Tested)
	{
		if (!LinesAreParallel(Tested))
		{
			return BreakOnPoint(GetIntersectionWith(Tested));
		}

		else
		{
			return null;
		}
	}

	@Override
	public boolean PointOnLine(Vector2f Point)
	{
		if (!IsVertical())
		{
			if (Point.InDomain(GetDomain()[0], GetDomain()[1]) && Point.InRange(GetRange()[0], GetRange()[1]))
			{
				// System.out.println("Point on line, not vertical: " + (Point.GetX() ==
				// PointOnLine.GetX()));
				// System.out.println("Slope: " + Slope);
				return Point.GetY() == Slope * Point.GetX() + YIntercept;
			}

			else
			{
				return false;
			}
		}

		else
		{
			return Point.GetX() == Point1.GetX() && (GetRange()[0] <= Point.GetY() && Point.GetY() <= GetRange()[1]);
		}
	}

	// Returns if the line is within a specified domain
	public boolean InDomain(float X1, float X2)
	{

		Vector2f Domain = new Vector2f(X1, X2);

		if (Point1.InDomain(X1, X2) || Point2.InDomain(X1, X2))
		{
			return true;
		}

		// NOTE: Range is used because X2 is in the Y axis of the Vector2f Domain.

		else if (Domain.InDomain(Point1.GetX(), Point2.GetX()) || Domain.InRange(Point1.GetX(), Point2.GetX()))
		{
			return true;
		}

		else
		{
			return false;
		}
	}

	// Returns if the line is within a specified range
	public boolean InRange(float Y1, float Y2)
	{
		Vector2f Range = new Vector2f(Y1, Y2);

		if (Point1.InRange(Y1, Y2) || Point2.InRange(Y1, Y2))
		{
			return true;
		}

		// NOTE: Domain is used because Y2 is in the X axis of the Vector2f Range.

		else if (Range.InDomain(Point1.GetY(), Point2.GetY()) || Range.InRange(Point1.GetY(), Point2.GetY()))
		{
			return true;
		}

		else
		{
			return false;
		}
	}

	// Returns the first end point of this segment
	public Vector2f GetPoint1()
	{
		return Point1;
	}

	// Returns the second end point of this segment
	public Vector2f GetPoint2()
	{
		return Point2;
	}

	// Returns the domain between the two end points
	public float[] GetDomain()
	{
		return new float[] { Math.min(Point1.GetX(), Point2.GetX()), Math.max(Point1.GetX(), Point2.GetX()) };
	}

	// Returns the range between the two end points
	public float[] GetRange()
	{
		return new float[] { Math.min(Point1.GetY(), Point2.GetY()), Math.max(Point1.GetY(), Point2.GetY()) };
	}

	@Override
	public void Render()
	{
		Rendering.RenderReferencedLine(Point1, Point2, 1f, new Color(255, 255, 255));
	}

	@Override
	public void Render(Color Hue, RenderingType Type)
	{
		Rendering.RenderReferencedLine(Point1, Point2, 1f, Hue);
	}
}
