/*
 * 
 */
package Rendering.Masking;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import Vectors.Vector2f;

public class Mask
{

	boolean Inverted = false;

	Image MaskTexture;

	public Mask(Image MaskingTexture)
	{
		this.MaskTexture = MaskingTexture;
	}

	public Mask(Image MaskingTexture, boolean Inverted)
	{
		this.MaskTexture = MaskingTexture;
		this.Inverted = Inverted;
	}

	public Image ApplyMask(Image Original)
	{
		BufferedImage NewImage = new BufferedImage(Original.getWidth(null), Original.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		BufferedImage BufferedOriginal = new BufferedImage(Original.getWidth(null), Original.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		if (Original instanceof BufferedImage)
		{
			BufferedOriginal = (BufferedImage) Original;
		}

		else
		{
			BufferedOriginal = new BufferedImage(Original.getWidth(null), Original.getHeight(null), BufferedImage.TYPE_INT_ARGB);

			// Draw the image on to the buffered image
			Graphics2D bGr = BufferedOriginal.createGraphics();
			bGr.drawImage(Original, 0, 0, null);
			bGr.dispose();
		}

		for (int x = 0; x < NewImage.getWidth(); x++)
		{
			for (int y = 0; y < NewImage.getHeight(); y++)
			{
				Color NewColor = new Color(BufferedOriginal.getRGB(x, y));

				byte Value = (byte) Math.sqrt(NewColor.getRed() * NewColor.getRed() * .241 + NewColor.getGreen() * NewColor.getGreen() * .691 + NewColor.getBlue() * NewColor.getBlue() * .068);

				int Transparency = Value + NewColor.getAlpha();

				if (Transparency > 255)
				{
					Transparency = 255;
				}

				if (Inverted)
				{
					Transparency = 255 - Transparency;
				}

				Color MaskedColor = new Color(NewColor.getRed(), NewColor.getGreen(), NewColor.getBlue(), Transparency);

				NewImage.setRGB(x, y, MaskedColor.getRGB());
			}
		}

		return NewImage;
	}

	public Image ApplyMask(Image Original, Vector2f Position, Vector2f Scale)
	{
		BufferedImage NewImage = new BufferedImage(Original.getWidth(null), Original.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		BufferedImage BufferedOriginal = new BufferedImage(Original.getWidth(null), Original.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		if (Original instanceof BufferedImage)
		{
			BufferedOriginal = (BufferedImage) Original;
		}

		else
		{
			BufferedOriginal = new BufferedImage(Original.getWidth(null), Original.getHeight(null), BufferedImage.TYPE_INT_ARGB);

			// Draw the image on to the buffered image
			Graphics2D bGr = BufferedOriginal.createGraphics();
			bGr.drawImage(Original, 0, 0, null);
			bGr.dispose();
		}

		for (int x = 0; x < NewImage.getWidth(); x++)
		{
			for (int y = 0; y < NewImage.getHeight(); y++)
			{
				Color NewColor = new Color(BufferedOriginal.getRGB(x, y));

				// Color MaskColor = new Color();

				byte Value = (byte) Math.sqrt(NewColor.getRed() * NewColor.getRed() * .241 + NewColor.getGreen() * NewColor.getGreen() * .691 + NewColor.getBlue() * NewColor.getBlue() * .068);

				int Transparency = Value + NewColor.getAlpha();

				if (Transparency > 255)
				{
					Transparency = 255;
				}

				if (Inverted)
				{
					Transparency = 255 - Transparency;
				}

				Color MaskedColor = new Color(NewColor.getRed(), NewColor.getGreen(), NewColor.getBlue(), Transparency);

				NewImage.setRGB(x, y, MaskedColor.getRGB());
			}
		}

		return NewImage;
	}

}
