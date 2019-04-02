package Threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecuter
{

	ExecutorService Pool;   
	
	public TaskExecuter(int AllocatedThreads)
	{
		Pool = Executors.newFixedThreadPool(AllocatedThreads);
	}
	
	public synchronized void ExecuteTasks(Task[] Jobs)
	{
		for (int i = 0; i < Jobs.length; i++)
		{
			Jobs[i].SetExecuter(this);
			
			Pool.execute(Jobs[i]);
		}
		
		boolean AllDone = false;
		
		while (!AllDone)
		{
			try
			{
				this.wait();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			AllDone = true;
			
			for (int i = 0; i < Jobs.length; i++)
			{
				if (!Jobs[i].IsDone())
				{
					AllDone = false;
				}
			}
		}
	}
	
	public synchronized void TaskUpdate()
	{
		notifyAll();
	}
	
}
