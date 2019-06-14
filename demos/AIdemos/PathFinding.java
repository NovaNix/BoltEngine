package AIdemos;

import java.awt.Color;

import AI.PathFinding.AStar;
import AI.PathFinding.Path;
import IO.InputManager;
import IO.File.EasyLoader;
import Rendering.Window;
import Rendering.Cameras.Camera;
import Rendering.Cameras.SingleFollowCamera;
import Rendering.Handling.Rendering;
import Rendering.Image.Texture;
import Vectors.Vector2f;

public class PathFinding 
{

	static Window Win = new Window("Path Finding");

	static Camera Cam = new Camera();
	
	static Texture Dirt;
	
	// Added for cleaner map
	static boolean t = true;
	static boolean f = false;
	
	static boolean[][] Map = new boolean[][]
	{
		{f, f, f, f, f, f, f},
		{f, t, t, f, t, t, f},
		{t, t, f, f, f, f, f},
		{f, f, f, t, f, t, t},
		{t, t, f, t, f, t, f},
		{t, f, f, t, f, t, f},
		{f, f, f, f, f, f, f},
		{f, t, t, t, t, f, t},
		{f, t, f, f, t, f, t},
		{f, f, f, f, t, f, f}
		
	};
	
	static Path MazePath;
	
	public static void main(String[] args)
	{
		Cam.AddRenderable(() -> RenderGrid());
		Cam.AddRenderable(() -> RenderPath());

		Win.AddCamera(Cam);

		Win.SetVisible(true);

		Dirt = new Texture(EasyLoader.LoadLocalImage("/DemoImages/Dirt.png"));
		
		MazePath = AStar.PathFind(Map, 1, 3, 8, 3, false);
		
		InputManager.Init(Win.GetHandle());
		
		while (!Win.ShouldClose())
		{
			InputManager.Update();
			
			Render();
		}
		
		System.exit(0);

	}

	public static void RenderGrid()
	{
		for (int x = 0; x < Map.length; x++)
		{
			for (int y = 0; y < Map[0].length; y++)
			{
				if (Map[x][y] == t)
				{
					Rendering.RenderRawImage(Dirt, new Vector2f(x * 32, y * 32), new Vector2f(32, 32), 0);
				}
			}
		}
		
		Rendering.RenderRawBox(new Vector2f(32, 96), new Vector2f(64, 128), 2, new Color(255, 0, 0));
		
		Rendering.RenderRawBox(new Vector2f(8 * 32, 96), new Vector2f((8 * 32) + 32, 96 + 32), 2, new Color(0, 255, 0));
	}
	
	public static void RenderPath()
	{
		for (int i = 0; i < MazePath.GetPointCount() - 1; i++)
		{
			Vector2f Point1 = MazePath.GetPoint(i).Derive();
			Vector2f Point2 = MazePath.GetPoint(i + 1).Derive();
			
			Point1.Multiply(new Vector2f(32, 32));
			Point2.Multiply(new Vector2f(32, 32));
			
			Point1.Add(new Vector2f(16, 16));
			Point2.Add(new Vector2f(16, 16));
			
			Rendering.RenderRawLine(Point1, Point2, 1, new Color(0, 255, 0));
		}
	}

	public static void Render()
	{
		Win.Render();
	}

	
}
