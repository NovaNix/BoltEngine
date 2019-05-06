/*
 * 
 */
package Algorithms.Sorting;

import java.util.ArrayList;
import java.util.Comparator;

import Algorithms.Algorithm;

public abstract class SortingAlgorithm implements Algorithm
{

	public Object[] Execute(Object[] Parameters)
	{
		return new Object[] {Sort(Parameters[0], Parameters[1])};
	}

	public abstract <H> ArrayList<H> Sort(ArrayList<H> ToSort, Comparator<H> Testing);

}
