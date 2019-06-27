/*
 * 
 */
package Rendering.Image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ColorPalette
{

	Color[] Colors;

	public ColorPalette(Color[] Colors)
	{
		this.Colors = Colors;
	}

	public static ColorPalette GeneratePalette(BufferedImage NewImage)
	{
		ArrayList<Color> Colors = new ArrayList<Color>();

		// System.out.println("Generating Color Palette");

		int Loop = 0;

		for (int y = 0; y < NewImage.getHeight(); y++)
		{
			for (int x = 0; x < NewImage.getWidth(); x++)
			{

				Color PixelColor = new Color(NewImage.getRGB(x, y), true);

				boolean AlreadyAdded = Colors.contains(PixelColor);

				if (!AlreadyAdded)
				{
					// System.out.println("Added Color " + PixelColor.getRed() + ", " +
					// PixelColor.getGreen() + ", " + PixelColor.getRed() + ", " +
					// PixelColor.getAlpha());
					Colors.add(PixelColor);
					Loop++;
				}

				else
				{
					// System.out.println("Duplicate");
				}
			}
		}

		System.out.println("Colors added: " + Loop);

		Color[] NewColorList = new Color[Colors.size()];

		NewColorList = Colors.toArray(NewColorList);

		return new ColorPalette(NewColorList);
	}

	public void ReplaceColorWith(Color Replaced, Color Replacement)
	{
		for (int i = 0; i < Colors.length; i++)
		{
			if (Colors[i].equals(Replaced))
			{
				Colors[i] = Replaced;
			}
		}
	}

	public boolean HasColor(Color Tested)
	{
		return GetColorID(Tested) != -1;
	}

	public Color GetColorAtID(int ID)
	{
		return Colors[ID];
	}

	public int GetColorCount()
	{
		return Colors.length;
	}

	public int GetColorID(Color Tested)
	{

		for (int i = 0; i < Colors.length; i++)
		{
			if (Colors[i].equals(Tested))
			{
				return i;
			}
		}

		return -1;
	}

	public int[] ToList()
	{
		int[] ColorList = new int[Colors.length * 4];

		for (int i = 0; i < Colors.length; i++)
		{
			Color Hue = Colors[i];

			int Position = i * 4;

			ColorList[Position] = Hue.getRed();
			ColorList[Position + 1] = Hue.getGreen();
			ColorList[Position + 2] = Hue.getBlue();
			ColorList[Position + 3] = Hue.getAlpha();
		}

		return ColorList;
	}

	public void Print()
	{
		System.out.println("Color Palette Table:" + System.lineSeparator());

		for (int i = 0; i < Colors.length; i++)
		{
			int R = Colors[i].getRed();
			int G = Colors[i].getGreen();
			int B = Colors[i].getBlue();
			int A = Colors[i].getAlpha();

			System.out.println(i + ": " + R + ", " + G + ", " + B + ", " + A);
		}

		System.out.println(System.lineSeparator());
	}
}
