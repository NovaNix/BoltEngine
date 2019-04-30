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
		float[] v = Vector2fUtils.GetCoordList(ToCorners.ToArray());
		
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

		System.out.println("The corner was not on the polygon!");
		System.out.println("The corners were " + P1 + ", " + P2 + ", " + P3);

		return -1;
	}

	public Triangle[] ExtractTriangles()
	{
		Polygon Clone = Copy();

		ArrayList<Triangle> Triangles = new ArrayList<Triangle>();

		ArrayList<Triangle> NewTriangles = new ArrayList<Triangle>();

		boolean Done = false;

		System.out.println("Extracting Triangles from Polygon");

		int Iteration = 0;

		while (!Done)
		{

			System.out.println("Iteration " + Iteration);

			Iteration++;

			ArrayList<Integer> ConcavePoints = new ArrayList<Integer>();

			Vector2f[] CornerList = Clone.GetCorners();

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
						PreviousCorner = CornerList.length - 1;
						NextCorner = 1;
					}

					else if (i == CornerList.length - 1)
					{
						PreviousCorner = i - 1;
						NextCorner = 0;
					}

					else
					{
						PreviousCorner = i - 1;
						NextCorner = i + 1;
					}

					Segmant TriLine = new Segmant(CornerList[PreviousCorner], CornerList[NextCorner]);

					boolean LineGood = true;

					for (int j = 0; j < Sides.length; j++)
					{
						Vector2f Collision = TriLine.GetIntersectionWith(Sides[j]);

						if (Collision != null)
						{
							if (Collision.equals(CornerList[PreviousCorner]) || Collision.equals(CornerList[NextCorner]))
							{
								Collision = null;
							}

							else
							{
								LineGood = false;
								System.out.println("Line is not good");
							}
						}
					}

					if (LineGood)
					{
						Triangle Tri = new Triangle(CornerList[PreviousCorner], CornerList[i], CornerList[NextCorner]);
						System.out.println("Triangle made from " + PreviousCorner + ", " + i + ", " + NextCorner);
						Triangles.add(Tri);
						NewTriangles.add(Tri);
						UsedList[PreviousCorner] = true;
						UsedList[i] = true;
						UsedList[NextCorner] = true;
					}
				}
			}

			for (int i = 0; i < NewTriangles.size(); i++)
			{
				if (Clone.GetCornerCount() == 3)
				{
					Done = true;

					Triangle[] TriangleArray = new Triangle[Triangles.size()];
					TriangleArray = Triangles.toArray(TriangleArray);

					System.out.println("Finished extracting triangles! We had " + TriangleArray.length + " triangles!");

					return TriangleArray;
				}

				Clone.Cut(NewTriangles.get(i));
			}

			NewTriangles.clear();

			if (Clone.GetCornerCount() == 3)
			{
				Done = true;

				Triangle[] TriangleArray = new Triangle[Triangles.size()];
				TriangleArray = Triangles.toArray(TriangleArray);

				System.out.println("Finished extracting triangles! We had " + TriangleArray.length + " triangles!");

				return TriangleArray;
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
		// AB = new Vector2f(-AB.GetY(), AB.GetX());
		Vector2f BC = CornerArray[NextCorner].Derive();
		BC.Subtract(CornerArray[Corner]);
		//
		// float DotProduct = (AB.GetX() * BC.GetX()) + (AB.GetY() * BC.GetY());
		// float Determinant = (AB.GetX() * BC.GetY()) + (AB.GetY() * BC.GetX());
		//
		// float Magnitudes =
		// CornerArray[Corner].GetDistanceTo(CornerArray[PreviousCorner]) *
		// CornerArray[Corner].GetDistanceTo(CornerArray[NextCorner]);

		// float Angle = (float) Math.toDegrees(Math.atan2(Determinant, DotProduct));
		//
		// if (Angle < 0)
		// {
		// Angle = 360 + Angle;
		// }

		Vector2f MidPoint = CornerArray[Corner];
		Vector2f Point1 = CornerArray[PreviousCorner];
		Vector2f Point2 = CornerArray[NextCorner];

		double result = Math.atan2(Point2.GetY() - MidPoint.GetY(), Point2.GetX() - MidPoint.GetX()) - Math.atan2(Point1.GetY() - MidPoint.GetY(), Point1.GetX() - MidPoint.GetX());

		result = Math.toDegrees(result);

		if (result < 0)
		{
			result = 360 + result;
		}

		// System.out.println("Result = " + result);
		//
		// System.out.println("Point " + (Corner + 1));
		//
		// System.out.println("Magnitude was " + Magnitudes);
		//
		// System.out.println("Angle was " + Angle);

		return result > 180;
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
