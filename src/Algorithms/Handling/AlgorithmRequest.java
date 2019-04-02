/*
 * 
 */
package Algorithms.Handling;

import Utils.Script;

public abstract class AlgorithmRequest
{

	Script FinishScript;
	
	public AlgorithmRequest(Script FinishScript)
	{
		this.FinishScript = FinishScript;
	}
	
	boolean IsDone = false;

	public abstract void Run();

	protected void Finish()
	{
		FinishScript.Run();
		
		IsDone = true;
	}
	
	public abstract Object GetResult();
	
	public boolean IsDone()
	{
		return IsDone();
	}
	
}
