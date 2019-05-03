/*
 *
 */
package Rendering.Masking;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import Rendering.OpenGLTest.Texture;
import Vectors.Vector2f;

public class Mask extends Texture
{

	boolean Inverted = false;

	BufferedImage MaskTexture;

	public Mask(BufferedImage MaskingTexture)
	{
		super(GenerateMask(MaskingTexture, false), new Vector2f(MaskingTexture.getWidth(), MaskingTexture.getHeight()));

		this.MaskTexture = MaskingTexture;
	}

	public Mask(BufferedImage MaskingTexture, boolean Inverted)
	{
		super(GenerateMask(MaskingTexture, Inverted), new Vector2f(MaskingTexture.getWidth(), MaskingTexture.getHeight()));

		this.MaskTexture = MaskingTexture;
		this.Inverted = Inverted;
	}

	public static ByteBuffer GenerateMask(BufferedImage Mask, boolean Inverted)
	{
		ByteBuffer Buffer = BufferUtils.createByteBuffer(Mask.getWidth() * Mask.getHeight() * 4);

		for (int y = 0; y < Mask.getHeight(); y++)
		{
			for (int x = 0; x < Mask.getWidth(); x++)
			{
				Color PixelColor = new Color(Mask.getRGB(x, y));

				if (Inverted)
				{
					// Put all red because R, G, and B should all be the same

					// The "255 - " is there to invert the colors

					Buffer.put((byte) (255 - PixelColor.getRed()));
					Buffer.put((byte) (255 - PixelColor.getRed()));
					Buffer.put((byte) (255 - PixelColor.getRed()));
					Buffer.put((byte) 255);
				}

				else
				{
					// Put all red because R, G, and B should all be the same

					Buffer.put((byte) PixelColor.getRed());
					Buffer.put((byte) PixelColor.getRed());
					Buffer.put((byte) PixelColor.getRed());
					Buffer.put((byte) 255);
				}
			}
		}

		Buffer.flip();

		return Buffer;
	}

}
