/*
 * 
 */
package Algorithms.Handling;

import java.util.ArrayList;

public class AlgorithmHandler implements Runnable
{
	boolean Alive = false;

	ArrayList<AlgorithmRequest> PendingRequests = new ArrayList<AlgorithmRequest>();
	ArrayList<AlgorithmRequest> FinishedRequests = new ArrayList<AlgorithmRequest>();

	TaskExecuter AlgorithmRunner;

	Thread HandlerThread;

	public AlgorithmHandler(int MaxRunning)
	{
		AlgorithmRunner = new TaskExecuter(MaxRunning);
		
		HandlerThread = new Thread(this);
		HandlerThread.start();
	}

	@Override
	public void run()
	{
		Alive = true;

		while (Alive)
		{
			
			AlgorithmRequest[] Requests = new AlgorithmRequest[PendingRequests.size()];
			Requests = PendingRequests.toArray(Requests);
			
			if (Requests.length != 0)
			{
				AlgorithmRunner.ExecuteTasks(Requests, false);
			}
			
			CleanFinishedRequests();

			if (PendingRequests.size() == 0)
			{
				try
				{
					wait();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public void PushRequest(AlgorithmRequest Request)
	{
		PendingRequests.add(Request);
		notifyAll();
	}

	private void ProcessRequest(AlgorithmRequest Request)
	{
		Request.Run();
	}

	private void CleanFinishedRequests()
	{
		boolean Done = false;

		while (!Done)
		{
			boolean Changed = false;

			for (int i = 0; i < PendingRequests.size(); i++)
			{
				if (PendingRequests.get(i).IsDone())
				{
					FinishedRequests.add(PendingRequests.get(i));
					i--;

					Changed = true;
				}
			}

			if (!Changed)
			{
				Done = true;
			}
		}
	}

	public void DumpFinishedRequests()
	{
		FinishedRequests = new ArrayList<AlgorithmRequest>();
	}
	
	public void Kill()
	{
		Alive = false;
		notifyAll();
	}

}
