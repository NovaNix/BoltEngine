package AI;

import AI.AIActions.AIAction;
import Utils.Tickable;

public class AI implements Tickable
{

	AIAction Action;
	
	public AI()
	{
		
	}
	
	public void SetAction(AIAction Action)
	{
		this.Action = Action;
	}

	@Override
	public void Tick() 
	{
		if (Action != null)
		{
			Action.Tick();
		}
	}
	
}
