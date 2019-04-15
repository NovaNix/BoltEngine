/*
 * 
 */
package TimeKeeping;

public class TickRegulator
{

	double delta = 0;

	long lastTime;

	double TPS;

	double tns;

	boolean Regulated = false;

	public TickRegulator(double TPS)
	{

		Regulated = true;

		lastTime = System.nanoTime();
		tns = 1000000000 / TPS;

		long now = System.nanoTime();
		delta += (now - lastTime) / tns;

	}

	public TickRegulator()
	{

	}

	public void Regualate(double TPS)
	{
		Regulated = true;

		lastTime = System.nanoTime();
		tns = 1000000000 / TPS;

		long now = System.nanoTime();
		delta += (now - lastTime) / tns;
	}

	public void Deregualate()
	{
		Regulated = false;
	}

	public void LoopUpdate()
	{
		long now = System.nanoTime();
		delta += (now - lastTime) / tns;

		lastTime = now;
	}

	public boolean TickTime()
	{
		if (Regulated)
		{
			if (delta >= 1)
			{
				delta--;
				return true;
			}

			else
			{
				return false;
			}
		}

		else
		{
			return true;
		}
	}

}
