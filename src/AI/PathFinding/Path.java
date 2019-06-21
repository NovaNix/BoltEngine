package AI.PathFinding;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import Geometry.Segment;
import Rendering.Handling.Renderable;
import Rendering.Handling.Rendering;
import Vectors.Vector2f;

public class Path implements Renderable
{

	ArrayList<Vector2f> PathPoints;

	public Path()
	{
		PathPoints = new ArrayList<Vector2f>();
	}

	public Path(Vector2f[] Points)
	{
		PathPoints = new ArrayList<Vector2f>(Arrays.asList(Points));

		Clean();
	}

	public void Clean()
	{
		boolean Done = false;

		while (!Done)
		{
			boolean Changed = false;

			for (int i = 0; i < PathPoints.size() - 2; i++)
			{
				Segment Connection = new Segment(PathPoints.get(i), PathPoints.get(i + 2));

				if (Connection.PointOnLine(PathPoints.get(i + 1)))
				{
					PathPoints.remove(i + 1);

					Changed = true;

					break;
				}
			}

			if (!Changed)
			{
				Done = true;
			}
		}
	}

	public void AddPoint(Vector2f Point)
	{
		PathPoints.add(Point);

		Clean();
	}

	public void RemovePoint(int Point)
	{
		PathPoints.remove(Point);

		Clean();
	}

	public void RemovePoint(Vector2f Point)
	{
		PathPoints.remove(Point);

		Clean();
	}

	public Vector2f GetPoint(int Position)
	{
		return PathPoints.get(Position);
	}

	public int GetPointCount()
	{
		return PathPoints.size();
	}

	public int GetClosestPoint(Vector2f Node)
	{
		int Closest = 0;

		Vector2f ClosestNode = PathPoints.get(0);

		for (int i = 0; i < PathPoints.size(); i++)
		{
			if (Node.GetDistanceTo(ClosestNode) < Node.GetDistanceTo(PathPoints.get(i)))
			{
				ClosestNode = PathPoints.get(i);
				Closest = i;
			}
		}

		return Closest;
	}

	@Override
	public void Render()
	{
		for (int i = 0; i < PathPoints.size() - 1; i++)
		{
			Rendering.RenderReferencedLine(PathPoints.get(i), PathPoints.get(i + 1), 2, Color.WHITE);
		}

	}

	public Path GetFlipped()
	{
		Path Flipped = new Path();

		for (int i = PathPoints.size() - 1; i > 0; i--)
		{
			Flipped.AddPoint(PathPoints.get(i));
		}

		return Flipped;
	}

}
