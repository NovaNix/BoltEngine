/*
 * 
 */
package IO.Keys;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import Rendering.WindowScreen;

// TODO: Auto-generated Javadoc
/**
 * The Class KeyboardKey.
 */
public class KeyboardKey extends Key implements KeyListener
{

	/** The Key code. */
	int KeyCode;

	/**
	 * Instantiates a new keyboard key.
	 *
	 * @param KeyCode
	 *            is the key code of the key
	 * @param Part
	 *            is the window the key is bound to
	 */
	public KeyboardKey(int KeyCode, WindowScreen Part)
	{
		this.KeyCode = KeyCode;

		Part.addKeyListener(this);

		Part.setFocusable(true);

		Part.setFocusTraversalKeysEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent arg0)
	{
		if (arg0.getKeyCode() == KeyCode)
		{
			Down = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent arg0)
	{
		if (arg0.getKeyCode() == KeyCode)
		{
			Down = false;
			Typed = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0)
	{

	}

}
