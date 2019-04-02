/*
 * 
 */
package Rendering.FrameEvents;

import Messaging.Message;
import Messaging.MessageSender;

public abstract class FrameEvent extends Message
{	


	public FrameEvent(String Name, Type Variation, MessageSender Sender)
	{
		super(Name, Variation, Sender);
	}

	public abstract Object[] GetData();
}
