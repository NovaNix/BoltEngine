package Random.Noise;

import java.util.HashMap;

import Engine.BoltMath;

public class PerlinNoise1D extends PerlinNoise
{

	HashMap<Integer, Float> NodeValues = new HashMap<Integer, Float>();

	public PerlinNoise1D(long Seed, float GridSample)
	{
		super(Seed, GridSample);
		// TODO Auto-generated constructor stub
	}

	public float GetPerlinValue(float X)
	{
		int GridLoc = GetGridLocation(X);

		float CellPos = BoltMath.mod(X, GridSample) / GridSample;

		float V1 = GetNodeValue(GridLoc);
		float V2 = GetNodeValue(GridLoc + 1);

		System.out.println("GridLoc: " + GridLoc);

		System.out.println("Cell Pos" + CellPos);

		System.out.println("V1: " + V1);

		System.out.println("V2: " + V2);

		System.out.println("X: " + X);

		return Interpolate(V1, V2, CellPos);

	}

	public int GetGridLocation(float X)
	{
		return (int) (X / GridSample);
	}

	public float GetNodeValue(int Node)
	{
		if (!NodeValues.containsKey(Node))
		{
			NodeValues.put(Node, RandomGen.nextInt(100) / 100f);
		}

		return NodeValues.get(Node);
	}

}
