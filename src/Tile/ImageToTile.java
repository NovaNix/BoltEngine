package Tile;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import IO.File.EasyLoader;
import Tile.Storage.Tile;
import Tile.Storage.TileMap;
import Vectors.Vector2f;

public class ImageToTile
{

	public static <H> H[][] LoadTiles(HashMap<Color, H> Key, BufferedImage TileImage, H[][] TileArray)
	{
		Color[][] Colors = GetColors(TileImage);

		if (TileArray.length != Colors.length || TileArray[0].length != TileArray[0].length)
		{
			System.out.println("Invalid TileArray Lengths!");
		}

		for (int x = 0; x < TileImage.getWidth(); x++)
		{
			for (int y = 0; y < TileImage.getHeight(); y++)
			{
				TileArray[x][y] = Key.get(Colors[x][y]);
			}
		}

		return TileArray;
	}

	public static TileMap LoadTiles(HashMap<Color, Tile> Key, BufferedImage TileImage)
	{
		Color[][] Colors = GetColors(TileImage);

		TileMap Tiles = new TileMap(TileImage.getWidth(), TileImage.getHeight(), TileMap.ORIGIN_TOP_LEFT);

		for (int x = 0; x < TileImage.getWidth(); x++)
		{
			for (int y = 0; y < TileImage.getHeight(); y++)
			{
				Tiles.SetTile(x, y, GetTileAt(x, y, Key, Colors[x][y]));
			}
		}

		return Tiles;
	}

	public static TileMap LoadTiles(HashMap<Color, Tile> Key, String TileImagePath)
	{
		BufferedImage TileImage = EasyLoader.LoadLocalImage(TileImagePath);

		return LoadTiles(Key, TileImage);
	}

	private static Tile GetTileAt(int x, int y, HashMap<Color, Tile> Key, Color Col)
	{

		// System.out.println("Color = " + Col.getRed() + ", " + Col.getGreen() + ", " +
		// Col.getBlue() + ", " + Col.getAlpha() + ", Coord = " + x + ", " + y);

		if (Key.containsKey(Col))
		{
			if (Key.get(Col) != null)
			{
				return new Tile(Key.get(Col), new Vector2f(x, y));
			}
		}

		return null;
	}

	private static Color[][] GetColors(BufferedImage TileImage)
	{
		int Width = TileImage.getWidth();
		int Height = TileImage.getHeight();

		Color[][] Colors = new Color[Width][Height];

		for (int x = 0; x < Width; x++)
		{
			for (int y = 0; y < Height; y++)
			{
				Colors[x][y] = new Color(TileImage.getRGB(x, y));
			}
		}

		return Colors;
	}

}
