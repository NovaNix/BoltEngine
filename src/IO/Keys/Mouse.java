package IO.Keys;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import Vectors.Vector2f;

public class Mouse implements GLFWCursorPosCallbackI, GLFWMouseButtonCallbackI
{

	static Mouse CallbackHandler = new Mouse();

	static Vector2f MousePos;

	static boolean LeftDown;
	static boolean RightDown;
	static boolean MiddleDown;

	static boolean LeftClicked;
	static boolean RightClicked;
	static boolean MiddleClicked;

	public static void InitMouse(long WindowHandle)
	{
		glfwSetCursorPosCallback(WindowHandle, CallbackHandler);
		glfwSetMouseButtonCallback(WindowHandle, CallbackHandler);
	}

	public static void Update()
	{
		glfwPollEvents();
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

	@Override
	public void invoke(long window, double xpos, double ypos)
	{
		MousePos = new Vector2f((float) xpos, (float) ypos);
	}

	@Override
	public String getSignature()
	{
		// TODO Auto-generated method stub
		return GLFWMouseButtonCallbackI.super.getSignature();
	}

	@Override
	public void callback(long args)
	{
		// TODO Auto-generated method stub
		GLFWCursorPosCallbackI.super.callback(args);
	}

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

}
