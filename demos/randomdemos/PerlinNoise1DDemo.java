package randomdemos;

import java.awt.Color;

import Engine.BoltEngine;
import Engine.Game;
import Engine.Debugging.TechDemo;
import IO.InputManager;
import Random.Noise.PerlinNoise1D;
import Rendering.Window;
import Rendering.Cameras.Camera;
import Rendering.Cameras.SingleFollowCamera;
import Rendering.Handling.Renderable;
import Rendering.Handling.Rendering2D;
import Vectors.Vector2f;

public class PerlinNoise1DDemo extends TechDemo
{

	PerlinNoise1D Noise;

	float[] HeightMap = new float[1000];

	public static void main(String[] args)
	{
		BoltEngine.StartGame(new PerlinNoise1DDemo());
	}

	public PerlinNoise1DDemo()
	{
		super("Perlin Noise Demo", 30);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Boot()
	{

		Noise = new PerlinNoise1D(2553, 100);

		for (int i = 0; i < HeightMap.length; i++)
		{
			HeightMap[i] = Noise.GetPerlinValue(i) * 700;
		}

		Cam.AddRenderable(new Renderable()
		{

			@Override
			public void Render()
			{
				for (int i = 0; i < HeightMap.length - 1; i++)
				{
					Rendering2D.RenderRawLine(new Vector2f(i, HeightMap[i]), new Vector2f(i, 0), 2, new Color(255, 255, 255));
				}

			}
		});
	}


	@Override
	protected void Tick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void UpdateInput()
	{
		InputManager.Update();
	}

}
