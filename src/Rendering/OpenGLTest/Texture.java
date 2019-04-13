package Rendering.OpenGLTest;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.awt.Color;
import java.awt.Image;
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
			ByteBuffer Buffer = BufferUtils.createByteBuffer(Convert.getWidth() * Convert.getHeight() * 4);
			
			this.Size = new Vector2f(Convert.getWidth(), Convert.getHeight());
			
			for (int y = 0; y < Convert.getHeight(); y++)
			{
				for (int x = 0; x < Convert.getWidth(); x++)
				{
					Color PixelColor = new Color(Convert.getRGB(x, y));
					
					Buffer.put((byte) PixelColor.getRed());
					Buffer.put((byte) PixelColor.getGreen());
					Buffer.put((byte) PixelColor.getBlue());
					Buffer.put((byte) PixelColor.getAlpha());
				}
			}
			
			Buffer.flip();
			
			TextureID = glGenTextures();
			
			glBindTexture(GL_TEXTURE_2D, TextureID); 
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);	
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, Convert.getWidth(), Convert.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, Buffer);
			glGenerateMipmap(GL_TEXTURE_2D);

			PreloadedImages.put(Convert, this);
			
		}
	}
	
	public Texture(Sprite Convert)
	{
		
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
