package Engine;

import Messaging.MessageManager;

public class BoltEngine
{

	public static final double EngineVersion = 0d;

	public static MessageManager MessagingEngineManager;

	public static Thread MessageManagerThread;

	private static Game CurrentGame;
	private static Thread GameThread;

	{
		MessagingEngineManager = new MessageManager(5);

		MessageManagerThread = new Thread(MessagingEngineManager);

		MessageManagerThread.start();
	}

	public static void StartGame(Game Start)
	{
		CurrentGame = Start;

		GameThread = new Thread(CurrentGame);

		GameThread.start();
	}

	public static void ShutDown()
	{
		MessagingEngineManager.Kill();

	}

}
