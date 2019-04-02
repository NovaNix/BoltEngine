package Threading;

import java.util.ArrayList;

import Engine.EngineConfig;

public abstract class EngineHandler implements Runnable
{

	public static ArrayList<EngineHandler> Handlers = new ArrayList<EngineHandler>();
	
	public static TaskExecuter Executer = new TaskExecuter(EngineConfig.MaxEngineHandlerThreads);
	
	public EngineHandler()
	{
		Handlers.add(this);
	}
	
	public static void UpdateHandlers()
	{
		EngineHandler[] Handler = new EngineHandler[Handlers.size()];
		
		Handler = Handlers.toArray(Handler);
		
		Task[] TaskList = new Task[Handlers.size()];
		
		for (int i = 0; i < TaskList.length; i++)
		{
			TaskList[i] = new Task("Engine Handler " + i, Handler[i]);
		}
		
		Executer.ExecuteTasks(TaskList);
	}

}
