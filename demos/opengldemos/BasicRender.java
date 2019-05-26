package opengldemos;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL43.GL_DEBUG_OUTPUT;
import static org.lwjgl.opengl.GL43.GL_DEBUG_SEVERITY_HIGH;
import static org.lwjgl.opengl.GL43.GL_DEBUG_SEVERITY_LOW;
import static org.lwjgl.opengl.GL43.GL_DEBUG_SEVERITY_MEDIUM;
import static org.lwjgl.opengl.GL43.GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR;
import static org.lwjgl.opengl.GL43.GL_DEBUG_TYPE_ERROR;
import static org.lwjgl.opengl.GL43.GL_DEBUG_TYPE_OTHER;
import static org.lwjgl.opengl.GL43.GL_DEBUG_TYPE_PERFORMANCE;
import static org.lwjgl.opengl.GL43.GL_DEBUG_TYPE_PORTABILITY;
import static org.lwjgl.opengl.GL43.GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR;
import static org.lwjgl.opengl.GL43.glDebugMessageCallback;

import java.awt.Color;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLDebugMessageCallbackI;

import IO.EasyLoader;
import Rendering.Rendering;
import Rendering.Window;
import Rendering.Cameras.Camera;
import Rendering.Image.Texture;
import TimeKeeping.TickRegulator;
import Vectors.Vector2f;

public class BasicRender implements GLDebugMessageCallbackI
{

	static Window Win = new Window("Basic Render");

	static Camera Cam = new Camera("Default Perspective", new Vector2f(0, 0));

	static Texture DirtTexture;

	static float Rotation = 0;

	public static void main(String[] args)
	{

		GL.createCapabilities();

		DirtTexture = new Texture(EasyLoader.LoadLocalImage("/DemoImages/Dirt.png"));

		glEnable(GL_TEXTURE_2D);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glEnable(GL_DEBUG_OUTPUT);

		glDebugMessageCallback(new BasicRender(), 0);

		glDisable(GL_CULL_FACE);

		// glfwSwapInterval(1);

		Cam.AddRenderable(() -> Rendering.RenderRawImage(DirtTexture, new Vector2f(50, 50), new Vector2f(500, 500), Rotation));

		Cam.AddRenderable(() -> Rendering.RenderRawOval(new Vector2f(700, 400), new Vector2f(250, 200)));

		Cam.AddRenderable(() -> Rendering.RenderRawBox(new Vector2f(0, 0), new Vector2f(1280, 780), 10, new Color(255, 0, 100)));

		Cam.AddRenderable(() -> Rendering.RenderRawLine(new Vector2f(0, 0), new Vector2f(1280, 780), 10, new Color(255, 255, 100)));

		Win.AddCamera(Cam);

		Win.SetVisible(true);

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

	@Override
	public void invoke(int source, int type, int id, int severity, int length, long message, long userParam)
	{
		if (type != GL_DEBUG_TYPE_ERROR)
		{
			return;
		}

		System.out.println("message: " + message);
		System.out.print("type: ");
		switch (type)
		{
			case GL_DEBUG_TYPE_ERROR:
				System.out.println("ERROR");
				break;
			case GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR:
				System.out.println("DEPRECATED_BEHAVIOR");
				break;
			case GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR:
				System.out.println("UNDEFINED_BEHAVIOR");
				break;
			case GL_DEBUG_TYPE_PORTABILITY:
				System.out.println("PORTABILITY");
				break;
			case GL_DEBUG_TYPE_PERFORMANCE:
				System.out.println("PERFORMANCE");
				break;
			case GL_DEBUG_TYPE_OTHER:
				System.out.println("OTHER");
				break;
		}

		System.out.println("id: " + id);
		System.out.print("severity: ");
		switch (severity)
		{
			case GL_DEBUG_SEVERITY_LOW:
				System.out.println("LOW");
				break;
			case GL_DEBUG_SEVERITY_MEDIUM:
				System.out.println("MEDIUM");
				break;
			case GL_DEBUG_SEVERITY_HIGH:
				System.out.println("HIGH");
				break;
		}

	}

}
