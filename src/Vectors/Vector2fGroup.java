package Vectors;

import java.util.ArrayList;

public class Vector2fGroup
{

	ArrayList<Vector2f> Vectors = new ArrayList<Vector2f>();

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

	public void AddVector(Vector2f Vector)
	{
		Vectors.add(Vector);
	}

	public void RemoveVector(Vector2f Vector)
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
