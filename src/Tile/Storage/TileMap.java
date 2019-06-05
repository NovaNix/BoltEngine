package Tile.Storage;

public class TileMap
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

}
