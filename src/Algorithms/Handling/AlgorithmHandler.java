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

	@Override
	public void run()
	{
		Alive = true;

		while (Alive)
		{
			for (AlgorithmRequest Request : PendingRequests)
			{
				ProcessRequest(Request);
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
		notify();
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

}
