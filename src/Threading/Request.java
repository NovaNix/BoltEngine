package Threading;

public abstract class Request extends Task
{

	public Request(String Name, Runnable Job)
	{
		super(Name, Job);
	}

}