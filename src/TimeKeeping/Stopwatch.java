/*
 * 
 */
package TimeKeeping;

public class Stopwatch
{

	long Timer = 0;

	long FinishedTime = 0;

	boolean Active = false;

	public Stopwatch()
	{

	}

	public void Start()
	{
		Active = true;
		Timer = System.currentTimeMillis();
		FinishedTime = 0;
	}

	public long GetCurrentTime()
	{
		if (Active)
		{
			return (Timer - System.currentTimeMillis()) / 1000;
		}

		else
		{
			return 0;
		}
	}

	public long GetFinalTime()
	{
		return FinishedTime;
	}

	public void Stop()
	{
		FinishedTime = GetCurrentTime();
		Active = false;
	}

	public void Pause()
	{
		Active = false;
	}

	public void Unpause()
	{
		Active = true;
	}

	public void Reset()
	{
		Timer = System.currentTimeMillis();
		FinishedTime = 0;
	}

}
