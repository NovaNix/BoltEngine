package Rendering.Handling;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

public class SwapBufferThread implements Runnable
{

	private static boolean SwapBuffer = false;

	public static String Lock = "This is a lock";

	private static SwapBufferThread SwapThread = new SwapBufferThread();

	static Thread Swap;

	static long WindowHandle;

	static
	{
		Swap = new Thread(SwapThread, "SwapBuffer Thread");
		Swap.start();
	}

	public static synchronized void SwapBuffer(long Handle)
	{
		synchronized (Lock)
		{
			SwapBuffer = true;
			WindowHandle = Handle;
		}

	}

	boolean Running;

	@Override
	public synchronized void run()
	{
		Running = true;
		System.out.println("Yeet 1");
		while (Running)
		{

			synchronized (Lock)
			{
				if (SwapBuffer)
				{
					glfwSwapBuffers(WindowHandle);
					System.out.println("Swapped");
					SwapBuffer = false;
				}
			}

		}

	}

}
