package Algorithms.Sorting;

import java.util.ArrayList;
import java.util.Comparator;

public class Merge extends SortingAlgorithm
{

	@Override
	public <H> ArrayList<H> Sort(ArrayList<H> ToSort, Comparator<H> Testing)
	{
		ArrayList<ArrayList<H>> Groups = BreakUp(ToSort);
		
		boolean Sorted = false;
		
		// Check if done
		
		if (Groups.size() == 1)
		{
			Sorted = true;
		}
		
		while (!Sorted)
		{
			ArrayList<ArrayList<H>> SortedGroups = new ArrayList<ArrayList<H>>();
			
			// Handle Odd Numbers
			
			if (Groups.size() % 2 != 0)
			{
				ArrayList<ArrayList<H>> EvenGroups = new ArrayList<ArrayList<H>>();
				
				EvenGroups.add(MergeGroups(Groups.get(0), Groups.get(1), Testing));
				
				for (int i = 2; i < Groups.size(); i++)
				{
					EvenGroups.add(Groups.get(i));
				}
				
				Groups = EvenGroups;
			}
			
			for (int i = 0; i < Groups.size(); i += 2)
			{
				SortedGroups.add(MergeGroups(Groups.get(i), Groups.get(i + 1), Testing));
			}
			
			Groups = SortedGroups;
			
			// Check if done
			
			if (Groups.size() == 1)
			{
				Sorted = true;
			}
		}
		
		// Returns Sorted List
		
		return Groups.get(0);
	}
	
	private static <H> ArrayList<ArrayList<H>> BreakUp(ArrayList<H> ToBreak)
	{
		
		ArrayList<ArrayList<H>> BrokenUp = new ArrayList<ArrayList<H>>();
		
		for (int i = 0; i < ToBreak.size(); i++)
		{
			ArrayList<H> Broken = new ArrayList<H>(); 
			
			Broken.add(ToBreak.get(i));
			
			BrokenUp.add(Broken);
		}
		
		return BrokenUp;
	}
	
	private static <H> ArrayList<H> MergeGroups(ArrayList<H> ToMerge1, ArrayList<H> ToMerge2, Comparator<H> Testing)
	{
		int Pointer1 = 0;
		int Pointer2 = 0;
		
		ArrayList<H> MergedList = new ArrayList<H>(); 
		
		boolean Merged = false;
		
		while (!Merged)
		{
			if (Testing.compare(ToMerge1.get(Pointer1), ToMerge2.get(Pointer2)) == 1)
			{
				MergedList.add(ToMerge1.get(Pointer1));
				
				Pointer1++;
			}
			
			else if (Testing.compare(ToMerge1.get(Pointer1), ToMerge2.get(Pointer2)) == 0) 
			{
				MergedList.add(ToMerge1.get(Pointer1));
				MergedList.add(ToMerge2.get(Pointer2));
				
				Pointer1++;
				Pointer2++;
			}
			
			else if (Testing.compare(ToMerge1.get(Pointer1), ToMerge2.get(Pointer2)) == -1) 
			{
				MergedList.add(ToMerge2.get(Pointer2));
				
				Pointer2++;
			}
			
			if (Pointer1 == ToMerge1.size())
			{
				for (int i = Pointer2; i < ToMerge2.size(); i++)
				{
					MergedList.add(ToMerge2.get(i));
				}
				
				Merged = true;
			}
			
			else if (Pointer2 == ToMerge2.size())
			{
				for (int i = Pointer1; i < ToMerge1.size(); i++)
				{
					MergedList.add(ToMerge1.get(i));
				}
				
				Merged = true;
			}
			
		}
		
		return MergedList;
		
	}
	
}
