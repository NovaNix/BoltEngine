package Rendering.Screens;

import java.awt.BorderLayout;

import Rendering.Window;
import Rendering.WindowScreen;
import Rendering.Cameras.Camera;
import Rendering.Exceptions.ExcessCamerasException;

public class SplitScreen extends WindowScreen
{

	public SplitScreen(Window ConnectedWindow)
	{
		super(ConnectedWindow);
		
		SimulatedEnviroment.setLayout(new BorderLayout());
	}

	@Override
	public void AddCamera(Camera Cam) throws ExcessCamerasException
	{
		if (Cameras.size() == 0)
		{
			Cameras.add(Cam);
			
			SimulatedEnviroment.add(Cam, BorderLayout.WEST);
			
			SimulatedEnviroment.pack();
			
			Update();
		}
		
		else if (Cameras.size() == 1)
		{
			Cameras.add(Cam);
			
			SimulatedEnviroment.add(Cam, BorderLayout.EAST);
			
			SimulatedEnviroment.pack();
			
			Update();
		}
		
		else
		{
			throw new ExcessCamerasException();
		}
	}

}
