package Random.Noise;

public class PerlinNoise2D extends PerlinNoise
{

	public PerlinNoise2D(long Seed, float GridSample)
	{
		super(Seed, GridSample);
		// TODO Auto-generated constructor stub
	}

	// public float GetPerlinValue(float X, float Y)
	// {
	// int[] GridLoc = GetGridLocation(X, Y);
	//
	// float CellPosX = BoltMath.mod(X, GridSample) / GridSample;
	// float CellPosY = BoltMath.mod(Y, GridSample) / GridSample;
	//
	// float V1 = GetNodeValue(GridLoc[0], GridLoc[1]);
	// float V2 = GetNodeValue(GridLoc[0], GridLoc[1]);
	//
	// return Interpolate(V1, V2, CellPos);
	//
	// }
	//
	// public int[] GetGridLocation(float X, float Y)
	// {
	// return new int[] { (int) (X / GridSample), (int) (X / GridSample) };
	// }
	//
	// public float GetNodeValue(int NodeX, int NodeY)
	// {
	//
	// }

}
