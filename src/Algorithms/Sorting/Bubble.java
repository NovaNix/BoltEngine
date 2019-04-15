package Algorithms.Sorting;

import java.util.ArrayList;
import java.util.Comparator;

public class Bubble extends SortingAlgorithm
{
	@Override
	public <H> ArrayList<H> Sort(ArrayList<H> ToSort, Comparator<H> Testing)
	{
		boolean Sorted = false;

		ArrayList<H> Modified = new ArrayList<H>();

		for (int i = 0; i < ToSort.size(); i++)
		{
			Modified.add(ToSort.get(i));
		}

		while (!Sorted)
		{
			boolean Changed = false;

			for (int i = 0; i < Modified.size() - 1; i++)
			{
				if (Testing.compare(Modified.get(i), Modified.get(i + 1)) == 1)
				{
					H Slot1 = Modified.get(i);
					H Slot2 = Modified.get(i + 1);

					Modified.set(i, Slot2);
					Modified.set(i + 1, Slot1);

					Changed = true;
				}
			}

			if (!Changed)
			{
				Sorted = true;
			}
		}

		return Modified;
	}

}
