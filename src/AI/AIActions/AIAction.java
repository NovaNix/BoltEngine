package AI.AIActions;

import AI.Puppetable;
import Utils.Tickable;

public abstract class AIAction implements Tickable
{

	protected Puppetable Control;
	
	protected boolean ActionDone = false;;
	
	public AIAction(Puppetable Control)
	{
		this.Control = Control;
	}
	
	public boolean ActionDone()
	{
		return ActionDone;
	}
	
}
