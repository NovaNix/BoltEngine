package Tile.Storage;

import Geometry.Shapes.Shape;
import Rendering.Renderable;

public class TileMap implements Renderable
{

	Tile[][] Tiles;

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
	}

	public void SetTile(int X, int Y, Tile T)
	{
		Tiles[X][Y] = T;
	}

	public Tile GetTile(int X, int Y)
	{
		return Tiles[X][Y];
	}

	public boolean CollidesWith(Shape Collision)
	{
		int XSize = Tiles.length;
		int YSize = Tiles[0].length;

		for (int x = 0; x < XSize; x++)
		{
			for (int y = 0; y < YSize; y++)
			{
				Shape S = Tiles[x][y].GetCollision();

				if (S != null)
				{
					if (Collision.CollidesWith(S))
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public void Render()
	{
		int XSize = Tiles.length;
		int YSize = Tiles[0].length;

		for (int x = 0; x < XSize; x++)
		{
			for (int y = 0; y < YSize; y++)
			{
				Tiles[x][y].Render();

				// Shape Coll = Tiles[x][y].GetCollision();
				//
				// if (Coll != null)
				// {
				// Coll.Render();
				// }
			}
		}

	}

}
