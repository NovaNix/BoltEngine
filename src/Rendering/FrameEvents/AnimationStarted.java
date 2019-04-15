/*
 * 
 */
package Rendering.FrameEvents;

import Messaging.Message.Type;
import Messaging.MessageSender;

public class AnimationStarted extends FrameEvent
{

	public AnimationStarted(MessageSender Sender)
	{
		super("An animation is being started!", Type.Notification, Sender);
	}

	@Override
	public Object[] GetData()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
