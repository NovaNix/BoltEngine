package opengldemos;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetError;

import org.lwjgl.opengl.GL;

import IO.EasyLoader;
import Rendering.OpenGLTest.Camera;
import Rendering.OpenGLTest.Rendering;
import Rendering.OpenGLTest.Texture;
import Rendering.OpenGLTest.Window;
import TimeKeeping.TickRegulator;
import Vectors.Vector2f;

public class BasicRender
{

	static Window Win = new Window("Basic Render");

	static Camera Cam = new Camera("Default Perspective", new Vector2f(0, 0));

	static Texture DirtTexture = new Texture(EasyLoader.LoadLocalImage("/DemoImages/Austin.png"));

	static float Rotation = 0;

	public static void main(String[] args)
	{

		GL.createCapabilities();

		glEnable(GL_TEXTURE_2D);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// glDisable(GL_CULL_FACE);

		glfwSwapInterval(1);

		Cam.AddRenderable(() -> Rendering.RenderRawImage(DirtTexture, new Vector2f(50, 50), new Vector2f(500, 500), Rotation));

		Win.AddCamera(Cam);

		Win.SetVisable(true);

		TickRegulator Rotator = new TickRegulator(30);

		while (!Win.ShouldClose())
		{
			Rotator.LoopUpdate();

			Render();

			if (Rotator.TickTime())
			{
				Rotation += 0.5;
			}
		}

		Win.Destroy();
		System.exit(1);

	}

	public static void Render()
	{
		glfwPollEvents();

		Win.Update();

		Win.Render();

		System.out.println(glGetError());
	}

}
