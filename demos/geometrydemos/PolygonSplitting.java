package geometrydemos;

import java.awt.image.BufferedImage;

import Geometry.Shapes.Polygon;
import Geometry.Shapes.Rectangle;
import Geometry.Shapes.Triangle;
import Rendering.Rendering;
import Rendering.Window;
import Rendering.Cameras.Camera;
import Rendering.Cameras.SingleFollowCamera;
import Rendering.Exceptions.ExcessCamerasException;
import Vectors.Vector2f;

public class PolygonSplitting
{

	static Window Win = new Window("Polygon Splitting");

	static Camera Cam = new SingleFollowCamera("Default Perspective", new Vector2f(0, 0));

//	static Polygon Poly = new Polygon(new Vector2f[] { new Vector2f(30, 20), new Vector2f(20, 90), new Vector2f(40, 130), new Vector2f(50, 70), new Vector2f(70, 120), new Vector2f(90, 140), new Vector2f(110, 110), new Vector2f(80, 60), new Vector2f(90, 40), new Vector2f(110, 50), new Vector2f(150, 60), new Vector2f(130, 20) });

	static Rectangle Poly = new Rectangle(new Vector2f(50, 50), new Vector2f(500, 200));

	static Triangle[] Split;

	public static void main(String[] args)
	{
		BufferedImage Black = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);

		Black.setRGB(0, 0, 0);

		Cam.AddRenderable(() -> Rendering.RenderRawImage(Black, new Vector2f(0, 0), new Vector2f(1000, 750), 0));

		Cam.AddRenderable(() -> RenderSplit());

		// Cam.AddRenderable(() -> Poly.Render());
		try
		{
			Win.GetScreen().AddCamera(Cam);
		} catch (ExcessCamerasException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Win.setVisible(true);

		Split = Poly.ExtractTriangles();

		while (true)
		{
			Render();
		}

	}

	public static void RenderSplit()
	{
		for (int i = 0; i < Split.length; i++)
		{
			Split[i].Render();
		}
	}

	public static void Render()
	{
		Win.GetScreen().Render();
	}

}
