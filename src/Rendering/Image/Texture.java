package Rendering.Image;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import Rendering.OpenGL.Shader;
import Vectors.Vector2f;

public class Texture extends Graphic
{

	ByteBuffer Data;

	int TextureID;

	Vector2f Size;

	Shader DrawShader;

	public Texture(BufferedImage Convert)
	{
		this.Size = new Vector2f(Convert.getWidth(), Convert.getHeight());

		ByteBuffer ColorData = ExtractColorData(Convert);

		TextureID = glGenTextures();

		GenerateTexture(ColorData, Size, TextureID);

		Data = ColorData;

		if (DrawShader == null)
		{
			DrawShader = new Shader("/vertexshaders/defaultshader.vert", true, "/fragmentshaders/drawimage.frag", true);
		}

	}

	public Texture(ByteBuffer Data, Vector2f Size)
	{
		this.Size = Size;

		TextureID = glGenTextures();

		GenerateTexture(Data, Size, TextureID);

		this.Data = Data;

		if (DrawShader == null)
		{
			DrawShader = new Shader("/vertexshaders/defaultshader.vert", true, "/fragmentshaders/drawimage.frag", true);
		}
	}

	private static void GenerateTexture(ByteBuffer Data, Vector2f Size, int ID)
	{
		glBindTexture(GL_TEXTURE_2D, ID);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, (int) Size.GetX(), (int) Size.GetY(), 0, GL_RGBA, GL_UNSIGNED_BYTE, Data);
		glGenerateMipmap(GL_TEXTURE_2D);
	}

	public static ByteBuffer ExtractColorData(BufferedImage Extract)
	{
		ByteBuffer Buffer = BufferUtils.createByteBuffer(Extract.getWidth() * Extract.getHeight() * 4);

		for (int y = 0; y < Extract.getHeight(); y++)
		{
			for (int x = 0; x < Extract.getWidth(); x++)
			{
				Color PixelColor = new Color(Extract.getRGB(x, y), true);

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

	@Override
	public void finalize()
	{
		// glDeleteTextures(TextureID);
	}

	@Override
	public void BindGraphic()
	{
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, TextureID);
	}

	float[] ActiveKernel = new float[] { 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f };

	@Override
	public void BindShader()
	{
		DrawShader.Bind();

		DrawShader.SetUniform("Texture1", 0);
		DrawShader.SetUniform("ImageSize", GetWidth(), GetHeight());
		DrawShader.SetUniform("Kernel", ActiveKernel);
	}

	@Override
	public Shader GetDrawingShader()
	{
		return DrawShader;
	}

}
