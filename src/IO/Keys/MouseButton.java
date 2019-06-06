package IO.Keys;

import IO.Mouse;

public class MouseButton extends Key
{

	public static final int LEFT_MOUSE = 0;
	public static final int RIGHT_MOUSE = 1;

	public static final int MIDDLE_MOUSE = 2;

	int Button;

	public MouseButton(int Button)
	{
		this.Button = Button;
	}

	@Override
	public boolean IsDown()
	{
		switch (Button)
		{
			case LEFT_MOUSE:
				return Mouse.LeftMouseDown();
			case RIGHT_MOUSE:
				return Mouse.RightMouseDown();
			case MIDDLE_MOUSE:
				return Mouse.MiddleMouseDown();
		}

		return false;
	}

}
