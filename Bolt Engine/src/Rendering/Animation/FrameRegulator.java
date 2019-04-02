/*
 * 
 */
package Rendering.Animation;

public class FrameRegulator
{

	int Counter = 0;
	
	float FPS;
	
	long LastUpdate;
	
	double Delta;

	double TNS;
	
	public FrameRegulator(float FPS)
	{
		this.FPS = FPS;
		
		this.LastUpdate = System.nanoTime();	

		TNS = 1000000000 / FPS;

		long now = System.nanoTime();
	 	Delta += (now - LastUpdate) / TNS;
	}
	
	public void Update()
	{
		long now = System.nanoTime();
	 	Delta += (now - LastUpdate) / TNS;
	 	
		LastUpdate = System.nanoTime(); 

		if (Delta >= 1)
		{
			Delta--;
			
			Counter++;
		}

	}
	
	public int GetRelativeFrame(int TotalFrame)
	{
		Update();

		if (TotalFrame > Counter)
		{
			return Counter;
		}
		
		else
		{
			return Counter % TotalFrame;
		}
	}
		
	public void SetCounter(int Counter)
	{
		this.Counter = Counter;
	}
	
	public int GetCounter()
	{
		return Counter;
	}
	
}
