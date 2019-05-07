package Geometry.Shapes;

import java.awt.Color;
import java.util.ArrayList;

import Engine.BoltMath;
import Geometry.Line;
import Geometry.Ray;
import Geometry.Segmant;
import Rendering.Rendering.RenderingType;
import Rendering.OpenGLTest.VertexBufferObject;
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

	float Rotation = 0f;

	protected float[] CompressedCorners;
	protected int[] Index;
	protected int[] TextureCoords;

	public Polygon(Vector2f[] Corners)
	{
		this.Position = new Vector2f(Vector2fUtils.GetMinX(Corners).GetX(), Vector2fUtils.GetMaxY(Corners).GetY());

		this.Corners = new Vector2fGroup(Corners);
		this.Corners.ToRelative(Position);

		Refresh();
	}

	public Polygon(Vector2f Position, Vector2f[] Corners)
	{
		this.Position = Position;

		this.Corners = new Vector2fGroup(Corners);
		this.Corners.ToRelative(Position);

		Refresh();
	}

	public void Refresh()
	{
		this.Sides = Segmant.GenerateSegmants(this.Corners.ToArray());

		Vector2f AVCenter = Vector2fUtils.GetAverage(this.Corners.ToArray());

		this.Center = new ReferencedVector2f(Position);
		this.Center.SetPosition(AVCenter);

		this.Radius = Center.GetDistanceTo(Vector2fUtils.GetFurthestPointFrom(Center, this.Corners.ToArray()));

		this.BoundingBubble = new Circle(Center, Radius);
	}

	public VertexBufferObject GenerateVBO()
	{
		float[] v = Vector2fUtils.GetCoordList(Corners.ToArray());

		float[] t = Vector2fUtils.GetCoordList(GetCompressed());

		Triangle[] Tris = ExtractTriangles();

		int[] index = new int[Tris.length * 3];

		for (int i = 0; i < Tris.length; i++)
		{
			index[i * 3] = Corners.GetVectorPosition(Tris[i].GetCorner1());
			index[(i * 3) + 1] = Corners.GetVectorPosition(Tris[i].GetCorner2());
			index[(i * 3) + 2] = Corners.GetVectorPosition(Tris[i].GetCorner3());
		}

		return new VertexBufferObject(v, t, index);
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

		Refresh();
	}

	private int GetMiddlePoint(int P1, int P2, int P3)
	{
		int ListEnd = GetCornerCount() - 1;

		int[] Corners = { P1, P2, P3 };

		for (int i = 0; i < Corners.length; i++)
		{
			int SelectedCorner = Corners[i];

			int Corner1;
			int Corner2;

			switch (i)
			{
				case 0:

					Corner1 = P2;
					Corner2 = P3;

					break;
				case 1:

					Corner1 = P1;
					Corner2 = P3;

					break;
				case 2:

					Corner1 = P2;
					Corner2 = P1;

					break;
				default:
					System.out.println("BIG ERROR!");
					return -1;
			}

			int Next;
			int Last;

			if (SelectedCorner == 0)
			{
				Next = 1;
				Last = ListEnd;
			}

			else if (SelectedCorner == ListEnd)
			{
				Next = 0;
				Last = ListEnd - 1;
			}

			else
			{
				Next = SelectedCorner + 1;
				Last = SelectedCorner - 1;
			}

			if ((Corner1 == Next || Corner2 == Next) && (Corner1 == Last || Corner2 == Last))
			{
				return SelectedCorner;
			}
		}

		return -1;
	}

	public Triangle[] ExtractTriangles()
	{
		Polygon Clone = Copy();

		ArrayList<Triangle> Triangles = new ArrayList<Triangle>();

		boolean Done = false;

		Vector2f[] AllCorners = Corners.ToArray();

		while (!Done)
		{
			Vector2f[] CornerList = Clone.GetCorners();

			float[] Angles = BoltMath.GetAngles(Clone);

			for (int i = 0; i < CornerList.length; i++)
			{

				if (!(Angles[i] >= 180))
				{

					int PreviousCorner = (i - 1) % CornerList.length;
					int NextCorner = (i + 1) % CornerList.length;

					Segmant TriLine = new Segmant(CornerList[PreviousCorner], CornerList[NextCorner]);

					Triangle Tri = new Triangle(CornerList[PreviousCorner], CornerList[i], CornerList[NextCorner]);

					boolean LineGood = true;

					for (int j = 0; j < Clone.GetSides().length; j++)
					{
						Vector2f Collision = TriLine.GetIntersectionWith(Clone.GetSides()[j]);

						if (Collision != null)
						{
							if (Collision.equals(CornerList[PreviousCorner]) || Collision.equals(CornerList[NextCorner]))
							{
								Collision = null;
							}

							else
							{
								LineGood = false;
								break;
							}
						}

					}

					if (LineGood)
					{
						boolean TriGood = true;

						for (int j = 0; j < AllCorners.length; j++)
						{
							if (!AllCorners[j].equals(Tri.GetCorner1()) && !AllCorners[j].equals(Tri.GetCorner2()) && !AllCorners[j].equals(Tri.GetCorner3()))
							{
								if (Tri.CollidesWith(AllCorners[j]))
								{
									TriGood = false;
									break;
								}
							}
						}

						if (TriGood)
						{
							Triangles.add(Tri);
							Clone.Cut(Tri);
							break;
						}
					}
				}
			}

			if (Clone.GetCornerCount() == 3)
			{
				Done = true;

				Vector2f[] FinalCorners = Clone.GetCorners();

				Triangles.add(new Triangle(FinalCorners[0], FinalCorners[1], FinalCorners[2]));

				Triangle[] TriangleArray = new Triangle[Triangles.size()];
				TriangleArray = Triangles.toArray(TriangleArray);

				return TriangleArray;
			}
		}

		return null;
	}

	public void RemoveCorner(int Corner)
	{

		int Prev = (Corner - 1) % GetCornerCount();
		int Next = (Corner + 1) % GetCornerCount();

		int OldCornerCount = GetCornerCount();
	
		Vector2f PrevPoint = Corners.ToArray()[Prev];
		Vector2f NextPoint = Corners.ToArray()[Next];
	
		Corners.RemoveVector(Corners.ToArray()[Corner]);
		
		Sides[Prev] = new Segmant(PrevPoint, NextPoint);
		Sides[Corner] = null;
		
		Sides = (Segmant[]) BoltUtils.RemoveNulls(Sides);
		
	}

	public int GetCornerCount()
	{
		return Corners.ToArray().length;
	}

	public float GetInternalAngle(int Point)
	{
		int PreviousCorner = (Point - 1) % GetCornerCount();
		int NextCorner = (Point + 1) % GetCornerCount();

		Vector2f MiddlePoint = GetCorners()[Point];

		Vector2f Point1 = GetCorners()[PreviousCorner];
		Vector2f Point2 = GetCorners()[NextCorner];

		Triangle Tri = new Triangle(Point1, Point2, MiddlePoint);

		float Angle = AngleBetween(Point1, MiddlePoint, Point2);

		if (!CollidesWith(Tri.GetCenter()))
		{
			Angle = 360 - Angle;
		}

		return Angle;
	}

	protected float AngleBetween(Vector2f Point1, Vector2f Intersection, Vector2f Point2)
	{
		Vector2f AB = Intersection.Derive();

		AB.Subtract(Point1);

		Vector2f BC = Point2.Derive();

		BC.Subtract(Intersection);

		float DotProduct = AB.GetDotProduct(BC);

		return (float) Math.toDegrees(Math.acos(DotProduct / (AB.GetMagnitude() * BC.GetMagnitude())));
	}

	public Vector2f GetScale()
	{
		Vector2f BottemRight = new Vector2f(Vector2fUtils.GetMaxX(Corners.ToArray()).GetX(), Vector2fUtils.GetMinY(Corners.ToArray()).GetY());
		Vector2f TopLeft = Position;

		float PolygonWidth = TopLeft.GetXDistanceTo(BottemRight);
		float PolygonHeight = BottemRight.GetYDistanceTo(TopLeft);

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
			Sides[i].Render(new Color(255, 0, 0), RenderingType.Referenced);
		}
	}

	@Override
	public void Render(Color Hue, RenderingType Type)
	{
		for (int i = 0; i < Sides.length; i++)
		{
			Sides[i].Render(Hue, Type);
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
		this.Rotation += Rotation;

		Vector2f[] CornerList = Corners.ToArray();

		for (int i = 0; i < CornerList.length; i++)
		{
			CornerList[i].Rotate(Rotation, Center);
		}
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
