/*
 * 
 */
package Random;

import java.util.ArrayList;
import java.util.Random;

public class RandomBag<H>
{

	ArrayList<H> Items = new ArrayList<H>();

	float ProbabilityPerItem = 100f;

	public RandomBag()
	{

	}

	public void AddItem(H Item)
	{
		Items.add(Item);

		CalculateProbability();
	}

	public void RemoveItem(H Item)
	{
		Items.remove(Item);

		CalculateProbability();
	}

	public H Grab()
	{
		H ReturnedItem = GetRandomItem();

		return ReturnedItem;
	}

	public H Take()
	{
		H ReturnedItem = GetRandomItem();

		RemoveItem(ReturnedItem);

		return ReturnedItem;
	}

	public float CalculateProbability()
	{
		return 100f / Items.size();
	}

	public int GetSize()
	{
		return Items.size();
	}

	public float GetProbability()
	{
		return ProbabilityPerItem;
	}

	private H GetRandomItem()
	{
		int Total = Items.size();

		Random Ran = new Random();

		int PickedItemSlot = Ran.nextInt(Total);
		;

		H PickedItem = Items.get(PickedItemSlot - 1);

		return PickedItem;

	}

	public RandomBag<H> Clone()
	{
		RandomBag<H> Bag = new RandomBag<H>();

		for (int i = 0; i < Items.size(); i++)
		{
			Bag.AddItem(Items.get(i));
		}

		return Bag;
	}

}
