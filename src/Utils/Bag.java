package Utils;


public class Bag <H>
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

}
