package Geometry.Shapes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import Engine.BoltMath;
import Geometry.Line;
import Geometry.Ray;
import Geometry.Segment;
import Rendering.Handling.Rendering.RenderingType;
import Storage.LoopedList;
import Utils.BoltUtils;
import Utils.Vector2fUtils;
import Vectors.ReferencedVector2f;
import Vectors.Vector2f;
import Vectors.Vector2fGroup;

@SuppressWarnings("rawtypes")
public class Polygon extends Shape<Polygon>
{

	Vector2fGroup Corners;
	LoopedList<Segment> Sides;

	Circle BoundingBubble;

	float Rotation = 0f;

	public Polygon(Vector2f[] Corners)
	{
		this.Position = new Vector2f(Vector2fUtils.GetMinX(Corners).GetX(), Vector2fUtils.GetMaxY(Corners).GetY());

		this.Corners = new Vector2fGroup(Corners);
		this.Corners.ToRelative(Position);

		Clean();
	}

	public Polygon(Vector2f Position, Vector2f[] Corners)
	{
		this.Position = Position;

		this.Corners = new Vector2fGroup(Corners);
		this.Corners.ToRelative(Position);

		Clean();
	}

	// Removed unnecessary points
	public void Clean()
	{
		boolean Done = false;

		while (!Done)
		{
			Vector2f[] CornerArray = GetCorners();
			float[] Angles = BoltMath.GetAngles(this);

			boolean CornerRemoved = false;

			for (int i = 0; i < GetCornerCount(); i++)
			{
				Vector2f PreviousCorner = CornerArray[BoltMath.mod((i - 1), CornerArray.length)];

				Vector2f CurrentCorner = CornerArray[i];

				if (CurrentCorner == PreviousCorner || Angles[i] == 180)
				{
					RemoveCorner(i);
					CornerRemoved = true;
					break;
				}
			}

			if (!CornerRemoved)
			{
				Done = true;
			}

		}

		Refresh();

	}

	// Regenerates the sides, bounding bubble, etc
	public void Refresh()
	{
		Segment[] Edges = Segment.GenerateSegments(this.Corners.ToArray());

		Sides = new LoopedList<Segment>(Arrays.asList(Edges));

		Vector2f AVCenter = Vector2fUtils.GetAverage(this.Corners.ToArray());

		this.Center = new ReferencedVector2f(Position);
		this.Center.SetPosition(AVCenter);

		this.Radius = Center.GetDistanceTo(Vector2fUtils.GetFurthestPointFrom(Center, this.Corners.ToArray()));

		this.BoundingBubble = new Circle(Center, Radius);
	}

	// Returns the triangulation of this polygon
	public Triangle[] ExtractTriangles()
	{
		Polygon Clone = Clone();

		ArrayList<Triangle> Triangles = new ArrayList<Triangle>();

		boolean Done = false;

		LoopedList<Vector2f> AllCorners = Corners.GetVectorList();

		while (!Done)
		{
			LoopedList<Vector2f> CornerList = Clone.GetCornerList();

			float[] Angles = BoltMath.GetAngles(Clone);

			for (int i = 0; i < CornerList.size(); i++)
			{
				if (!(Angles[i] >= 180))
				{

					Vector2f PreviousCorner = CornerList.get(i - 1);
					Vector2f MidCorner = CornerList.get(i);
					Vector2f NextCorner = CornerList.get(i + 1);

					Segment TriLine = new Segment(PreviousCorner, NextCorner);

					Triangle Tri = new Triangle(PreviousCorner, MidCorner, NextCorner);

					boolean LineGood = true;

					for (int j = 0; j < Clone.GetSides().size(); j++)
					{
						Vector2f Collision = TriLine.GetIntersectionWith(Clone.GetSides().get(j));

						if (Collision != null)
						{
							if (Collision.equals(PreviousCorner) || Collision.equals(NextCorner))
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

						for (int j = 0; j < AllCorners.size(); j++)
						{
							Vector2f SelectedCorner = AllCorners.get(j);

							if (!SelectedCorner.equals(Tri.GetCorner1()) && !SelectedCorner.equals(Tri.GetCorner2()) && !SelectedCorner.equals(Tri.GetCorner3()))
							{
								if (Tri.CollidesWith(SelectedCorner))
								{
									TriGood = false;
									break;
								}
							}
						}

						if (TriGood)
						{
							Triangles.add(Tri);
							Clone.RemoveCorner(i);
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

	// Removed the specified corner of this polygon
	public void RemoveCorner(int Corner)
	{

		int Prev = Corner - 1;
		int Next = Corner + 1;

		Vector2f PrevPoint = Corners.GetVector(Prev);
		Vector2f NextPoint = Corners.GetVector(Next);

		Corners.RemoveVector(Corners.GetVector(Corner));

		Sides.set(Prev, new Segment(PrevPoint, NextPoint));
		Sides.remove(Corner);
	}

	// Returns the number of corners
	public int GetCornerCount()
	{
		return Corners.ToArray().length;
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

		return new Vector2f(Math.abs(PolygonWidth), Math.abs(PolygonHeight));
	}

	@Override
	public Vector2f GetFollowingPosition()
	{
		return Center;
	}

	@Override
	public void Render()
	{
		for (int i = 0; i < Sides.size(); i++)
		{
			Sides.get(i).Render(new Color(255, 255, 255), RenderingType.Referenced);
		}
	}

	@Override
	public void Render(Color Hue, RenderingType Type)
	{
		for (int i = 0; i < Sides.size(); i++)
		{
			Sides.get(i).Render(Hue, Type);
		}
	}

	@Override
	public Vector2f[] GetCollisionPointsWith(Line Collision)
	{
		Vector2f[] Collisions = new Vector2f[Sides.size()];

		for (int i = 0; i < Sides.size(); i++)
		{
			Collisions[i] = Sides.get(i).GetIntersectionWith(Collision);
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
	public boolean CollidesWith(Line Collision)
	{
		if (CollidesWith(Collision.GetPointOnLine()))
		{
			return true;
		}

		else
		{
			return Collision.GetIntersectionsWith(Sides.toArray(new Segment[Sides.size()])) != null;
		}
	}

	@Override
	public boolean CollidesWith(Shape Collision)
	{

		if (Collision instanceof Polygon)
		{
			if (Collision instanceof Rectangle && this instanceof Rectangle)
			{
				return ((Rectangle) this).CollidesWith((Rectangle) Collision);
			}

			return CollidesWith((Polygon) Collision);
		}

		else
		{
			if (CollidesWith(Collision.GetCenter()) || Collision.CollidesWith(Center))
			{
				return true;
			}

			for (int i = 0; i < Sides.size(); i++)
			{
				if (Collision.CollidesWith(Sides.get(i)))
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

			for (int i = 0; i < Sides.size(); i++)
			{
				if (Collision.CollidesWith(Sides.get(i)))
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

		Vector2f[] RayCollisions = new Vector2f[Sides.size()];

		for (int i = 0; i < Sides.size(); i++)
		{
			RayCollisions[i] = PointRay.GetIntersectionWith(Sides.get(i));
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

	public LoopedList<Vector2f> GetCornerList()
	{
		return Corners.GetVectorList();
	}

	public LoopedList<Segment> GetSides()
	{
		return Sides;
	}

	@Override
	public void SetScale(Vector2f Scale)
	{
		Vector2f CurrentScale = GetScale();
		Vector2f MultiplyBy = new Vector2f(Scale.GetX() - CurrentScale.GetX(), Scale.GetY() - CurrentScale.GetY());

		Corners.Multiply(MultiplyBy);

		Refresh();

	}

	public void Rotate(float Rotation)
	{
		this.Rotation += Rotation;

		Vector2f[] CornerList = Corners.ToArray();

		for (int i = 0; i < CornerList.length; i++)
		{
			CornerList[i].Rotate(Rotation, Center);
		}

		Refresh();
	}

	@Override
	public void SetCenter(Vector2f Center)
	{
		Vector2f Translation = this.Center.Derive();
		Translation.Subtract(Center);
		Position.Add(Translation);
	}

	@Override
	public Polygon Clone()
	{
		return new Polygon(Position.Derive(), Corners.ToArray());
	}

}
