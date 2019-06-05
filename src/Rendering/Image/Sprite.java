/*
 *
 */
package Rendering.Image;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import Vectors.Vector2f;

public class Sprite
{
	Vector2f Size;
	int[][] Pixels;

	Texture Compiled;
	ColorPalette Colors;

	public Sprite(Image Template)
	{
		BufferedImage Temp = (BufferedImage) Template;
		this.Size = new Vector2f(Temp.getWidth(), Temp.getHeight());
		this.Colors = ColorPalette.GeneratePalette(Template);
		this.Pixels = GenerateCompressedPixels(Template, Colors);
	}

	public Sprite(Vector2f Size, int[] PixelData, ColorPalette Colors)
	{
		this.Size = Size;

		int Pointer = 0;

		for (int y = 0; y < (int) Size.GetY(); y++)
		{
			for (int x = 0; x < (int) Size.GetX(); x++)
			{
				Pixels[x][y] = PixelData[Pointer];
				Pointer++;
			}
		}

		this.Colors = Colors;

	}

	public Sprite(Vector2f Size, int[][] ColorIDs, ColorPalette Colors)
	{
		this.Size = Size;
		this.Pixels = ColorIDs;
		this.Colors = Colors;
	}

	public static int[][] GenerateCompressedPixels(Image Template, ColorPalette Colors)
	{
		BufferedImage ReadableImage = (BufferedImage) Template;

		int[][] CompressedPixels = new int[ReadableImage.getWidth()][ReadableImage.getHeight()];

		for (int x = 0; x < ReadableImage.getWidth(); x++)
		{
			for (int y = 0; y < ReadableImage.getHeight(); y++)
			{
				CompressedPixels[x][y] = Colors.GetColorID(new Color(ReadableImage.getRGB(x, y), true));
			}
		}

		return CompressedPixels;
	}

	private void CompileImage()
	{
		BufferedImage Compiled = new BufferedImage((int) Size.GetX(), (int) Size.GetY(), BufferedImage.TYPE_4BYTE_ABGR);

		for (int x = 0; x < Compiled.getWidth(); x++)
		{
			for (int y = 0; y < Compiled.getHeight(); y++)
			{
				Compiled.setRGB(x, y, Colors.GetColorAtID(Pixels[x][y]).getRGB());
			}
		}

		this.Compiled = new Texture(Compiled);
	}

	public void SwitchToPalette(ColorPalette Colors)
	{
		this.Colors = Colors;
	}

	public ColorPalette GetPalette()
	{
		return Colors;
	}

	public Texture GetImage()
	{
		this.CompileImage();

		return Compiled;
	}

	public int[] GetPixelData()
	{
		int[] PixelData = new int[(int) Size.GetX() * (int) Size.GetY() * 4];

		int Pointer = 0;

		for (int y = 0; y < (int) Size.GetY(); y++)
		{
			for (int x = 0; x < (int) Size.GetX(); x++)
			{
				PixelData[Pointer] = Pixels[x][y];
				Pointer++;
			}
		}

		return PixelData;
	}

	public void Print()
	{
		for (int x = 0; x < (int) Size.GetX(); x++)
		{
			for (int y = 0; y < (int) Size.GetY(); y++)
			{
				System.out.println("X: " + x + ", " + "Y:" + y + ", Color ID: " + Pixels[x][y]);
			}
		}
	}
}
