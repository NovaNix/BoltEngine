package Geometry.Shapes;

import java.util.ArrayList;

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

	protected float[] CompressedCorners;
	protected int[] Index;
	protected int[] TextureCoords;
	
	public Polygon(Vector2f[] Corners)
	{
		this.Position = new Vector2f(Vector2fUtils.GetMinX(Corners).GetX(), Vector2fUtils.GetMaxY(Corners).GetY());

		this.Corners = new Vector2fGroup(Corners);
		this.Corners.ToRelative(Position);

		this.Sides = Segmant.GenerateSegmants(this.Corners.ToArray());

		Vector2f AVCenter = Vector2fUtils.GetAverage(this.Corners.ToArray());

		this.Center = new ReferencedVector2f(Position);
		this.Center.SetPosition(AVCenter);

		this.Radius = Center.GetDistanceTo(Vector2fUtils.GetFurthestPointFrom(Center, this.Corners.ToArray()));

		this.BoundingBubble = new Circle(Center, Radius);
	}

	public Polygon(Vector2f Position, Vector2f[] Corners)
	{
		this.Position = Position;

		this.Corners = new Vector2fGroup(Corners);
		this.Corners.ToRelative(Position);

		this.Sides = Segmant.GenerateSegmants(this.Corners.ToArray());

		Vector2f AVCenter = Vector2fUtils.GetAverage(this.Corners.ToArray());

		this.Center = new ReferencedVector2f(Position);
		this.Center.SetPosition(AVCenter);

		this.Radius = Center.GetDistanceTo(Vector2fUtils.GetFurthestPointFrom(Center, Corners));

		this.BoundingBubble = new Circle(Center, Radius);
	}

	public Vector2f[] GetCompressed()
	{
		Vector2f BottemLeft = new Vector2f(Vector2fUtils.GetMinX(Corners.ToArray()).GetX(), Vector2fUtils.GetMinY(Corners.ToArray()).GetY());
		Vector2f TopRight = new Vector2f(Vector2fUtils.GetMaxX(Corners.ToArray()).GetX(), Vector2fUtils.GetMaxY(Corners.ToArray()).GetY());

		float PolygonWidth = BottemLeft.GetXDistanceTo(TopRight);
		float PolygonHeight = BottemLeft.GetYDistanceTo(TopRight);

		Vector2f PCenter = BottemLeft.Derive();
		PCenter.Add(new Vector2f(PolygonWidth / 2, PolygonHeight / 2));

		Vector2fGroup Vectors = Corners.Clone();

		Vectors.Subtract(PCenter);
		Vectors.Divide(new Vector2f(PolygonWidth / 2, PolygonHeight / 2));

		return Vectors.ToArray();

	}

	public void Cut(Triangle Tri)
	{
		int C1 = Corners.GetVectorPosition(Tri.GetCorner1());
		int C2 = Corners.GetVectorPosition(Tri.GetCorner2());
		int C3 = Corners.GetVectorPosition(Tri.GetCorner3());
		
		int Mid = GetMiddlePoint(C1, C2, C3);
		
		Corners.RemoveVector(Corners.ToArray()[Mid]);
	}
	
	private int GetMiddlePoint(int P1, int P2, int P3)
	{
		int[] Points = {P1, P2, P3};
		
		for (int i = 0; i < 3; i++)
		{
			int PreviousCorner;
			int NextCorner;
			
			int SelectedPoint = Points[i];
			
			if (SelectedPoint == 0) 
			{
				PreviousCorner = Corners.ToArray().length - 1;
				NextCorner = 1;
			}

			else if (SelectedPoint == Corners.ToArray().length - 1) 
			{
				PreviousCorner = SelectedPoint - 1;
				NextCorner = 0;
			}

			else 
			{
				PreviousCorner = SelectedPoint - 1;
				NextCorner = SelectedPoint + 1;
			}
			
			if (i == 0)
			{
				if (Math.max(P2, P3) == Math.max(PreviousCorner, NextCorner) && Math.min(P2, P3) == Math.min(PreviousCorner, NextCorner))
				{
					return P1;
				}
			}
			
			else if (i == 1)
			{
				if (Math.max(P1, P3) == Math.max(PreviousCorner, NextCorner) && Math.min(P1, P3) == Math.min(PreviousCorner, NextCorner))
				{
					return P2;
				}
			}
			
			else if (i == 2)
			{
				if (Math.max(P2, P1) == Math.max(PreviousCorner, NextCorner) && Math.min(P2, P1) == Math.min(PreviousCorner, NextCorner))
				{
					return P3;
				}
			}
		}
		
		System.out.println("Point not on polygon!");
		
		return 0;
	}
	
	public Triangle[] ExtractTriangles()
	{
		Polygon Clone = Copy();
		
		ArrayList<Triangle> Triangles = new ArrayList<Triangle>(); 
		
		boolean Done = false;
		
		System.out.println("Extracting Triangles from Polygon");
		
		while (!Done) 
		{
			
			ArrayList<Integer> ConcavePoints = new ArrayList<Integer>();
			
			Vector2f[] CornerList = Corners.ToArray();
			
			for (int i = 0; i < CornerList.length; i++)
			{
				if (PointConcave(i))
				{
					System.out.println("Found a concave point! It was at " + i);
					ConcavePoints.add(i);
				}
			}

			boolean[] UsedList = new boolean[CornerList.length];
			
			for (int i = 0; i < CornerList.length; i++) 
			{
				if (UsedList[i] == false && !ConcavePoints.contains(i))
				{
					int PreviousCorner;
					int NextCorner;

					if (i == 0) 
					{
						PreviousCorner = Corners.ToArray().length - 1;
						NextCorner = 1;
					}

					else if (i == Corners.ToArray().length - 1) 
					{
						PreviousCorner = i - 1;
						NextCorner = 0;
					}

					else 
					{
						PreviousCorner = i - 1;
						NextCorner = i + 1;
					}

					Triangle Tri = new Triangle(CornerList[PreviousCorner], CornerList[i], CornerList[NextCorner]);

					System.out.println("Triangle made!");
					
					Triangles.add(Tri);

					if (Clone.GetCornerCount() == 3)
					{
						Done = true;

						Triangle[] TriangleArray = new Triangle[Triangles.size()];
						TriangleArray = Triangles.toArray(TriangleArray);
						
						System.out.println("Finished extracting triangles! We had " + TriangleArray.length + " triangles!");
						
						return TriangleArray;
					}
					
					Clone.Cut(Tri);
					
					UsedList[PreviousCorner] = true;
					UsedList[i] = true;
					UsedList[NextCorner] = true;
				}
			} 
		}
		
		return null;
	}
	
	public int GetCornerCount()
	{
		return Corners.ToArray().length;
	}
	
	private boolean PointConcave(int Corner)
	{
		
		int PreviousCorner;
		int NextCorner;
		
		if (Corner == 0)
		{
			PreviousCorner = Corners.ToArray().length - 1;
			NextCorner = 1;
		}
		
		else if (Corner == Corners.ToArray().length - 1)
		{
			PreviousCorner = Corner - 1;
			NextCorner = 0;
		}
		
		else
		{
			PreviousCorner = Corner - 1;
			NextCorner = Corner + 1;
		}
		
		Vector2f[] CornerArray = Corners.ToArray();
		
		Vector2f AB = CornerArray[Corner].Derive();
		AB.Subtract(CornerArray[PreviousCorner]);
		AB = new Vector2f(-AB.GetY(), AB.GetX());
		Vector2f BC = CornerArray[NextCorner].Derive();
		BC.Subtract(CornerArray[Corner]);
		
		float DotProduct = (AB.GetX() * BC.GetX()) + (AB.GetY() * BC.GetY());

		System.out.println("AB = " + AB.ToString());
		System.out.println("BC = " + BC.ToString());
		
		System.out.println("The dot product was " + DotProduct);
		
		return (DotProduct) < 0;
	}
	
	public Vector2f GetScale()
	{
		Vector2f BottemLeft = new Vector2f(Vector2fUtils.GetMinX(Corners.ToArray()).GetX(), Vector2fUtils.GetMinY(Corners.ToArray()).GetY());
		Vector2f TopRight = new Vector2f(Vector2fUtils.GetMaxY(Corners.ToArray()).GetX(), Vector2fUtils.GetMaxY(Corners.ToArray()).GetY());

		float PolygonWidth = BottemLeft.GetXDistanceTo(TopRight);
		float PolygonHeight = BottemLeft.GetYDistanceTo(TopRight);

		return new Vector2f(PolygonWidth, PolygonHeight);
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
		if (BoundingBubble.CollidesWith(Collision.GetBoundingBubble()))
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
		// Corners.Add(Translation);
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
		Vector2f CurrentScale = GetScale();
		Vector2f MultiplyBy = new Vector2f(Scale.GetX() - CurrentScale.GetX(), Scale.GetY() - CurrentScale.GetY());

		Corners.Multiply(MultiplyBy);

		// this.Sides = Segmant.GenerateSegmants(Corners.ToArray());

		this.Center = new ReferencedVector2f(Position);
		this.Center.SetPosition(Vector2fUtils.GetAverage(Corners.ToArray()));

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
		// Corners.Add(Translation);
	}

	public Polygon Copy()
	{
		return new Polygon(Position, Corners.ToArray());
	}
}
