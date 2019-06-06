package IO.Keys;

import IO.Keyboard;

public class KeyboardKey extends Key
{

	int KeyCode;

	public KeyboardKey(int KeyCode)
	{
		this.KeyCode = KeyCode;
	}

	@Override
	public boolean IsDown()
	{
		return Keyboard.KeyDown(KeyCode);
	}

}
