/*
 * 
 */
package Algorithms.Sorting;

import java.util.ArrayList;
import java.util.Comparator;

import Algorithms.Algorithm;

public abstract class SortingAlgorithm extends Algorithm
{

	public abstract <H> ArrayList<H> Sort(ArrayList<H> ToSort, Comparator<H> Testing);

}
