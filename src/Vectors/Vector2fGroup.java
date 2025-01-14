package Vectors;

import java.util.ArrayList;
import java.util.Arrays;

import Storage.LoopedList;

public class Vector2fGroup
{

	private LoopedList<Vector2f> Vectors = new LoopedList<Vector2f>();

	public Vector2fGroup()
	{

	}

	public Vector2fGroup(Vector2f[] Vectors)
	{
		for (int i = 0; i < Vectors.length; i++)
		{
			this.Vectors.add(Vectors[i]);
		}
	}

	public Vector2f GetVector(int Vector)
	{
		return Vectors.get(Vector);
	}

	public void AddVector(Vector2f Vector)
	{
		Vectors.add(Vector);
	}

	public void ReplaceVector(Vector2f Replace, Vector2f Replacement)
	{
		Vectors.set(GetVectorPosition(Replace), Replacement);

	}

	public void ReplaceVector(Vector2f Replace, Vector2f[] Replacement)
	{
		ArrayList<Vector2f> ReplacementList = new ArrayList<Vector2f>(Arrays.asList(Replacement));
		int Index = GetVectorPosition(Replace);

		Vectors.remove(Index);
		Vectors.addAll(Index, ReplacementList);
	}

	public void RemoveVector(Vector2f Vector)
	{
		Vectors.remove(Vector);
	}

	public void RemoveVector(int Vector)
	{
		Vectors.remove(Vector);
	}

	public int GetVectorPosition(Vector2f Vector)
	{
		return Vectors.indexOf(Vector);
	}

	public void Add(Vector2f Vector)
	{
		for (int i = 0; i < Vectors.size(); i++)
		{
			Vectors.get(i).Add(Vector);
		}
	}

	public void Subtract(Vector2f Vector)
	{
		for (int i = 0; i < Vectors.size(); i++)
		{
			Vectors.get(i).Subtract(Vector);
		}
	}

	public void Multiply(Vector2f Vector)
	{
		for (int i = 0; i < Vectors.size(); i++)
		{
			Vectors.get(i).Multiply(Vector);
		}
	}

	public void Divide(Vector2f Vector)
	{
		for (int i = 0; i < Vectors.size(); i++)
		{
			Vectors.get(i).Divide(Vector);
		}
	}

	public void ToRelative(Vector2f ReferencePoint)
	{
		for (int i = 0; i < Vectors.size(); i++)
		{
			Vector2f Referenced = new ReferencedVector2f(ReferencePoint);
			Referenced.SetPosition(Vectors.get(i));
			Vectors.set(i, Referenced);
		}
	}

	public Vector2f[] ToArray()
	{
		Vector2f[] VectorArray = new Vector2f[Vectors.size()];
		VectorArray = Vectors.toArray(VectorArray);

		return VectorArray;
	}

	public LoopedList<Vector2f> GetVectorList()
	{
		return Vectors;
	}

	public Vector2fGroup Clone()
	{
		Vector2fGroup Group = new Vector2fGroup();

		for (int i = 0; i < Vectors.size(); i++)
		{
			Group.Add(Vectors.get(i).Derive());
		}

		return Group;
	}

}
