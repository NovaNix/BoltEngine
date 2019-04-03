package Utils;

import java.util.ArrayList;

import Vectors.Vector2f;

public class Vector2fUtils
{

	public static Vector2f GetFurthestNorth(Vector2f[] Points)
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
	
	public static Vector2f GetFurthestSouth(Vector2f[] Points)
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
	
	public static Vector2f GetFurthestEast(Vector2f[] Points)
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
	
	public static Vector2f GetFurthestWest(Vector2f[] Points)
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
		float AverageX = 0;
		float AverageY = 0;
		
		for (int i = 0; i < Points.length; i++)
		{
			AverageX += Points[i].GetX();
			AverageY += Points[i].GetY();
		}
		
		AverageX /= Points.length;
		AverageY /= Points.length;
		
		return new Vector2f(AverageX, AverageY);
		
	}
	
	public static Vector2f GetCenter(Vector2f[] Points)
	{
		Vector2f Northern = GetFurthestNorth(Points);
		Vector2f Southern = GetFurthestSouth(Points);
		Vector2f Western = GetFurthestWest(Points);
		Vector2f Eastern = GetFurthestEast(Points);
		
		float XDistance = Western.GetXDistanceTo(Eastern)/2;
		float YDistance = Southern.GetYDistanceTo(Northern)/2;
		
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
	
	 
}