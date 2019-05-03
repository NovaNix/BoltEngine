package Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Geometry.Line;
import Vectors.Vector;
import Vectors.Vector2f;

public class BoltUtils
{

	public static Vector2f[] RemoveNulls(Vector2f[] ToClear)
	{
		return RemoveNulls(ToClear, new Vector2f[CountNonNulls(ToClear)]);
	}

	public static Line[] RemoveNulls(Line[] ToClear)
	{
		return RemoveNulls(ToClear, new Line[CountNonNulls(ToClear)]);
	}

	// Returns the number of null values in List
	public static <H> int CountNulls(H[] List)
	{
		int Nulls = 0;
		
		for (int i = 0; i < List.length; i++)
		{
			if (List[i] == null)
			{
				Nulls++;	
			}
		}
		
		return Nulls;
		
	}
	
	// Returns the number of non-null values in List
	public static <H> int CountNonNulls(H[] List)
	{
		int NonNulls = 0;
		
		for (int i = 0; i < List.length; i++)
		{
			if (List[i] != null)
			{
				NonNulls++;	
			}
		}
		
		return NonNulls;
	}
	
	// Moves all non-null values in List into Container
	public static <H> H[] RemoveNulls(H[] List, H[] Container)
	{
		int Slot = 0;
		
		for (int i = 0; i < List.length; i++)
		{
			if (List[i] != null)
			{
				Container[Slot] = List[i];
				Slot++;
			}
		}
		
		return Container;
	}
	
	public static double Square(double Number)
	{
		return Math.pow(Number, 2);
	}

}
