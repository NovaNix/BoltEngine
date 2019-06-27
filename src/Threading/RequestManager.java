package Threading;

import java.util.ArrayList;

import Algorithms.Handling.AlgorithmRequest;

public class RequestManager implements Runnable
{

	boolean Alive = false;

	ArrayList<Request> PendingRequests = new ArrayList<Request>();
	ArrayList<Request> FinishedRequests = new ArrayList<Request>();

	Thread HandlerThread;

	TaskExecuter AlgorithmRunner;

	public RequestManager(int Threads)
	{
		AlgorithmRunner = new TaskExecuter(Threads);

		HandlerThread = new Thread(this);
		HandlerThread.start();
	}

	@Override
	public synchronized void run()
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

	public void PushRequest(Request Request)
	{
		PendingRequests.add(Request);
		notifyAll();
	}

	private void CleanFinishedRequests()
	{
		boolean Done = false;

		ArrayList<Request> ToRemove = new ArrayList<Request>();

		while (!Done)
		{
			boolean Changed = false;

			for (int i = 0; i < PendingRequests.size(); i++)
			{
				if (PendingRequests.get(i).IsDone())
				{
					ToRemove.add(PendingRequests.get(i));

					Changed = true;
				}
			}

			if (!Changed)
			{
				Done = true;
			}

			else
			{
				for (int i = 0; i < ToRemove.size(); i++)
				{
					FinishedRequests.add(ToRemove.get(i));
					PendingRequests.remove(ToRemove.get(i));
				}
			}
		}
	}

	public void DumpFinishedRequests()
	{
		FinishedRequests = new ArrayList<Request>();
	}

	public void Kill()
	{
		Alive = false;
		notifyAll();
	}

}
