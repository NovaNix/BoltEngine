/*
 *
 */
package Rendering.FrameEvents;

import Messaging.Message;

public abstract class FrameEvent extends Message
{

	public FrameEvent(String Name, Type Variation, Object Sender)
	{
		super(Name, Variation, Sender);
	}

	public abstract Object[] GetData();
}
