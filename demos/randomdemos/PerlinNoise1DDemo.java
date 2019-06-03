package randomdemos;

import java.awt.Color;

import Engine.BoltEngine;
import Engine.Game;
import Random.Noise.PerlinNoise1D;
import Rendering.Renderable;
import Rendering.Rendering;
import Rendering.Window;
import Rendering.Cameras.Camera;
import Rendering.Cameras.SingleFollowCamera;
import Vectors.Vector2f;

public class PerlinNoise1DDemo extends Game
{

	Window Win;
	Camera Cam;

	PerlinNoise1D Noise;

	float[] HeightMap = new float[1000];

	public static void main(String[] args)
	{
		BoltEngine.StartGame(new PerlinNoise1DDemo());
	}

	public PerlinNoise1DDemo()
	{
		super(30);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void StartUp()
	{
		Win = new Window("Perlin Noise Demo");
		Cam = new SingleFollowCamera("POV", new Vector2f(0, 0));

		Win.AddCamera(Cam);

		Win.Show();

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
					Rendering.RenderRawLine(new Vector2f(i, HeightMap[i]), new Vector2f(i, 0), 2, new Color(255, 255, 255));
				}

			}
		});
	}

	@Override
	public void ShutDown()
	{
		// Win.();

	}

	@Override
	protected void Tick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void UpdateInput()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void Render()
	{
		Win.Render();

	}

	@Override
	protected boolean EndGame()
	{
		// TODO Auto-generated method stub
		return Win.ShouldClose();
	}

}