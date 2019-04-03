package Geometry.Shapes;

import java.util.ArrayList;
import java.util.Arrays;

import Geometry.Line;
import Geometry.Ray;
import Geometry.Segmant;
import Utils.BoltUtils;
import Utils.Vector2fUtils;
import Vectors.ReferencedVector2f;
import Vectors.Vector2f;
import Vectors.Vector2fGroup;

public class Polygon extends Shape
{
	
	Vector2fGroup Corners;
	Segmant[] Sides;
	
	Circle BoundingBubble;
	
	public Polygon(Vector2f[] Corners)
	{
		this.Position = new Vector2f(Vector2fUtils.GetFurthestWest(Corners).GetX(), Vector2fUtils.GetFurthestNorth(Corners).GetY());
		
		this.Corners = new Vector2fGroup(Corners);
		this.Sides = Segmant.GenerateSegmants(Corners);
		
		this.Corners.ToRelative(Position);
		
		Vector2f AVCenter = Vector2fUtils.GetAverage(Corners);
		
		this.Center = new ReferencedVector2f(Position);
		this.Center = AVCenter.Derive();
		
		this.Radius = Center.GetDistanceTo(Vector2fUtils.GetFurthestPointFrom(Center, Corners));
		
		this.BoundingBubble = new Circle(Center, Radius);
	}
	
	public Polygon(Vector2f Position, Vector2f[] Corners)
	{
		this.Position = Position;
		
		this.Corners = new Vector2fGroup(Corners);
		this.Sides = Segmant.GenerateSegmants(Corners);
		
		this.Corners.ToRelative(Position);
		
		Vector2f AVCenter = Vector2fUtils.GetAverage(Corners);
		
		this.Center = new ReferencedVector2f(Position);
		this.Center = AVCenter.Derive();
		
		this.Radius = Center.GetDistanceTo(Vector2fUtils.GetFurthestPointFrom(Center, Corners));
		
		this.BoundingBubble = new Circle(Center, Radius);
	}
	
	public void MergeWith(Polygon Merged)
	{
		ArrayList<Segmant> Edges = new ArrayList<Segmant>();
		
		for (int i = 0; i < Sides.length; i++)
		{
			
		}
		
	}

	@Override
	public Vector2f GetFollowingPosition()
	{
		return Center;
	}

	@Override
	public void Render()
	{
		for (int i = 0; i < Sides.length; i++)
		{
			Sides[i].Render();
		}
	}

	@Override
	public Vector2f[] GetCollisionPointsWith(Line Collision)
	{
		Vector2f[] Collisions = new Vector2f[Sides.length];
		
		for (int i = 0; i < Sides.length; i++)
		{
			Collisions[i] = Sides[i].GetIntersectionWith(Collision);
		}
		
		return BoltUtils.RemoveNulls(Collisions);
	}

	@Override
	public Vector2f[] GetCollisionPointsWith(Shape Collision)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Segmant[] GetCollisionSegmantsWith(Line Collision)
	{
		Segmant[] Collisions = new Segmant[Sides.length];
		
		for (int i = 0; i < Sides.length; i++)
		{
			if (Sides[i].GetIntersectionWith(Collision) != null)
			{
				Collisions[i] = Sides[i];
			}
		}
		
		return (Segmant[]) BoltUtils.RemoveNulls(Collisions);
	}

	@Override
	public Segmant[] GetCollisionSegmantsWith(Shape Collision)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean CollidesWith(Line Collision)
	{
		if (CollidesWith(Collision.GetPointOnLine()))
		{
			 return true;
		}
		
		else
		{
			return Collision.GetIntersectionsWith(Sides) != null;
		}
	}

	@Override
	public boolean CollidesWith(Shape Collision)
	{

		if (Collision instanceof Polygon)
		{
			return CollidesWith((Polygon) Collision);
		}
		
		else
		{
			if (CollidesWith(Collision.GetCenter()) || Collision.CollidesWith(Center))
			{
				return true;
			}
			
			for (int i = 0; i < Sides.length; i++)
			{
				if (Collision.CollidesWith(Sides[i]))
				{
					return true;
				}
			} 
		}
		
		return false;
	}

	public boolean CollidesWith(Polygon Collision)
	{
		if (BoundingBubble.CollidesWith(((Polygon) Collision).GetBoundingBubble()))
		{
			if (CollidesWith(Collision.GetCenter()) || Collision.CollidesWith(Center))
			{
				return true;
			}
			
			for (int i = 0; i < Sides.length; i++)
			{
				if (Collision.CollidesWith(Sides[i]))
				{
					return true;
				}
			} 
		} 
		
		return false;
	}
	
	@Override
	public boolean CollidesWith(Vector2f Point)
	{
		Ray PointRay = new Ray(Point, 0f);
		
		Vector2f[] RayCollisions = new Vector2f[Sides.length];
		
		for (int i = 0; i < Sides.length; i++)
		{
			RayCollisions[i] = PointRay.GetIntersectionWith(Sides[i]);
		}
		
		Vector2f[] Collisions = BoltUtils.RemoveNulls(RayCollisions);
		
		if (Collisions != null)
		{
			return (Collisions).length % 2 != 0;
		}
		
		else
		{
			return false;
		}
		
	}

	@Override
	public void Move(Vector2f Translation)
	{
		Position.Add(Translation);
//		Corners.Add(Translation);
	}

	@Override
	public void SetPosition(Vector2f Position)
	{
		this.Position.SetPosition(Position);
	}
	
	public Circle GetBoundingBubble()
	{
		return BoundingBubble;
	}
	
	public Vector2f[] GetCorners()
	{
		return Corners.ToArray();
	}
	
	public Segmant[] GetSides()
	{
		return Sides;
	}

	@Override
	public void SetScale(Vector2f Scale)
	{
		float CurrentXScale = Math.abs(Vector2fUtils.GetFurthestEast(Corners.ToArray()).GetXDistanceTo(Vector2fUtils.GetFurthestWest(Corners.ToArray())));
		float CurrentYScale = Math.abs(Vector2fUtils.GetFurthestNorth(Corners.ToArray()).GetXDistanceTo(Vector2fUtils.GetFurthestSouth(Corners.ToArray())));
		
		Vector2f MultiplyBy = new Vector2f(Scale.GetX() - CurrentXScale, Scale.GetY() - CurrentYScale);
		
		Corners.Multiply(MultiplyBy);
		
		this.Sides = Segmant.GenerateSegmants(Corners.ToArray());
		
		this.Center = new ReferencedVector2f(Position);
		this.Center = Vector2fUtils.GetAverage(Corners.ToArray());
		
	}

	public void Rotate(float Rotation)
	{
		
	}
	
	@Override
	public void SetCenter(Vector2f Center)
	{
		Vector2f Translation = this.Center.Derive();
		Translation.Subtract(Center);
		Position.Add(Translation);
//		Corners.Add(Translation);
	}
}
