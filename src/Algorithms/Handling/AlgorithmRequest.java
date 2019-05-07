/*
 *
 */
package Algorithms.Handling;

import Algorithms.Algorithm;
import Threading.Task;

public class AlgorithmRequest extends Task
{

	// boolean IsDone = false;

	Algorithm Alg;

	public AlgorithmRequest(Algorithm Alg)
	{
		super("Algorithm Request", Alg);

		this.Alg = Alg;
	}

	public Object[] GetResult()
	{
		return Alg.GetResult();
	}

	public boolean IsDone()
	{
		return IsDone();
	}

}
