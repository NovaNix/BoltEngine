package IO.Keys;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public class InputManager
{

	public static void Init(long WindowHandle)
	{
		Mouse.InitMouse(WindowHandle);
		Keyboard.InitKeyboard(WindowHandle);
	}

	public static void Update()
	{
		glfwPollEvents();
	}

}
