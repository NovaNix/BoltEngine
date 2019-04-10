/*
 * 
 */
package Utils;

import java.util.ArrayList;
import java.util.HashMap;

import Vectors.Vector2f;

public class ChunkArray <H>
{

	ArrayList<Vector2f> Pointers = new ArrayList<Vector2f>();
	
	HashMap<Vector2f, H> Chunks = new HashMap<Vector2f, H>();
	
	int Distance = 0;
	
	boolean AutoClean = false;

	public ChunkArray(boolean AutoClean, int Distance)
	{
		this.AutoClean = AutoClean;
		this.Distance = Distance;
	}
	
	public void AddPointer(Vector2f Pointer)
	{
		Pointers.add(Pointer);
	}
	
	public void Move(int Pointer, Vector2f Amount)
	{
		Pointers.get(Pointer).Add(Amount);
		
		if (AutoClean)
		{
			Clean();
		}
	}
	
	public void Set(int Pointer, Vector2f Position)
	{
		Pointers.set(Pointer, Position);
		
		if (AutoClean)
		{
			Clean();
		}
	}
	
	public void Set(Vector2f Pointer, Vector2f Position)
	{
		int Slot = 0;
		
		for (int i = 0; i < Pointers.size(); i++)
		{
			if (Pointers.get(i).equals(Position))
			{
				Slot = i;
				
				break;
			}
		}
		
		Pointers.set(Slot, Position);
		
		if (AutoClean)
		{
			Clean();
		}
	}
	
	public Vector2f GetPointer(int Pointer)
	{
		return Pointers.get(Pointer);
	}
	
	public void RemovePointer(int Pointer)
	{
		Pointers.remove(Pointer);
	}
	
	public void RemovePointer(Vector2f Pointer)
	{
		Pointers.remove(Pointer);
	}
	
	public void Clean()
	{
		synchronized (Chunks)
		{
			ArrayList<Vector2f> ToRemove = new ArrayList<Vector2f>();
			
			for (Vector2f Key : Chunks.keySet())
			{
				if (!InRange(Key))
				{
					ToRemove.add(Key);
				}
			}
			
			for (int i = 0; i < ToRemove.size(); i++)
			{
				Chunks.remove(ToRemove.get(i));
			}
		}

	}
	
	public void Load(H Chunk, Vector2f Position)
	{
		synchronized (Chunks)
		{
			Chunks.putIfAbsent(Position, Chunk);
		}
	}
	
	public ArrayList<Vector2f> GetNeeded()
	{
		synchronized (Chunks)
		{
			ArrayList<Vector2f> NeededChunks = new ArrayList<Vector2f>();
			for (int i = 0; i < Pointers.size(); i++)
			{
				for (int x = (int) Pointers.get(i).GetX() - Distance; x < Pointers.get(i).GetX() + Distance; x++)
				{
					for (int y = (int) Pointers.get(i).GetY() - Distance; y < Pointers.get(i).GetY() + Distance; y++)
					{
						Vector2f ChunkPosition = new Vector2f(x, y);
						
						if (!Has(ChunkPosition))
						{
							if (!NeededChunks.contains(ChunkPosition))
							{
								NeededChunks.add(new Vector2f(x, y));
							}
						}
					}
				}
			}
			
			return NeededChunks;
		}
		
	}
	
	public boolean InRange(Vector2f Position)
	{
		for (int i = 0; i < Pointers.size(); i++)
		{
			if (Pointers.get(i).GetDistanceTo(Position) <= Distance)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean Has(Vector2f Position)
	{
		return Chunks.containsKey(Position);
	}
	
	public H GetChunk(Vector2f Position)
	{
		return Chunks.get(Position);
	}
	
	public ArrayList<H> GetAllChunks()
	{
		synchronized (Chunks) 
		{
			return new ArrayList<H>(Chunks.values());
		}
	}
	
	public int GetNumberOfLoadedChunks()
	{
		return Chunks.keySet().size();
	}
	
}
