package Rendering;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_DEBUG_CONTEXT;
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
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL;

import IO.EasyLoader;
import Rendering.Cameras.Camera;
import Rendering.Exceptions.ExcessCamerasException;
import Rendering.Image.Texture;
import Rendering.Screens.SingleCameraScreen;

//import org.jogamp.glg2d.GLG2DCanvas;

import Vectors.Vector2f;

public class Window implements GLFWWindowSizeCallbackI
{

	private long WindowHandle;

	private Vector2f CurrentSize = new Vector2f(1280, 780);

	private boolean IsVisible = false;

	private WindowScreen Screen;

	// NOTE! The window should be initialized inside of the thread where rendering
	// will be done
	// This is because of funky opengl stuff

	Color BackgroundColor = new Color(0, 0, 0);
	
	public Window(String Name, String IconPath)
	{
		Init(Name);

		BufferedImage Icon = EasyLoader.LoadLocalImage(IconPath);

		SetIcon(Icon);

		SetScreen(new SingleCameraScreen(this));

	}

	public Window(String Name)
	{
		Init(Name);

		SetScreen(new SingleCameraScreen(this));

	}

	private void Init(String Name)
	{
		GLFWErrorCallback.createPrint(System.err).set();

		boolean Init = glfwInit();

		if (Init == false)
		{
			System.out.println("Error!");
		}

		WindowHandle = glfwCreateWindow(1280, 780, Name, NULL, NULL);

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_SAMPLES, 4);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GL_TRUE);

		glfwMakeContextCurrent(WindowHandle);

		glfwSwapInterval(1);

		glfwSetWindowSizeCallback(WindowHandle, this);
	}

	public void Update()
	{
		Screen.Update();
	}

	public void Render()
	{
		GL.createCapabilities();

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glClearColor(BackgroundColor.getRed() / 255f, BackgroundColor.getGreen() / 255f, BackgroundColor.getBlue() / 255f, BackgroundColor.getAlpha() / 255f);

		Screen.Render();

		glfwSwapBuffers(WindowHandle);

	}

	public void SetScreen(WindowScreen Screen)
	{
		this.Screen = Screen;
	}

	public int GetWidth()
	{
		return (int) CurrentSize.GetX();
	}

	public int GetHeight()
	{
		return (int) CurrentSize.GetY();
	}

	public Vector2f GetSize()
	{
		return CurrentSize.Derive();
	}

	public void AddCamera(Camera Cam)
	{
		try
		{
			Screen.AddCamera(Cam);

			glfwMakeContextCurrent(WindowHandle);

			GL.createCapabilities();

			Cam.Update();
		} catch (ExcessCamerasException e)
		{
			e.printStackTrace();
		}
	}

	public void Show()
	{
		glfwShowWindow(WindowHandle);

		IsVisible = true;
	}

	public void Hide()
	{
		glfwHideWindow(WindowHandle);
		IsVisible = false;
	}

	public void ToggleVisible()
	{
		if (IsVisible == true)
		{
			Hide();
		}

		else
		{
			Show();
		}
	}

	public void SetVisible(boolean Visible)
	{
		if (Visible == false)
		{
			Hide();
		}

		else
		{
			Show();
		}
	}

	public boolean ShouldClose()
	{
		return glfwWindowShouldClose(WindowHandle);
	}

	public void Destroy()
	{
		glfwDestroyWindow(WindowHandle);
	}

	public long GetHandle()
	{
		return WindowHandle;
	}

	public void SetIcon(BufferedImage Icon)
	{
		ByteBuffer Data = Texture.ExtractColorData(Icon);

		GLFWImage.Buffer GLFWBuffer = GLFWImage.create(1);
		GLFWImage GLFWIcon = GLFWImage.create().set(Icon.getWidth(), Icon.getHeight(), Data);
		GLFWBuffer.put(0, GLFWIcon);

		glfwSetWindowIcon(WindowHandle, GLFWBuffer);
	}

	public void SetBackgroundColor(Color Hue)
	{
		BackgroundColor = Hue;
	}
	
	@Override
	public void invoke(long window, int width, int height)
	{
		CurrentSize = new Vector2f(width, height);
		// Update();
	}
}
