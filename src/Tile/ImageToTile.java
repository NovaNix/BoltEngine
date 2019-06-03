package Tile;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

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
