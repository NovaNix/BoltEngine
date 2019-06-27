/*
 *
 */
package Rendering.Image;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import Rendering.OpenGL.Shader;
import Vectors.Vector2f;

public class Sprite extends Graphic
{
	static Shader DrawShader;

	Vector2f Size;
	int[][] Pixels;

	Texture PixelTexture;
	Texture ColorTexture;

	ColorPalette Colors;

	public Sprite(BufferedImage Template)
	{
		BufferedImage Temp = Template;
		this.Size = new Vector2f(Temp.getWidth(), Temp.getHeight());
		this.Colors = ColorPalette.GeneratePalette(Template);
		this.Pixels = GenerateCompressedPixels(Template, Colors);

		this.PixelTexture = GeneratePixelTexture(Temp, Colors, Size);
		this.ColorTexture = GenerateColorTexture(Colors);
	}

	public static int[][] GenerateCompressedPixels(BufferedImage Template, ColorPalette Colors)
	{
		BufferedImage ReadableImage = Template;

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

	public static Texture GeneratePixelTexture(BufferedImage Template, ColorPalette Colors, Vector2f Size)
	{
		ByteBuffer Buffer = BufferUtils.createByteBuffer((int) (Size.GetX() * Size.GetY() * 4));

		System.out.println("Generating New Sprite");

		for (int y = 0; y < Size.GetY(); y++)
		{
			for (int x = 0; x < Size.GetX(); x++)
			{
				Color PixelColor = new Color(Template.getRGB(x, y), true);

				int ID = Colors.GetColorID(PixelColor);

				// System.out.println("Color ID: " + ID);

				int RColor = 0;
				int GColor = 0;
				int BColor = 0;
				int AColor = 0;

				ID++;

				if (ID <= 255)
				{
					RColor = ID;
				}

				else if (ID > 255 && ID <= 255 * 2)
				{
					RColor = 255;
					GColor = ID - 255;
				}

				else if (ID > 255 * 2 && ID <= 255 * 3)
				{
					RColor = 255;
					GColor = 255;
					BColor = ID - (255 * 2);
				}

				else
				{
					RColor = 255;
					GColor = 255;
					BColor = 255;
					AColor = ID - (255 * 3);
				}

				// System.out.println("RColor: " + RColor);
				// System.out.println("GColor: " + GColor);
				// System.out.println("BColor: " + BColor);
				// System.out.println("AColor: " + AColor);

				Buffer.put((byte) RColor);
				Buffer.put((byte) GColor);
				Buffer.put((byte) BColor);
				Buffer.put((byte) AColor);

				// System.out.println();
			}
		}

		Buffer.flip();

		return new Texture(Buffer, Size);
	}

	public static Texture GenerateColorTexture(ColorPalette Colors)
	{
		ByteBuffer Buffer = BufferUtils.createByteBuffer(Colors.GetColorCount() * 4);

		for (int i = 0; i < Colors.GetColorCount(); i++)
		{

			Color PixelColor = Colors.GetColorAtID(i);

			Buffer.put((byte) PixelColor.getRed());
			Buffer.put((byte) PixelColor.getGreen());
			Buffer.put((byte) PixelColor.getBlue());
			Buffer.put((byte) PixelColor.getAlpha());

		}

		Buffer.flip();

		return new Texture(Buffer, new Vector2f(Colors.GetColorCount(), 1));
	}

	public void SwitchToPalette(ColorPalette Colors)
	{
		this.Colors = Colors;
		this.ColorTexture = GenerateColorTexture(Colors);
	}

	public ColorPalette GetPalette()
	{
		return Colors;
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
	} // TODO Auto-generated method stub

	@Override
	public void BindGraphic()
	{
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, PixelTexture.GetID());
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, ColorTexture.GetID());
	}

	@Override
	public void BindShader()
	{

		if (DrawShader == null)
		{
			DrawShader = new Shader("/vertexshaders/defaultshader.vert", true, "/fragmentshaders/drawsprite.frag", true);
		}

		DrawShader.Bind();

		DrawShader.SetUniform("Texture1", 0);
		DrawShader.SetUniform("Texture2", 1);

		DrawShader.SetUniform("ColorCount", Colors.GetColorCount());

		// System.out.println("Color Count: " + Colors.GetColorCount());
	}

	@Override
	public Shader GetDrawingShader()
	{
		return DrawShader;
	}

}
