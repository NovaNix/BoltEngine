package Geometry.Shapes;

import Geometry.Segment;
import Vectors.Vector2f;

public class Rectangle extends Polygon
{

	Segment TopFace;
	Segment LeftFace;
	Segment RightFace;
	Segment BottemFace;

	Vector2f Scale;

	float Rotation = 0;

	public Rectangle(Vector2f Position, Vector2f Scale)
	{
		super(Position, new Vector2f[] { Position, new Vector2f(Position.GetX() + Scale.GetX(), Position.GetY()), new Vector2f(Position.GetX() + Scale.GetX(), Position.GetY() + Scale.GetY()), new Vector2f(Position.GetX(), Position.GetY() + Scale.GetY()) });

		TopFace = Sides.get(0);
		BottemFace = Sides.get(2);
		LeftFace = Sides.get(3);
		RightFace = Sides.get(1);

		// this.Position = Position;

		this.Scale = Scale;
	}

	public Vector2f GetScale()
	{
		return Scale;
	}

	public boolean CollidesWith(Rectangle Collision)
	{
		if (Rotation == 0 && Collision.GetRotation() == 0)
		{
			return Math.abs(Center.GetXDistanceTo(Collision.GetCenter())) <= (Scale.GetX() / 2) + (Collision.GetScale().GetX() / 2) && Math.abs(Center.GetYDistanceTo(Collision.GetCenter())) <= (Scale.GetY() / 2) + (Collision.GetScale().GetY() / 2);
		}

		else
		{
			return super.CollidesWith(Collision);
		}
	}

	@Override
	public boolean CollidesWith(Vector2f Collision)
	{
		if (Rotation == 0)
		{
			return Collision.InDomain(TopFace.GetDomain()[0], TopFace.GetDomain()[1]) && Collision.InRange(LeftFace.GetRange()[0], LeftFace.GetRange()[1]);
		}

		else
		{
			return super.CollidesWith(Collision);
		}
	}

	public float GetRotation()
	{
		return Rotation;
	}
}
