package Geometry;

import java.awt.Color;

import Geometry.Shapes.Shape;
import Rendering.Rendering;
import Vectors.Vector2f;

public class Point extends Shape
{

	Vector2f Position;

	@Override
	public Vector2f GetFollowingPosition()
	{
		return Position;
	}

	@Override
	public void Render()
	{
		// TODO Auto-generated method stub
		Rendering.RenderReferencedPoint(Position, new Color(255, 255, 255));
	}

	@Override
	public void Move(Vector2f Translation)
	{
		Position.Add(Translation);
	}

	@Override
	public void SetPosition(Vector2f Position)
	{
		this.Position = Position;
	}

	@Override
	public Vector2f[] GetCollisionPointsWith(Line Collision)
	{
		if (Collision.PointOnLine(Position))
		{
			return new Vector2f[] { Position };
		}

		return null;
	}

	@Override
	public Vector2f[] GetCollisionPointsWith(Shape Collision)
	{
		if (Collision.CollidesWith(Position))
		{
			return new Vector2f[] { Position };
		}

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
		return null;
	}

	@Override
	public boolean CollidesWith(Line Collision)
	{
		return Collision.PointOnLine(Position);
	}

	@Override
	public boolean CollidesWith(Shape Collision)
	{
		return Collision.CollidesWith(Position);
	}

	@Override
	public boolean CollidesWith(Vector2f Point)
	{
		return Position.equals(Point);
	}

	@Override
	public void SetScale(Vector2f Scale)
	{

	}

	@Override
	public void SetCenter(Vector2f Center)
	{
		SetPosition(Center);
	}

}
