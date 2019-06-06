package Geometry.Shapes;

import Geometry.Line;
import Geometry.Segment;
import Rendering.Rendering;
import Utils.BoltUtils;
import Vectors.ReferencedVector2f;
import Vectors.Vector2f;

public class Circle extends Shape<Circle>
{

	public Circle(Vector2f Center, float Radius)
	{
		this.Center = Center;
		this.Radius = Radius;

		this.Position = new ReferencedVector2f(Center);
		Position.Add(new Vector2f(-Radius, -Radius));
	}

	@Override
	public Vector2f[] GetCollisionPointsWith(Line Collision)
	{

		if (Radius > 0)
		{
			float m = Collision.GetSlope();
			float b = Collision.GetYIntercept();
			float h = Center.GetX();
			float k = Center.GetY();
			float r = Radius;

			if (!Collision.IsVertical())
			{

				if (Math.pow(Collision.GetSlope(), 2) + 1 != 0)
				{

					// Equasion: x = (h - b m + k m - sqrt(-b^2 + 2 b k - k^2 - 2 b h m + 2 h k m -
					// h^2 m^2 + r^2 + m^2 r^2))/(1 + m^2);

					double E1 = h - (b * m) + k * m;
					double E2 = Math.sqrt(-BoltUtils.Square(b) + 2 * b * k - BoltUtils.Square(k) - 2 * b * h * m + 2 * h * k * m - BoltUtils.Square(h) * BoltUtils.Square(m) + BoltUtils.Square(r) + BoltUtils.Square(m) * BoltUtils.Square(r));
					double E3 = (1 + BoltUtils.Square(m));

					double X1 = (E1 - E2) / E3;
					double X2 = (E1 + E2) / E3;

					Vector2f Point1 = new Vector2f((float) X1, Collision.GetYFromX((float) X1));
					Vector2f Point2 = new Vector2f((float) X2, Collision.GetYFromX((float) X2));

					if (!Collision.PointOnLine(Point1))
					{
						Point1 = null;
					}

					if (!Collision.PointOnLine(Point2))
					{
						Point2 = null;
					}

					return BoltUtils.RemoveNulls(new Vector2f[] { Point1, Point2 });
				}

				else
				{
					return null;
				}
			}

			else
			{
				double X = Collision.GetPointOnLine().GetX();
				double Y1 = k + Math.sqrt(-BoltUtils.Square(h) + (2 * h * X) + BoltUtils.Square(r) - BoltUtils.Square(X));
				double Y2 = k - Math.sqrt(-BoltUtils.Square(h) + (2 * h * X) + BoltUtils.Square(r) - BoltUtils.Square(X));

				Vector2f Point1 = new Vector2f((float) X, (float) Y1);
				Vector2f Point2 = new Vector2f((float) X, (float) Y2);

				if (!Collision.PointOnLine(Point1))
				{
					Point1 = null;
				}

				if (!Collision.PointOnLine(Point2))
				{
					Point2 = null;
				}

				return BoltUtils.RemoveNulls(new Vector2f[] { Point1, Point2 });
			}
		}

		else if (Radius == 0)
		{
			if (Collision.PointOnLine(Center))
			{
				return new Vector2f[] { Center.Derive() };
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

	@Override
	public Vector2f[] GetCollisionPointsWith(Shape Collision)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Segment[] GetCollisionSegmentsWith(Line Collision)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Segment[] GetCollisionSegmentsWith(Shape Collision)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean CollidesWith(Line Collision)
	{
		return (GetCollisionPointsWith(Collision) != null);
	}

	@Override
	public boolean CollidesWith(Shape Collision)
	{
		if (Collision instanceof Circle)
		{
			return Center.GetDistanceTo(Collision.GetCenter()) <= Radius + ((Circle) Collision).GetRadius();
		}

		return false;
	}

	@Override
	public boolean CollidesWith(Vector2f Point)
	{
		return Center.GetDistanceTo(Point) <= Radius;
	}

	@Override
	public Vector2f GetFollowingPosition()
	{
		return Center;
	}

	@Override
	public void Render()
	{
		Rendering.RenderReferencedOval(Center, new Vector2f(Radius, Radius));
	}

	@Override
	public void Move(Vector2f Translation)
	{
		Center.Add(Translation);
	}

	@Override
	public void SetPosition(Vector2f Position)
	{
		Center.SetPosition(Position);
		Center.Add(new Vector2f(Radius, Radius));
	}

	public float GetRadius()
	{
		return Radius;
	}

	@Override
	public void SetScale(Vector2f Scale)
	{
		this.Radius = Scale.GetX() / 2;
		this.Position.SetPosition(Center);
		Position.Subtract(new Vector2f(Radius, Radius));
	}

	@Override
	public void SetCenter(Vector2f Center)
	{
		this.Center.SetPosition(Center);
	}

	@Override
	public Circle Clone()
	{
		return new Circle(Center.Derive(), Radius);
	}

	@Override
	public boolean CollidesWith(Line Collision, boolean IncludeContour)
	{
		return false;
	}

	@Override
	public boolean CollidesWith(Shape Collision, boolean IncludeContour)
	{
		return false;
	}

	@Override
	public boolean CollidesWith(Vector2f Point, boolean IncludeContour)
	{
		if (IncludeContour == true)
		{
			return CollidesWith(Point);
		}

		else
		{
			return Center.GetDistanceTo(Point) < Radius;
		}
	}

}
