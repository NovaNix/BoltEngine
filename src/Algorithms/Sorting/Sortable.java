/*
 * 
 */
package Algorithms.Sorting;

import java.util.Comparator;

public interface Sortable
{

	public <H> void Sort(Algorithm Alg)
	{
		
	}
		
	
	public <H> void Sort(SortingAlgorithm Algorithm, Comparator<H> Tester);

}
