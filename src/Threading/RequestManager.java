package Threading;

public abstract class RequestManager <H> implements Runnable
{

	boolean Alive = false;

	ArrayList<H> PendingRequests = new ArrayList<H>();
	ArrayList<H> FinishedRequests = new ArrayList<H>();

	Thread HandlerThread;

	public RequestManager()
	{
		HandlerThread = new Thread(this);
		HandlerThread.start();
	}

	public abstract void ProcessRequest(H Request);
	public abstract boolean RequestDone(H Request);

	@Override
	public void run()
	{
		Alive = true;

		while (Alive)
		{

			for (int i = 0; i < PendingRequests.size(); i++)
			{
				ProcessRequest(PendingRequests.get(i));
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

	public void PushRequest(H Request)
	{
		PendingRequests.add(Request);
		notifyAll();
	}

	private void CleanFinishedRequests()
	{
		boolean Done = false;

		ArrayList<H> ToRemove = new ArrayList<H>();

		while (!Done)
		{
			boolean Changed = false;

			for (int i = 0; i < PendingRequests.size(); i++)
			{
				if (RequestDone(PendingRequests.get(i)))
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
		FinishedRequests = new ArrayList<AlgorithmRequest>();
	}

	public void Kill()
	{
		Alive = false;
		notifyAll();
	}

}
