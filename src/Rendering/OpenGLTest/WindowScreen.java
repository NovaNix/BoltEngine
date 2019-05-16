package Rendering.OpenGLTest;

import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glReadPixels;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;

import org.lwjgl.BufferUtils;

import Menu.Menu;
import Rendering.Exceptions.ExcessCamerasException;
import Vectors.Vector2f;

public abstract class WindowScreen
{

	protected ArrayList<Camera> Cameras = new ArrayList<Camera>();

	protected Window ConnectedWindow;

	protected JFrame SimulatedEnviroment;

	protected Menu CurrentMenu;

	protected Vector2f MousePosition = new Vector2f(0, 0);

	public WindowScreen(Window ConnectedWindow)
	{
		this.SimulatedEnviroment = new JFrame();

		SimulatedEnviroment.setLayout(new BorderLayout());

		SimulatedEnviroment.setUndecorated(true);

		this.ConnectedWindow = ConnectedWindow;

		Update();
	}

	public void Update()
	{

		// System.out.println("WindowScreen Updated! Current size: " +
		// SimulatedEnviroment.getWidth() + SimulatedEnviroment.getHeight());

		if (ConnectedWindow.GetWidth() != SimulatedEnviroment.getWidth() || ConnectedWindow.GetHeight() != SimulatedEnviroment.getHeight())
		{
			Dimension Size = new Dimension(ConnectedWindow.GetWidth(), ConnectedWindow.GetHeight());

			SimulatedEnviroment.setMinimumSize(Size);
			SimulatedEnviroment.setPreferredSize(Size);
			SimulatedEnviroment.setMaximumSize(Size);
			SimulatedEnviroment.pack();
		}

		for (int i = 0; i < Cameras.size(); i++)
		{
			Cameras.get(i).Update();
		}
	}

	public abstract void AddCamera(Camera Cam) throws ExcessCamerasException;

	Lock RenderLock = new ReentrantLock();

	public ByteBuffer TakeScreenShot()
	{
		// byte[] pixels = new byte[3 * getWidth() * getHeight()];

		ByteBuffer pixels = BufferUtils.createByteBuffer(ConnectedWindow.GetWidth() * ConnectedWindow.GetHeight() * 3);

		glReadPixels(0, 0, ConnectedWindow.GetWidth(), ConnectedWindow.GetHeight(), GL_RGB, GL_UNSIGNED_BYTE, pixels);

		return pixels;
	}

	public JFrame GetSimulatedWindow()
	{
		return SimulatedEnviroment;
	}

	public void Render()
	{
		System.out.println("Rendering Cameras!");

		int[] CameraTexHandles = new int[Cameras.size()];

		for (int i = 0; i < Cameras.size(); i++)
		{
			CameraTexHandles[i] = Cameras.get(i).Render();
		}

		glBindFramebuffer(GL_FRAMEBUFFER, 0);

//		glViewPort(0, 0, getWidth(), getHeight());
		
		Rendering.Start(new Matrix4f(), new Matrix4f().ortho2D(0, getWidth(), 0, getHeight()));
		
		for (int i = 0; i < Cameras.size(); i++)
		{
			Rendering.RenderRawImage(CameraTexHandles[i], new Vector2f(Cameras.get(i).getX(), Cameras.get(i).getY()), new Vector2f(Cameras.get(i).getWidth(), Cameras.get(i).getHeight()), 0);
		}
	}

	public Vector2f GetMousePosition()
	{
		return MousePosition;
	}

}
