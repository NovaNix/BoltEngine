package Engine;

import Messaging.MessageManager;

public class BoltEngine
{

	public static final double EngineVersion = 0d;

	public static MessageManager MessagingEngineManager;

	public static Thread MessageManagerThread;

	public static void Initiate()
	{
		MessagingEngineManager = new MessageManager();

		MessageManagerThread = new Thread(MessagingEngineManager);

		MessageManagerThread.start();
	}

}
