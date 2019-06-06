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

	// These hold how fast tick and render are run in milliseconds
	// Stopwatches are not used to measure the time due to potential accuracy issues
	private double TickSpeed;
	private double RenderSpeed;

	protected boolean PrintFPS = false;
	protected boolean PrintTPS = false;

	protected boolean PrintRenderSpeed = false;
	protected boolean PrintTickSpeed = false;

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
				double Now1 = System.currentTimeMillis();

				UpdateInput();
				Tick();

				TickSpeed = System.currentTimeMillis() - Now1;

				TPS++;
			}

			if (RenderHandler.TickTime())
			{
				double Now2 = System.currentTimeMillis();

				Render();

				RenderSpeed = System.currentTimeMillis() - Now2;

				FPS++;
			}

			if (SecondTimer.Check())
			{

				SecondTimer.Set();

				this.TPS = TPS;
				this.FPS = FPS;

				if (PrintFPS)
				{
					System.out.println("FPS: " + FPS);
				}

				if (PrintTPS)
				{
					System.out.println("TPS: " + TPS);
				}

				if (PrintRenderSpeed)
				{
					System.out.println("RenderSpeed: " + RenderSpeed);
				}

				if (PrintTickSpeed)
				{
					System.out.println("TickSpeed: " + TickSpeed);
				}

				TPS = 0;
				FPS = 0;
			}

			Running = !EndGame();
		}

		Running = false;
	}

	public abstract void StartUp();

	public abstract void ShutDown();

	protected abstract void Tick();

	protected abstract void UpdateInput();

	protected abstract void Render();

	protected abstract boolean EndGame();

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

	public double GetTickSpeed()
	{
		return TickSpeed;
	}

	public double GetRenderSpeed()
	{
		return RenderSpeed;
	}

}
