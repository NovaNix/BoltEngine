package Storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import Engine.BoltMath;

// LoopedList acts as an ArrayList, but when a index goes outside of its range it wraps around
public class LoopedList<E> extends ArrayList<E>
{

	private static final long serialVersionUID = -4873773011572105942L;

	public LoopedList()
	{
		super();
	}

	public LoopedList(Collection<? extends E> c)
	{
		super(c);
	}

	public LoopedList(int initialCapacity)
	{
		super(initialCapacity);
	}

	// Returns the ArrayList location of the specified location
	private int LoopedLocation(int Location)
	{
		return BoltMath.mod(Location, size());
	}

	@Override
	public void add(int index, E element)
	{
		super.add(LoopedLocation(index), element);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c)
	{
		return super.addAll(LoopedLocation(index), c);
	}

	@Override
	public E get(int index)
	{
		return super.get(LoopedLocation(index));
	}

	@Override
	public ListIterator<E> listIterator(int index)
	{
		return super.listIterator(LoopedLocation(index));
	}

	@Override
	public E remove(int index)
	{
		return super.remove(LoopedLocation(index));
	}

	@Override
	public E set(int index, E element)
	{
		return super.set(LoopedLocation(index), element);
	}

	// NOTE! THe subList produced does not update with the list due to potential
	// issues with overlap
	// This may be changed later

	@Override
	public List<E> subList(int fromIndex, int toIndex)
	{
		List<E> SubList = new ArrayList<E>();

		for (int i = fromIndex; i < toIndex; i++)
		{
			SubList.add(get(i));
		}

		return SubList;
	}

}
