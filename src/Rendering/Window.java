/*
 * 
 */
package Rendering;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

//import org.jogamp.glg2d.GLG2DCanvas;

import IO.EasyLoader;
import Rendering.Cameras.Camera;
import Rendering.Exceptions.ExcessCamerasException;
import Rendering.Screens.SingleCameraScreen;
import Vectors.Vector2f;

@SuppressWarnings("serial")
public class Window extends JFrame implements ComponentListener
{

	Vector2f MousePosition;

	// GLG2DCanvas Screen;

	WindowScreen WinScreen;

	public Window(String Name, String IconPath)
	{
		super(Name);

		this.setLayout(new BorderLayout());

		setPreferredSize(new Dimension(1000, 750));
		setMaximumSize(new Dimension(1000, 750));
		setMinimumSize(new Dimension(1000, 750));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		SetScreen(new SingleCameraScreen(this));

		ImageIcon Icon = EasyLoader.LoadLocalImageIcon(IconPath);
		setIconImage(Icon.getImage());

		this.addComponentListener(this);
		WinScreen.addComponentListener(this);

		// createBufferStrategy(2);
	}

	public Window(String Name)
	{
		super(Name);

		setPreferredSize(new Dimension(1000, 750));
		setMaximumSize(new Dimension(1000, 750));
		setMinimumSize(new Dimension(1000, 750));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		SetScreen(new SingleCameraScreen(this));

		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		this.addComponentListener(this);
		WinScreen.addComponentListener(this);

		// createBufferStrategy(2);
	}

	public void Update()
	{
		if (isFocused())
		{
			WinScreen.requestFocus();
		}

		WinScreen.Update();

	}

	public void AddCamera(Camera Cam)
	{
		try
		{
			WinScreen.AddCamera(Cam);
			Cam.Update();
		} catch (ExcessCamerasException e)
		{
			e.printStackTrace();
		}
	}

	public void SetScreen(WindowScreen Screen)
	{
		// this.Screen = new GLG2DCanvas(Screen);
		this.WinScreen = Screen;
	}

	public WindowScreen GetScreen()
	{
		return WinScreen;
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		Update();
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e)
	{
		// TODO Auto-generated method stub

	}
}
