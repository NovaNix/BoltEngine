package Engine.Debugging;

import Engine.Game;
import IO.InputManager;
import Rendering.Window;
import Rendering.Cameras.Camera;

public abstract class TechDemo extends Game
{

	protected Window Win;
	protected Camera Cam;
	
	String DemoName;
	
	public TechDemo(String DemoName, float Tps) 
	{
		super(Tps);
		
		this.DemoName = DemoName;
	}

	@Override
	public void StartUp() {
		
		Win = new Window(DemoName);
		
		Cam = new Camera();
		
		Win.AddCamera(Cam);

		Win.Show();
		
		InputManager.Init(Win.GetHandle());
		
		Boot();
	}

	public abstract void Boot();
	
	@Override
	public void ShutDown() 
	{
		System.exit(0);
	}

	@Override
	protected void Render() 
	{
		Win.Render();
	}

	@Override
	protected boolean EndGame()
	{
		return Win.ShouldClose();
	}

}
