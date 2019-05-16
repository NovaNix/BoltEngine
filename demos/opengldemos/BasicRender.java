package opengldemos;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;

import org.lwjgl.opengl.GL;

import IO.EasyLoader;
import Rendering.OpenGLTest.Camera;
import Rendering.OpenGLTest.Rendering;
import Rendering.OpenGLTest.Texture;
import Rendering.OpenGLTest.Window;
import Vectors.Vector2f;

public class BasicRender
{

	static Window Win = new Window("Basic Render");

	static Camera Cam = new Camera("Default Perspective", new Vector2f(0, 0));

	static Texture DirtTexture = new Texture(EasyLoader.LoadLocalImage("/DemoImages/Dirt.png"));

	public static void main(String[] args)
	{

		GL.createCapabilities();

		glEnable(GL_TEXTURE_2D);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glfwSwapInterval(1);

		Cam.AddRenderable(() -> Rendering.RenderRawImage(DirtTexture, new Vector2f(0, 0), new Vector2f(500, 500), 0));

		Win.AddCamera(Cam);

		Win.SetVisable(true);

		while (!Win.ShouldClose())
		{
			Render();
		}

		Win.Destroy();

	}

	public static void Render()
	{
		glfwPollEvents();

		Win.Update();

		Win.Render();
	}

}
