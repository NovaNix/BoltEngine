package Rendering;

import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glReadPixels;
import static org.lwjgl.opengl.GL11.glViewport;
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

import GUI.GUI;
import Rendering.Cameras.Camera;
import Rendering.Exceptions.ExcessCamerasException;
import Rendering.Handling.Rendering;
import Vectors.Vector2f;

public abstract class WindowScreen
{

	protected ArrayList<Camera> Cameras = new ArrayList<Camera>();

	protected Window ConnectedWindow;

	protected JFrame SimulatedEnviroment;

	protected Vector2f MousePosition = new Vector2f(0, 0);

	GUI WindowGUI;

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
		for (int i = 0; i < Cameras.size(); i++)
		{
			Cameras.get(i).Update();
		}

		if (WindowGUI != null)
		{
			WindowGUI.Tick();
		}
	}

	public abstract void AddCamera(Camera Cam) throws ExcessCamerasException;

	Lock RenderLock = new ReentrantLock();

	public void SetGUI(GUI UI)
	{
		WindowGUI = UI;
	}

	public ByteBuffer TakeScreenShot()
	{
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
		int[] CameraTexHandles = new int[Cameras.size()];

		for (int i = 0; i < Cameras.size(); i++)
		{
			CameraTexHandles[i] = Cameras.get(i).Render();
		}

		glBindFramebuffer(GL_FRAMEBUFFER, 0);

		glViewport(0, 0, ConnectedWindow.GetWidth(), ConnectedWindow.GetHeight());

		for (int i = 0; i < Cameras.size(); i++)
		{
			Rendering.DrawCamera(CameraTexHandles[i]);
		}

		if (WindowGUI != null)
		{
			WindowGUI.Render();
		}
	}

	public Vector2f GetMousePosition()
	{
		return MousePosition;
	}

	public void UpdateSize()
	{
		Dimension Size = new Dimension(ConnectedWindow.GetWidth(), ConnectedWindow.GetHeight());

		SimulatedEnviroment.setMinimumSize(Size);
		SimulatedEnviroment.setPreferredSize(Size);
		SimulatedEnviroment.setMaximumSize(Size);
		SimulatedEnviroment.pack();

		for (int i = 0; i < Cameras.size(); i++)
		{
			Cameras.get(i).UpdateSize();
		}

		if (WindowGUI != null)
		{
			WindowGUI.SetSize(new Vector2f(ConnectedWindow.GetWidth(), ConnectedWindow.GetHeight()));
		}
	}

}
