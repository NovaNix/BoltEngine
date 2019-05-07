/*
 * 
 */
package Algorithms.Sorting;

import java.util.Comparator;

public interface Sortable <H>
{
	public ArrayList<H> GetToSort();
	public Comparator<H> GetTester();
		
	public void SetSorted(ArrayList<H> Sorted);
}
