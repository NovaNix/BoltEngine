package Geometry.Shapes;

import Geometry.Line;
import Geometry.Segment;
import Rendering.Renderable;
import Rendering.Cameras.Followable;
import Utils.Movable;
import Vectors.Vector2f;

public abstract class Shape<H extends Shape> implements Followable, Renderable, Movable
{

	protected Vector2f Position;

	protected Vector2f Center;

	protected float Radius;

	public abstract Vector2f[] GetCollisionPointsWith(Line Collision);

	public abstract Vector2f[] GetCollisionPointsWith(Shape Collision);

	public abstract Segment[] GetCollisionSegmentsWith(Line Collision);

	public abstract Segment[] GetCollisionSegmentsWith(Shape Collision);

	public abstract boolean CollidesWith(Line Collision);

	public abstract boolean CollidesWith(Shape Collision);

	public abstract boolean CollidesWith(Vector2f Point);

	public abstract boolean CollidesWith(Line Collision, boolean IncludeContour);

	public abstract boolean CollidesWith(Shape Collision, boolean IncludeContour);

	public abstract boolean CollidesWith(Vector2f Point, boolean IncludeContour);

	public abstract void SetScale(Vector2f Scale);

	public abstract void SetPosition(Vector2f Position);

	public abstract void SetCenter(Vector2f Center);

	public abstract H Clone();

	public Vector2f GetPosition()
	{
		return Position;
	}

	public Vector2f GetCenter()
	{
		return Center;
	}

	public float GetRadius()
	{
		return Radius;
	}

}
