/*
 * 
 */
package Rendering.FrameEvents;

import java.awt.Image;

import Messaging.Message.Type;
import Messaging.MessageSender;

public class ChangeFrame extends FrameEvent
{

	Image Frame;

	public ChangeFrame(Image NewFrame, MessageSender Sender)
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
