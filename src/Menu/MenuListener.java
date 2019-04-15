/*
 * 
 */
package Menu;

import Messaging.Message;
import Messaging.MessageListener;

public interface MenuListener extends MessageListener
{
	public void NewMenuEvent(MenuEvent Event);

	@Override
	public default void MessageRecieved(Message Recieved)
	{
		if (Recieved instanceof MenuEvent)
		{
			NewMenuEvent((MenuEvent) Recieved);
		}
	}
}
