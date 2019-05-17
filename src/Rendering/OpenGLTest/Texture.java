package Rendering.OpenGLTest;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_BORDER_COLOR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterfv;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;

import Rendering.Image.Sprite;
import Vectors.Vector2f;

public class Texture
{

	private static HashMap<BufferedImage, Texture> PreloadedImages = new HashMap<BufferedImage, Texture>();
	private static HashMap<Sprite, Texture> PreloadedSprites = new HashMap<Sprite, Texture>();

	ByteBuffer Data;

	int TextureID;

	Vector2f Size;

	public Texture(BufferedImage Convert)
	{
		if (PreloadedImages.containsKey(Convert))
		{
			this.Data = PreloadedImages.get(Convert).GetData();
		}

		else
		{
			this.Size = new Vector2f(Convert.getWidth(), Convert.getHeight());

			ByteBuffer ColorData = ExtractColorData(Convert);

			TextureID = glGenTextures();

			GenerateTexture(ColorData, Size, TextureID);

			Data = ColorData;

			PreloadedImages.put(Convert, this);
		}
	}

	public Texture(Sprite Convert)
	{

	}

	public Texture(ByteBuffer Data, Vector2f Size)
	{
		this.Size = Size;

		TextureID = glGenTextures();

		GenerateTexture(Data, Size, TextureID);

		this.Data = Data;
	}

	private void GenerateTexture(ByteBuffer Data, Vector2f Size, int ID)
	{
		glBindTexture(GL_TEXTURE_2D, ID);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		float[] borderColor = { 1.0f, 1.0f, 0.0f, 1.0f };
		glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, (int) Size.GetX(), (int) Size.GetY(), 0, GL_RGBA, GL_UNSIGNED_BYTE, Data);
		glGenerateMipmap(GL_TEXTURE_2D);
	}

	public static ByteBuffer ExtractColorData(BufferedImage Extract)
	{
		ByteBuffer Buffer = BufferUtils.createByteBuffer(Extract.getWidth() * Extract.getHeight() * 4);

		for (int y = 0; y < Extract.getHeight(); y++)
		{
			for (int x = 0; x < Extract.getWidth(); x++)
			{
				Color PixelColor = new Color(Extract.getRGB(x, y));

				Buffer.put((byte) PixelColor.getRed());
				Buffer.put((byte) PixelColor.getGreen());
				Buffer.put((byte) PixelColor.getBlue());
				Buffer.put((byte) PixelColor.getAlpha());
			}
		}

		Buffer.flip();

		return Buffer;
	}

	public int GetWidth()
	{
		return (int) Size.GetX();
	}

	public int GetHeight()
	{
		return (int) Size.GetY();
	}

	public Vector2f GetSize()
	{
		return Size;
	}

	public int GetID()
	{
		return TextureID;
	}

	public ByteBuffer GetData()
	{
		return Data;
	}

}
