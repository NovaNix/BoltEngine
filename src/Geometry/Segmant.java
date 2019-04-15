package Geometry;

import java.awt.Color;

import Rendering.Renderable;
import Rendering.Rendering;
import Vectors.Vector2f;

public class Segmant extends Line implements Renderable
{

	Vector2f Point1;
	Vector2f Point2;

	public Segmant(Vector2f Point1, Vector2f Point2)
	{
		super(Point1, Point2);

		this.Point1 = Point1;
		this.Point2 = Point2;
	}

	public static Segmant[] GenerateSegmants(Vector2f[] Points)
	{
		Segmant[] Sides = new Segmant[Points.length];

		for (int i = 0; i < Points.length - 1; i++)
		{
			Sides[i] = new Segmant(Points[i], Points[i + 1]);
		}

		Sides[Points.length - 1] = new Segmant(Points[Points.length - 1], Points[0]);

		return Sides;
	}

	public Segmant[] BreakOnPoint(Vector2f Point)
	{
		if (PointOnLine(Point))
		{
			return new Segmant[] { new Segmant(Point1, Point), new Segmant(Point2, Point) };
		}

		else
		{
			return null;
		}
	}

	public Segmant[] BreakOnIntersection(Line Tested)
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
			// boolean Touching = ((Point.GetX() == Point1.GetX()) &&
			// (Point.InRange(GetRange()[0], GetRange()[1])));
			//
			// System.out.println("Point on line, vertical:" + Touching);
			// System.out.println("Slope: " + Slope);
			// System.out.println("In Range: " + (GetRange()[0] <= Point.GetY() &&
			// Point.GetY() <= GetRange()[1]));
			// Point1.Print();
			// Point2.Print();
			//
			// Point.Print();
			return Point.GetX() == Point1.GetX() && (GetRange()[0] <= Point.GetY() && Point.GetY() <= GetRange()[1]);
		}
	}

	// @Override
	// public Vector2f GetIntersectionWith(Line Tested)
	// {
	// Vector2f Collision;
	//
	// if (!LinesAreParallel(Tested))
	// {
	// if (!IsVertical() && !Tested.IsVertical())
	// {
	// float XCollision = (Tested.GetYIntercept() - YIntercept) / (Slope -
	// Tested.GetSlope());
	// float YCollision = (Slope * XCollision) + YIntercept;
	//
	// Collision = new Vector2f(XCollision, YCollision);
	// }
	//
	// else if (IsVertical() && !Tested.IsVertical())
	// {
	// float XCollision = PointOnLine.GetX();
	// float YCollision = (Tested.GetSlope() * XCollision) + Tested.GetYIntercept();
	//
	// Collision = new Vector2f(XCollision, YCollision);
	// }
	//
	// else if (!IsVertical() && Tested.IsVertical())
	// {
	// float XCollision = Tested.GetPointOnLine().GetX();
	// float YCollision = (Slope * XCollision) + YIntercept;
	//
	// Collision = new Vector2f(XCollision, YCollision);
	// }
	//
	// else
	// {
	// return null;
	// }
	// }
	//
	// else
	// {
	// return null;
	// }
	//
	// if (PointOnLine(Collision) && Tested.PointOnLine(Collision))
	// {
	// return Collision;
	// }
	//
	// else
	// {
	// return null;
	// }
	// }

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

	public Vector2f GetPoint1()
	{
		return Point1;
	}

	public Vector2f GetPoint2()
	{
		return Point2;
	}

	public float[] GetDomain()
	{
		return new float[] { Math.min(Point1.GetX(), Point2.GetX()), Math.max(Point1.GetX(), Point2.GetX()) };
	}

	public float[] GetRange()
	{
		return new float[] { Math.min(Point1.GetY(), Point2.GetY()), Math.max(Point1.GetY(), Point2.GetY()) };
	}

	@Override
	public void Render()
	{
		Rendering.RenderReferencedLine(Point1, Point2, 1f, new Color(255, 255, 255));
	}
}
