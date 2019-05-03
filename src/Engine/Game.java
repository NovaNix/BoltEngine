package Engine;

import TimeKeeping.TickRegulator;
import TimeKeeping.Timer;

public abstract class Game implements Runnable
{

	// Stores if the game is currently running
	private boolean Running = false;

	// These handle the speed of tick updating and rendering
	private TickRegulator TickHandler;
	private TickRegulator RenderHandler;

	// These store the number of frames and ticks run per second
	private int FPS;
	private int TPS;

	// These hold how fast tick and render are run
	private double TickSpeed;
	private double RenderSpeed;

	public Game(float Tps)
	{
		TickHandler = new TickRegulator(Tps);
		RenderHandler = new TickRegulator();
	}

	public Game(float TPS, float MaxFPS)
	{
		TickHandler = new TickRegulator(TPS);
		RenderHandler = new TickRegulator(MaxFPS);
	}

	@Override
	public void run()
	{
		StartUp();
		GameLoop();
		ShutDown();
	}

	private final void GameLoop()
	{
		Running = true;

		Timer SecondTimer = new Timer(1);

		SecondTimer.Set();

		int TPS = 0;
		int FPS = 0;

		while (Running)
		{
			TickHandler.LoopUpdate();
			RenderHandler.LoopUpdate();

			if (TickHandler.TickTime())
			{
				Tick();

				TPS++;
			}

			if (RenderHandler.TickTime())
			{
				Render();

				FPS++;
			}

			if (SecondTimer.Check())
			{

				SecondTimer.Set();

				this.TPS = TPS;
				this.FPS = FPS;

				TPS = 0;
				FPS = 0;
			}
		}

		Running = false;
	}

	public abstract void StartUp();

	public abstract void ShutDown();

	protected abstract void Tick();

	protected abstract void UpdateInput();

	protected abstract void Render();

	public void CapFPS(float FPS)
	{
		RenderHandler.Regualate(FPS);
	}

	public void UnCapFPS()
	{
		RenderHandler.Deregualate();
	}

	public int GetFPS()
	{
		return FPS;
	}

	public int GetTPS()
	{
		return TPS;
	}

}
