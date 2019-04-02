package Geometry.Shapes;

import Geometry.Line;
import Geometry.Segmant;
import Rendering.Renderable;
import Utils.Followable;
import Utils.Movable;
import Vectors.Vector2f;

public abstract class Shape implements Followable, Renderable, Movable
{

	Vector2f Position;
	
	Vector2f Center;
	
	float Radius;
	
	public abstract Vector2f[] GetCollisionPointsWith(Line Collision);
	public abstract Vector2f[] GetCollisionPointsWith(Shape Collision);
	
	public abstract Segmant[] GetCollisionSegmantsWith(Line Collision);
	public abstract Segmant[] GetCollisionSegmantsWith(Shape Collision);
	
	public abstract boolean CollidesWith(Line Collision);
	public abstract boolean CollidesWith(Shape Collision);
	public abstract boolean CollidesWith(Vector2f Point);
	
	public abstract void SetScale(Vector2f Scale);
	
	public abstract void SetPosition(Vector2f Position);
	public abstract void SetCenter(Vector2f Center);
	
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
