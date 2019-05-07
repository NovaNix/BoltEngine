/*
 * 
 */
package Algorithms.Handling;

import Utils.Script;

public class AlgorithmRequest extends Task implements Runnable
{

	boolean IsDone = false;

	Algorithm Alg;
	
	public AlgorithmRequest(Algorithm Alg)
	{
		super("Algorithm Request", this);
	
		this.Alg = Alg;
	}
	
	@Override
	public void run()
	{
		Alg.run();
		IsDone = true;
	}

	protected void Finish()
	{
		IsDone = true;
	}

	public Object[] GetResult()
	{
		return Alg.GetResut();
	}

	public boolean IsDone()
	{
		return IsDone();
	}

}
