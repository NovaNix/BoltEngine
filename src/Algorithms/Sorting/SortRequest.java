/*
 * 
 */
package Algorithms.Sorting;

import java.util.ArrayList;
import java.util.Comparator;

import Algorithms.Handling.AlgorithmRequest;
import Utils.Script;

@SuppressWarnings("rawtypes")
public class SortRequest extends AlgorithmRequest
{
	SortingAlgorithm Algorithm;
	ArrayList ToSort;
	Comparator Testing;

	ArrayList Result;

	public <H> SortRequest(SortingAlgorithm Algorithm, ArrayList<H> ToSort, Comparator<H> Testing, Script FinishScript)
	{
		super(FinishScript);

		this.Algorithm = Algorithm;
		this.ToSort = ToSort;
		this.Testing = Testing;
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public void Run()
	{
		Result = Algorithm.Sort(ToSort, Testing);

		Finish();
	}

	@Override
	public Object GetResult()
	{
		return Result;
	}

}
