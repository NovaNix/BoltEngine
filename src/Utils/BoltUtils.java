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
		int NonNulls = 0;

		for (int i = 0; i < ToClear.length; i++)
		{
			if (ToClear[i] != null)
			{
				NonNulls++;
			}
		}

		if (NonNulls != 0)
		{
			Vector2f[] NonNullArray = new Vector2f[NonNulls];
			int Added = 0;
			for (int i = 0; i < ToClear.length; i++)
			{
				if (ToClear[i] != null)
				{
					NonNullArray[Added] = ToClear[i];
					Added++;
				}
			}
			return NonNullArray;
		}

		else
		{
			return null;
		}
	}

	public static Line[] RemoveNulls(Line[] ToClear)
	{
		int NonNulls = 0;

		for (int i = 0; i < ToClear.length; i++)
		{
			if (ToClear[i] != null)
			{
				NonNulls++;
			}
		}

		if (NonNulls != 0)
		{
			Line[] NonNullArray = new Line[NonNulls];
			int Added = 0;
			for (int i = 0; i < ToClear.length; i++)
			{
				if (ToClear[i] != null)
				{
					NonNullArray[Added] = ToClear[i];
					Added++;
				}
			}
			return NonNullArray;
		}

		else
		{
			return null;
		}
	}

	public static double Square(double Number)
	{
		return Math.pow(Number, 2);
	}

}
