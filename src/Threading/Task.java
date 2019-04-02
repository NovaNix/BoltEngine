package Threading;

public class Task implements Runnable
{

	String Name;
	
	Runnable Job;
	
	boolean Done = false;
	
	TaskExecuter Executer;
	
	public Task(String Name, Runnable Job)
	{
		this.Name = Name;
		
		this.Job = Job;
	}
	
	@Override
	public void run()
	{
		Job.run();
		
		Done = true;
		
		Executer.TaskUpdate();
	}
	
	public void SetExecuter(TaskExecuter Runner)
	{
		this.Executer = Runner;
	}
	
	public String GetName()
	{
		return Name;
	}
	
	public Runnable GetJob()
	{
		return Job;
	}
	
	public boolean IsDone()
	{
		return Done;
	}
	
	public void Reset()
	{
		Done = false;
	}
	
}
