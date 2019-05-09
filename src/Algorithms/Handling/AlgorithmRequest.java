/*
 *
 */
package Algorithms.Handling;

import Algorithms.Algorithm;
import Threading.Request;

public class AlgorithmRequest extends Request
{

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

}
