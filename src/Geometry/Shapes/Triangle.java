package Geometry.Shapes;

import Vectors.Vector2f;

public class Triangle extends Polygon
{

	public Triangle(Vector2f Corner1, Vector2f Corner2, Vector2f Corner3)
	{
		super(new Vector2f[] {Corner1, Corner2, Corner3});
	}
	
	public Vector2f GetCorner1()
	{
		return Corners.ToArray()[0];
	}
	
	public Vector2f GetCorner2()
	{
		return Corners.ToArray()[1];
	}
	
	public Vector2f GetCorner3()
	{
		return Corners.ToArray()[2];
	}
	
}
