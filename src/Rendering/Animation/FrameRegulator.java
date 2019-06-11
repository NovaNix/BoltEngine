/*
 * 
 */
package Rendering.Animation;

public class FrameRegulator
{

	int Running = 0;

	float FPS;

	double TNS;

	long StartTime;

	public FrameRegulator(float FPS)
	{
		this.FPS = FPS;

		TNS = 1000000000 / FPS;
	}

	public void Start()
	{
		StartTime = System.nanoTime();
		Running = 1;
	}

	public void Stop()
	{
		Running = 0;
	}

	public int GetRelativeFrame(int TotalFrame)
	{
		return (int) ((System.nanoTime() - StartTime) / TNS) % TotalFrame;
	}

}
