package Random.Noise;

import java.util.Random;

public abstract class PerlinNoise
{

	protected Random RandomGen;

	protected float GridSample;

	public PerlinNoise(long Seed, float GridSample)
	{
		RandomGen = new Random(Seed);
		this.GridSample = GridSample;
	}

	public float Interpolate(float X, float Y, float W)
	{
		// Code for Cos Interpolation borrowed from tutorial
		double ft = W * Math.PI;
		double f = (1 - Math.cos(ft)) * 0.5;
		return (float) (X * (1 - f) + Y * f);
	}

}
