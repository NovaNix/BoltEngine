public abstract class Game implements Runnable
{

	// Stores if the game is currently running
	private boolean Running = false;
	
	// These handle the speed of tick updating and rendering
	private TickRegulator TickHandler;
	private TickRegulator RenderHandler;

	// These store the number of frames and ticks run per second
	private float FPS;
	private float TPS;
	
	// These hold how fast tick and render are run
	private double TickSpeed;
	private double RenderSpeed;
	

	public Game(float Tps)
	{
	
	}
	
	public Game(float TPS, float MaxFPS)
	{

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
		
		while (Running)
		{
			// UPDATE TICKHANDLER AND RENDERHANDLER
		}
		
		Running = false;
	}
	
	public abstract void StartUp();
	public abstract void ShutDown();
	
	private abstract void Tick();
	private abstract void UpdateInput();
	private abstract void Render();
	
	public void CapFPS(float FPS)
	{

	}
	
	public void UnCapFPS()
	{
	
	}

}
