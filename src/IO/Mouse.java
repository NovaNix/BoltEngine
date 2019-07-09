package IO;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import Vectors.Vector2f;

public class Mouse
{
	static Vector2f MousePos = new Vector2f(0, 0);

	static boolean LeftDown;
	static boolean RightDown;
	static boolean MiddleDown;

	static boolean LeftClicked;
	static boolean RightClicked;
	static boolean MiddleClicked;

	static long Window;

	public static void InitMouse(long WindowHandle)
	{
		Window = WindowHandle;

		glfwSetCursorPosCallback(WindowHandle, new GLFWCursorPosCallbackI()
		{

			@Override
			public void invoke(long window, double xpos, double ypos)
			{
				MousePos = new Vector2f((float) xpos, (float) ypos);
			}
		});

		glfwSetMouseButtonCallback(WindowHandle, new GLFWMouseButtonCallbackI()
		{

			@Override
			public void invoke(long window, int button, int action, int mods)
			{
				boolean DownState = action == GLFW_PRESS;

				switch (button)
				{
					case GLFW_MOUSE_BUTTON_LEFT:
						LeftDown = DownState;
						break;
					case GLFW_MOUSE_BUTTON_RIGHT:
						RightDown = DownState;
						break;
					case GLFW_MOUSE_BUTTON_MIDDLE:
						MiddleDown = DownState;
						break;
				}

				if (DownState == false)
				{
					switch (button)
					{
						case GLFW_MOUSE_BUTTON_LEFT:
							LeftClicked = true;
							System.out.println("Mouse Clicked");
							break;
						case GLFW_MOUSE_BUTTON_RIGHT:
							RightClicked = true;
							break;
						case GLFW_MOUSE_BUTTON_MIDDLE:
							MiddleClicked = true;
							break;
					}
				}
			}
		});
	}

	public static boolean LeftMouseDown()
	{
		return LeftDown;
	}

	public static boolean RightMouseDown()
	{
		return RightDown;
	}

	public static boolean MiddleMouseDown()
	{
		return MiddleDown;
	}

	public static boolean LeftMouseClicked()
	{
		return LeftClicked;
	}

	public static boolean RightMouseClicked()
	{
		return RightClicked;
	}

	public static boolean MiddleMouseClicked()
	{
		return MiddleClicked;
	}

	public static Vector2f GetMousePosition()
	{
		return MousePos.Derive();
	}

	static boolean Visible = true;

	public static void HideMouse()
	{
		glfwSetInputMode(Window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		Visible = false;
	}

	public static void ShowMouse()
	{
		glfwSetInputMode(Window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		Visible = true;
	}

	public static boolean MouseVisible()
	{
		return Visible;
	}

	public static void ClearClicks()
	{
		LeftClicked = false;
		RightClicked = false;
		MiddleClicked = false;
	}

}
