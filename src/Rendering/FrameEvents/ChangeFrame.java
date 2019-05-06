/*
 *
 */
package Rendering.FrameEvents;

import java.awt.Image;

public class ChangeFrame extends FrameEvent
{

	Image Frame;

	public ChangeFrame(Image NewFrame, Object Sender)
	{
		super("The animation frame is being changed!", Type.Notification, Sender);

		Frame = NewFrame;
	}

	@Override
	public Object[] GetData()
	{
		return new Object[] { Frame };
	}

}
