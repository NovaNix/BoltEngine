package Rendering;

import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glReadPixels;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;

import org.lwjgl.BufferUtils;

import Menu.Menu;
import Rendering.Cameras.Camera;
import Rendering.Exceptions.ExcessCamerasException;
import Vectors.Vector2f;

public abstract class WindowScreen extends Canvas implements Renderable, MouseMotionListener
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

		this.addMouseMotionListener(this);
		this.ConnectedWindow = ConnectedWindow;

		// this.SimulatedEnviroment.setLayout(new BorderLayout());

		ConnectedWindow.add(this, BorderLayout.CENTER);

		ConnectedWindow.pack();

		createBufferStrategy(2);

		Update();
	}

	public void Update()
	{
		for (int i = 0; i < Cameras.size(); i++)
		{
			Cameras.get(i).Update();
		}

		if (!(getWidth() == SimulatedEnviroment.getWidth() && getHeight() == SimulatedEnviroment.getHeight()))
		{
			SimulatedEnviroment.setMinimumSize(getSize());
			SimulatedEnviroment.setPreferredSize(getSize());
			SimulatedEnviroment.setMaximumSize(getSize());
			SimulatedEnviroment.pack();
		}
	}

	public abstract void AddCamera(Camera Cam) throws ExcessCamerasException;

	Lock RenderLock = new ReentrantLock();

	public ByteBuffer TakeScreenShot()
	{
		// byte[] pixels = new byte[3 * getWidth() * getHeight()];

		ByteBuffer pixels = BufferUtils.createByteBuffer(getWidth() * getHeight() * 3);

		glReadPixels(0, 0, getWidth(), getHeight(), GL_RGB, GL_UNSIGNED_BYTE, pixels);

		return pixels;
	}

	// @Override
	// public void paintComponent(Graphics g)
	// {
	//
	//
	//// RenderLock.lock();
	////
	//// try
	//// {
	//// BufferStrategy bs;
	//// ConnectedWindow.createBufferStrategy(2);
	//// bs = ConnectedWindow.getBufferStrategy();
	////
	//// super.paintComponent(g);
	//// for (int i = 0; i < Cameras.size(); i++)
	//// {
	//// g.drawImage(Cameras.get(i).Render(), Cameras.get(i).getX(),
	// Cameras.get(i).getY(), Cameras.get(i).getWidth(), Cameras.get(i).getHeight(),
	// Cameras.get(i));
	//// }
	//// if (CurrentMenu != null)
	//// {
	//// CurrentMenu.Render();
	//// }
	////
	//// } finally
	//// {
	//// RenderLock.unlock();
	//// }
	//
	// }

	public JFrame GetSimulatedWindow()
	{
		return SimulatedEnviroment;
	}

	@Override
	public void Render()
	{
		BufferStrategy Strat = getBufferStrategy();

		Graphics g = Strat.getDrawGraphics();

		for (int i = 0; i < Cameras.size(); i++)
		{
			g.drawImage(Cameras.get(i).Render(), Cameras.get(i).getX(), Cameras.get(i).getY(), Cameras.get(i).getWidth(), Cameras.get(i).getHeight(), Cameras.get(i));
		}
		if (CurrentMenu != null)
		{
			CurrentMenu.Render();
		}

		g.dispose();
		Strat.show();

		// synchronized (this)
		// {
		// repaint();
		//
		// try
		// {
		// RenderLock.lock();
		// } finally
		// {
		// RenderLock.unlock();
		// }
		// }
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		MousePosition = new Vector2f(e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		MousePosition = new Vector2f(e.getX(), e.getY());
	}

	public Vector2f GetMousePosition()
	{
		return MousePosition;
	}

}
