/*
 * 
 */
package Rendering.FrameEvents;

import Messaging.MessageSender;
import Messaging.Message.Type;

public class AnimationEnded extends FrameEvent
{
	public AnimationEnded(MessageSender Sender)
	{
		super("An animation just ended!", Type.Notification, Sender);
	}

	@Override
	public Object[] GetData()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
