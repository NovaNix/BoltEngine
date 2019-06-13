package Storage;

import java.util.ArrayList;

public class Bag<H>
{

	ArrayList<H> Items = new ArrayList<H>();

	public Bag()
	{

	}

	public void Add(H Item)
	{
		Items.add(Item);
	}

	public H Grab()
	{
		return Items.get(0);
	}

	public H Take()
	{
		H Item = Items.get(0);
		Items.remove(Item);
		return Item;
	}

	public int Size()
	{
		return Items.size();
	}

	public Bag<H> Clone()
	{
		Bag<H> Bag = new Bag<H>();

		for (int i = 0; i < Items.size(); i++)
		{
			Bag.Add(Items.get(i));
		}

		return Bag;
	}

}
