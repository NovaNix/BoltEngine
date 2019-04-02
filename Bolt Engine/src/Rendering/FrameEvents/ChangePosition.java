/*
 * 
 */
package Rendering.FrameEvents;

import Messaging.MessageSender;
import Vectors.Vector2f;

public class ChangePosition extends FrameEvent
{

	Vector2f Change;
	boolean Translated;
	
	public ChangePosition(Vector2f Change, boolean Translated, MessageSender Sender)
	{
		super("The animation position is being changed!", Type.Notification, Sender);
		
		this.Change = Change;
		this.Translated = Translated;
	}
	
	@Override
	public Object[] GetData()
	{
		return new Object[] {Change, Translated};
	}

}
