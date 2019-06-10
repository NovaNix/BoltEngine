/*
 * 
 */
package Rendering.Image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class ColorPalette
{

	HashMap<Integer, Color> Colors = new HashMap<Integer, Color>();

	public ColorPalette(Color[] Colors)
	{
		for (int i = 0; i < Colors.length; i++)
		{
			this.Colors.put(i, Colors[i]);
		}
	}

	@SuppressWarnings("unchecked")
	public static ColorPalette GeneratePalette(BufferedImage NewImage)
	{
		ArrayList<Color> Colors = new ArrayList<Color>();

		@SuppressWarnings("rawtypes")
		Comparator Comparator = new Comparator()
		{

			@Override
			public int compare(Object arg0, Object arg1)
			{
				Color Color1 = (Color) arg0;
				Color Color2 = (Color) arg1;

				if (Color1.getRGB() == Color2.getRGB())
				{
					return 0;
				}

				else
				{
					return -1;
				}

			}
		};

		for (int x = 0; x < NewImage.getWidth(); x++)
		{
			for (int y = 0; y < NewImage.getHeight(); y++)
			{
				Color PixelColor = new Color(NewImage.getRGB(x, y));

				boolean AlreadyAdded = false;

				for (int i = 0; i < Colors.size(); i++)
				{
					if (Comparator.compare(PixelColor, Colors.get(i)) == 0)
					{
						AlreadyAdded = true;
						break;
					}

				}

				if (!AlreadyAdded)
				{
					Colors.add(PixelColor);
				}
			}
		}

		Color[] NewColorList = new Color[Colors.size()];

		for (int i = 0; i < Colors.size(); i++)
		{
			NewColorList[i] = Colors.get(i);
		}

		return new ColorPalette(NewColorList);
	}

	public void ReplaceColorWith(Color Replaced, Color Replacement)
	{
		for (Integer Key : Colors.keySet())
		{
			if (Colors.get(Key).equals(Replaced))
			{
				Colors.put(Key, Replacement);
			}
		}
	}

	public boolean HasColor(Color Tested)
	{
		return GetColorID(Tested) != -1;
	}

	public Color GetColorAtID(int ID)
	{
		return Colors.get(ID);
	}

	@SuppressWarnings("unchecked")
	public int GetColorID(Color Tested)
	{

		@SuppressWarnings("rawtypes")
		Comparator Comparator = new Comparator()
		{

			@Override
			public int compare(Object arg0, Object arg1)
			{
				Color Color1 = (Color) arg0;
				Color Color2 = (Color) arg1;

				if (Color1.getRGB() == Color2.getRGB())
				{
					return 0;
				}

				else
				{
					return -1;
				}

			}
		};

		for (Integer Key : Colors.keySet())
		{
			if (Comparator.compare(Tested, Colors.get(Key)) == 0)
			{
				return Key;
			}
		}

		return -1;
	}

	public int[] ToList()
	{
		int Size = Colors.keySet().size();

		int[] ColorList = new int[Size * 4];

		for (int i = 0; i < Size; i++)
		{
			Color Hue = Colors.get(i);

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

		for (Integer Key : Colors.keySet())
		{
			int R = Colors.get(Key).getRed();
			int G = Colors.get(Key).getGreen();
			int B = Colors.get(Key).getBlue();
			int A = Colors.get(Key).getAlpha();

			System.out.println(Key + ": " + R + ", " + G + ", " + B + ", " + A);
		}

		System.out.println(System.lineSeparator());
	}
}
