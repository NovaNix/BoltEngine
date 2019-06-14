package geometrydemos;

import java.awt.Color;
import Engine.BoltEngine;
import Engine.Debugging.TechDemo;
import Geometry.Shapes.Polygon;
import Geometry.Shapes.Triangle;
import IO.InputManager;
import Rendering.Window;
import Rendering.Cameras.Camera;
import Rendering.Cameras.SingleFollowCamera;
import Rendering.Handling.Rendering.RenderingType;
import Vectors.Vector2f;

public class PolygonSplitting extends TechDemo
{

	public PolygonSplitting() 
	{
		super("Polygon Splitting", 30);
	}

	 static Polygon Poly = new Polygon(new Vector2f[] { new Vector2f(30, 20), new
	 Vector2f(20, 90), new Vector2f(40, 130), new Vector2f(50, 70), new
	 Vector2f(70, 120), new Vector2f(90, 140), new Vector2f(110, 110), new
	 Vector2f(80, 60), new Vector2f(90, 40), new Vector2f(110, 50), new
	 Vector2f(150, 60), new Vector2f(130, 20) });

	static Triangle[] Split;

	public static void main(String[] args)
	{
		BoltEngine.StartGame(new PolygonSplitting());
	}

	public static void RenderSplit()
	{
		for (int i = 0; i < Split.length; i++)
		{
			Split[i].Render(new Color(255, 255, 255), RenderingType.Referenced);
		}
	}

	@Override
	public void Boot() 
	{
		Cam.AddRenderable(() -> RenderSplit());
		
		Split = Poly.ExtractTriangles();
	}

	@Override
	protected void Tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void UpdateInput() 
	{
		InputManager.Update();
	}

}
