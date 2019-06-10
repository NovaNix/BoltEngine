package Rendering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class ScreenShotHandler
{

	public static BufferedImage GetScreenShotImage(ByteBuffer ScreenShot, int Width, int Height, String Path)
	{
		BufferedImage ScreenShotImage = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR);

		for (int i = 0; i < ScreenShot.limit() / 3; i++)
		{
			int x = 0;
			int y = 0;

			ScreenShotImage.setRGB(x, y, new Color(ScreenShot.get(i), ScreenShot.get(i + 1), ScreenShot.get(i + 2)).getRGB());
		}

		return ScreenShotImage;
	}

}
