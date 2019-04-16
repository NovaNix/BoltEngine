package IO.Keys;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

import java.util.HashMap;

import org.lwjgl.glfw.GLFWKeyCallbackI;

public class Keyboard implements GLFWKeyCallbackI
{

	HashMap<Integer, Boolean> Keys = new HashMap<Integer, Boolean>();

	public Keyboard(long WindowHandle)
	{
		glfwSetKeyCallback(WindowHandle, this);
	}

	public void Update()
	{
		glfwPollEvents();
	}

	public boolean KeyDown(int Key)
	{
		if (Keys.containsKey(Key))
		{
			return Keys.get(Key);
		}

		return false;
	}

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods)
	{

		if (action == GLFW_RELEASE)
		{
			Keys.put(key, false);
		}

		else if (action == GLFW_PRESS)
		{
			Keys.put(key, true);
		}
	}

}
