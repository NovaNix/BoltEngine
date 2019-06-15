package Tile.Storage;

import java.util.ArrayList;

import Geometry.Shapes.Shape;
import Rendering.Handling.Renderable;

@SuppressWarnings("rawtypes")
public class TileMap implements Renderable
{

	Tile[][] Tiles;

	ArrayList<Tile> NonNullTiles = new ArrayList<Tile>();
	ArrayList<Tile> PhysicalTiles = new ArrayList<Tile>();

	int OriginType;

	public static final int ORIGIN_TOP_LEFT = 0;
	public static final int ORIGIN_TOP_RIGHT = 1;
	public static final int ORIGIN_BOTTEM_LEFT = 2;
	public static final int ORIGIN_BOTTEM_RIGHT = 3;

	public static final int ORIGIN_CENTER = 4;

	public TileMap(int Width, int Height, int OriginType)
	{
		Tiles = new Tile[Width][Height];
		this.OriginType = OriginType;

		NonNullTiles = GetNonNullTiles(Tiles);
		PhysicalTiles = GetPhysicalTiles(Tiles);
	}

	public static ArrayList<Tile> GetNonNullTiles(Tile[][] Tile)
	{
		ArrayList<Tile> NonNull = new ArrayList<Tile>();

		int XSize = Tile.length;
		int YSize = Tile[0].length;

		for (int x = 0; x < XSize; x++)
		{
			for (int y = 0; y < YSize; y++)
			{
				if (Tile[x][y] != null)
				{
					NonNull.add(Tile[x][y]);
				}

				else
				{
					// System.out.println("Non Null Found at " + x + ", " + y);
				}
			}
		}

		return NonNull;
	}

	public static ArrayList<Tile> GetPhysicalTiles(Tile[][] Tile)
	{
		ArrayList<Tile> NonNull = new ArrayList<Tile>();

		int XSize = Tile.length;
		int YSize = Tile[0].length;

		for (int x = 0; x < XSize; x++)
		{
			for (int y = 0; y < YSize; y++)
			{
				if (Tile[x][y] != null)
				{
					if (Tile[x][y].GetCollision() != null)
					{
						NonNull.add(Tile[x][y]);
					}
				}

				else
				{
					// System.out.println("Non Null Found at " + x + ", " + y);
				}
			}
		}

		return NonNull;
	}

	public void SetTile(int X, int Y, Tile T)
	{
		Tiles[X][Y] = T;

		if (T != null)
		{
			NonNullTiles.add(T);

			if (T.GetCollision() != null)
			{
				PhysicalTiles.add(T);
			}
		}

		else
		{
			// System.out.println("Non Null Found at " + X + ", " + Y);
		}
	}

	public Tile GetTile(int X, int Y)
	{
		return Tiles[X][Y];
	}

	public boolean CollidesWith(Shape Collision)
	{
		for (int i = 0; i < PhysicalTiles.size(); i++)
		{
			if (Collision.CollidesWith(PhysicalTiles.get(i).GetCollision()))
			{
				return true;
			}
		}

		return false;
	}

	public int GetWidth()
	{
		return Tiles.length;
	}
	
	public int GetHeight()
	{
		return Tiles[0].length;
	}
	
	@Override
	public void Render()
	{
		for (int i = 0; i < NonNullTiles.size(); i++)
		{
			NonNullTiles.get(i).Render();
		}

	}

}
