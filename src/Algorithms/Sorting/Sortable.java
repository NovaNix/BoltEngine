/*
 *
 */
package Algorithms.Sorting;

import java.util.ArrayList;
import java.util.Comparator;

public interface Sortable<H>
{
	ArrayList<H> GetToSort();

	Comparator<H> GetTester();

	void SetSorted(ArrayList<H> Sorted);
}
