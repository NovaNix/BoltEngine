/*
 *
 */
package Algorithms.Sorting;

import java.util.ArrayList;
import java.util.Comparator;

import Algorithms.Algorithm;

public abstract class SortingAlgorithm <H> extends Algorithm
{

	boolean Done = false;

	ArrayList<H> ToSort;
	Comparator<H> Testing;

	ArrayList<H> Sorted;

	Sortable Sorting = null;

	public SortingAlgorithm(ArrayList<H> ToSort, Comparator<H> Testing)
	{
		this.ToSort = ToSort;
		this.Testing = Testing;
	}

	public SortingAlgorithm(Sortable<H> Sort)
	{
		this.ToSort = Sort.GetToSort();
		this.Testing = Sort.GetTester();
	
		this.Sorting = Sort;
	}

	@Override
	public Object[] GetResult()
	{
		if (Done)
		{
			return new Object[] {Sorted};
		}
		
		return null;
	}

	@Override
	public void run()
	{
		Sorted = Sort(ToSort, Testing);
		
		if (Sorting != null)
		{
			SetSorted(Sorted);
		}
		
		Done = true;
	}

	public abstract <H> ArrayList<H> Sort(ArrayList<H> ToSort, Comparator<H> Testing);

}
