package Rendering.OpenGLTest;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwHideWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIcon;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

import IO.EasyLoader;

//import org.jogamp.glg2d.GLG2DCanvas;

import Vectors.Vector2f;

public class Window implements GLFWWindowSizeCallbackI
{

	long WindowHandle;

	Vector2f CurrentSize = new Vector2f(0, 0);

	boolean IsVisable = false;

	public Window(String Name, String IconPath)
	{
		GLFWErrorCallback.createPrint(System.err).set();

		glfwInit();

		WindowHandle = glfwCreateWindow(1280, 750, Name, NULL, NULL);

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_SAMPLES, 4);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

		glfwMakeContextCurrent(WindowHandle);

		glfwSwapInterval(0);

		glfwSetWindowSizeCallback(WindowHandle, this);

		Texture Icon = new Texture(EasyLoader.LoadLocalImage(IconPath));

		GLFWImage.Buffer GLFWBuffer = GLFWImage.create(1);
		GLFWImage GLFWIcon = GLFWImage.create().set(Icon.GetWidth(), Icon.GetHeight(), Icon.GetData());
		GLFWBuffer.put(0, GLFWIcon);

		glfwSetWindowIcon(WindowHandle, GLFWBuffer);

	}

	public Window(String Name)
	{
		glfwInit();

		WindowHandle = glfwCreateWindow(1280, 750, Name, NULL, NULL);

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_SAMPLES, 4);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

		glfwMakeContextCurrent(WindowHandle);

		glfwSwapInterval(0);

		glfwSetWindowSizeCallback(WindowHandle, this);
	}

	public void Update()
	{

	}

	public void Render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	}

	public Vector2f GetSize()
	{
		return CurrentSize.Derive();
	}

	// public void AddCamera(Camera Cam)
	// {
	// try
	// {
	// WinScreen.AddCamera(Cam);
	// Cam.Update();
	// } catch (ExcessCamerasException e)
	// {
	// e.printStackTrace();
	// }
	// }
	//
	public void Show()
	{
		glfwShowWindow(WindowHandle);
		IsVisable = true;
	}

	public void Hide()
	{
		glfwHideWindow(WindowHandle);
		IsVisable = false;
	}

	public void ToggleVisable()
	{
		if (IsVisable == true)
		{
			Hide();
		}

		else
		{
			Show();
		}
	}

	public void SetVisable(boolean Visable)
	{
		if (Visable == true)
		{
			Hide();
		}

		else
		{
			Show();
		}
	}

	public void Destroy()
	{
		glfwDestroyWindow(WindowHandle);
	}

	@Override
	public void invoke(long window, int width, int height)
	{
		CurrentSize = new Vector2f(width, height);
		// Update();
	}
}
