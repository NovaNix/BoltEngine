/*
 * 
 */
package IO.Keys;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Rendering.WindowScreen;
import Vectors.Vector2f;

// TODO: Auto-generated Javadoc
/**
 * The Class MouseKey.
 */
public class MouseKey extends Key implements MouseListener
{

	/** The Constant LEFTCLICK. */
	public static final int LEFTCLICK = 1;
	
	/** The Constant RIGHTCLICK. */
	public static final int RIGHTCLICK = 2;
	
	/** The Constant MIDDLECLICK. */
	public static final int MIDDLECLICK = 3;
	
	/** The Key code. */
	int KeyCode;
	
	WindowScreen Part;
	
	Vector2f LastClickLocation = new Vector2f(0, 0);
	
	/**
	 * Instantiates a new mouse key.
	 *
	 * @param KeyCode is the key code of the key
	 * @param Part is the window the key is bound to
	 */
	public MouseKey(int KeyCode, WindowScreen Part)
	{
		this.KeyCode = KeyCode;
		
		Part.addMouseListener(this);
		
		this.Part = Part;
		
		switch (KeyCode)
		{
			case LEFTCLICK:
				
				Name = "Left Click";
				
				break;
				
			case RIGHTCLICK:
				
				Name = "Right Click";
				
				break;
				
			case MIDDLECLICK:
				
				Name = "Middle Click";
				
				break;
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent arg0)
	{

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0)
	{

		switch (KeyCode)
		{
			case LEFTCLICK:
				
				if (SwingUtilities.isLeftMouseButton(arg0))
				{
					Down = true;
				}
				
				break;
				
			case RIGHTCLICK:
				
				if (SwingUtilities.isRightMouseButton(arg0))
				{
					Down = true;
				}
				
				break;
				
			case MIDDLECLICK:
				
				if (SwingUtilities.isMiddleMouseButton(arg0))
				{
					Down = true;
				}
				
				break;
		}
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		switch (KeyCode)
		{
			case LEFTCLICK:
				
				if (SwingUtilities.isLeftMouseButton(arg0))
				{
					Down = false;
					Typed = true;
					LastClickLocation = Part.GetMousePosition();
				}
				
				break;
				
			case RIGHTCLICK:
				
				if (SwingUtilities.isRightMouseButton(arg0))
				{
					Down = false;
					Typed = true;
					LastClickLocation = Part.GetMousePosition();
				}
				
				break;
				
			case MIDDLECLICK:
				
				if (SwingUtilities.isMiddleMouseButton(arg0))
				{
					Down = false;
					Typed = true;
					LastClickLocation = Part.GetMousePosition();
				}
				
				break;
		}
		
	}
	
	public Vector2f GetLastClickLocation()
	{
		return LastClickLocation;
	}
	
	public WindowScreen GetPart()
	{
		return Part;
	}
	
}
