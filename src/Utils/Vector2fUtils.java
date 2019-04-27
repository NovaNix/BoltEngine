package Utils;

import java.util.ArrayList;

import Vectors.Vector2f;

public class Vector2fUtils
{

	public static Vector2f GetMaxY(Vector2f[] Points)
	{
		Vector2f Furthest = Points[0];

		for (int i = 1; i < Points.length; i++)
		{
			if (Points[i].GetY() < Furthest.GetY())
			{
				Furthest = Points[i];
			}
		}

		return Furthest;
	}

	public static Vector2f GetMinY(Vector2f[] Points)
	{
		Vector2f Furthest = Points[0];

		for (int i = 1; i < Points.length; i++)
		{
			if (Points[i].GetY() > Furthest.GetY())
			{
				Furthest = Points[i];
			}
		}

		return Furthest;
	}

	public static Vector2f GetMaxX(Vector2f[] Points)
	{
		Vector2f Furthest = Points[0];

		for (int i = 1; i < Points.length; i++)
		{
			if (Points[i].GetX() > Furthest.GetX())
			{
				Furthest = Points[i];
			}
		}

		return Furthest;
	}

	public static Vector2f GetMinX(Vector2f[] Points)
	{
		Vector2f Furthest = Points[0];

		for (int i = 1; i < Points.length; i++)
		{
			if (Points[i].GetX() < Furthest.GetX())
			{
				Furthest = Points[i];
			}
		}

		return Furthest;
	}

	public static Vector2f GetAverage(Vector2f[] Points)
	{
		Vector2f Average = new Vector2f();
		
		for (int i = 0; i < Points.length; i++)
		{
			Average.Add(Points[i]);
		}

		Average.Divide(new Vector2f(Points.length, Points.length));

		return Average;

	}

	public static Vector2f GetCenter(Vector2f[] Points)
	{
		Vector2f Northern = GetMaxY(Points);
		Vector2f Southern = GetMinY(Points);
		Vector2f Western = GetMinX(Points);
		Vector2f Eastern = GetMaxX(Points);

		float XDistance = Western.GetXDistanceTo(Eastern) / 2;
		float YDistance = Southern.GetYDistanceTo(Northern) / 2;

		return new Vector2f(Northern.GetX() + XDistance, Western.GetY() + YDistance);
	}

	public static Vector2f GetClosestPointTo(Vector2f Point, Vector2f[] Points)
	{

		ArrayList<Vector2f> NonNullPoints = new ArrayList<Vector2f>();

		for (int i = 0; i < Points.length; i++)
		{
			if (Points[i] != null)
			{
				NonNullPoints.add(Points[i]);
			}
		}

		if (NonNullPoints.size() == 0)
		{
			return null;
		}

		else
		{

			Vector2f ClosestPoint = NonNullPoints.get(0);

			for (int i = 0; i < NonNullPoints.size(); i++)
			{
				if (Point.GetDistanceTo(ClosestPoint) > Point.GetDistanceTo(NonNullPoints.get(i)))
				{
					ClosestPoint = NonNullPoints.get(i);
				}
			}

			return ClosestPoint;
		}
	}

	public static Vector2f GetFurthestPointFrom(Vector2f Point, Vector2f[] Points)
	{

		ArrayList<Vector2f> NonNullPoints = new ArrayList<Vector2f>();

		for (int i = 0; i < Points.length; i++)
		{
			if (Points[i] != null)
			{
				NonNullPoints.add(Points[i]);
			}
		}

		if (NonNullPoints.size() == 0)
		{
			return null;
		}

		else
		{

			Vector2f FurthestPoint = NonNullPoints.get(0);

			for (int i = 0; i < NonNullPoints.size(); i++)
			{
				if (Point.GetDistanceTo(FurthestPoint) < Point.GetDistanceTo(NonNullPoints.get(i)))
				{
					FurthestPoint = NonNullPoints.get(i);
				}
			}

			return FurthestPoint;
		}
	}

	public static float[] GetCoordList(Vector2f[] Vectors)
	{
		float[] Coords = new float[Vectors.length * 2];

		for (int i = 0, j = 0; i < Vectors.length; i++)
		{
			Coords[j] = Vectors[i].GetX();
			j++;
			Coords[j] = Vectors[i].GetY();
			j++;
		}

		return Coords;
	}

}
