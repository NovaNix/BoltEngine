package Physics.Forces;

import java.util.ArrayList;

import Vectors.Vector2f;

public class Force
{
	public Vector2f Direction;

	public Force(Vector2f Direction)
	{
		this.Direction = Direction;
		
		if (Direction == null)
		{
			System.out.println("Direction on force is null!");
		}
	}
	
	public static Force CalculateNetForce(Force[] Forces)
	{
		Vector2f NetForce = new Vector2f(0, 0);
		
		for (int i = 0; i < Forces.length; i++)
		{
			NetForce.Add(Forces[i].GetDirection());
		}
		
		return new Force(NetForce);
	}
	
	public static Force CalculateNetForce(ArrayList<Force> Forces)
	{
		Vector2f NetForce = new Vector2f(0, 0);
		
		for (int i = 0; i < Forces.size(); i++)
		{
			NetForce.Add(Forces.get(i).GetDirection());
		}
		
		return new Force(NetForce);
	}
	
	public Vector2f GetDirection()
	{
		return Direction;
	}

	public Vector2f GetInverse()
	{
		return Direction.Invert();
	}
}
