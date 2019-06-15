/*
 * 
 */
package TimeKeeping;

public class Timer
{

	long Seconds;

	long Timer;

	boolean Active = false;

	public Timer(float Seconds)
	{
		this.Seconds = (long) Seconds;

		this.Timer = System.currentTimeMillis();
	}

	public boolean IsSet()
	{
		return Active;
	}
	
	public boolean Check()
	{
		if (Active)
		{
			if (System.currentTimeMillis() - Timer > Seconds * 1000)
			{
				Active = false;

				return true;
			}

			else
			{
				return false;
			}
		}

		else
		{
			return false;
		}
	}

	public void Set()
	{
		Timer = System.currentTimeMillis();

		Active = true;
	}

	public long GetRemainingTime()
	{
		if (System.currentTimeMillis() - Timer > Seconds * 1000)
		{
			return 0;
		}

		else
		{
			return (long) (Seconds - ((System.currentTimeMillis() - Timer) / 1000));
		}
	}

}
