/*
 *
 */
package IO;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class EasyLoader
{

	static HashMap<String, Font> Fonts = new HashMap<String, Font>();
	static HashMap<String, BufferedImage> Images = new HashMap<String, BufferedImage>();
	static HashMap<String, ImageIcon> Icons = new HashMap<String, ImageIcon>();
	static HashMap<String, File> Files = new HashMap<String, File>();

	public static BufferedImage LoadImage(String Path, boolean Internal)
	{
		if (IsAdded(Images, Path))
		{
			return Images.get(Path);
		}
		
		else
		{
			if (Internal)
			{
				return LoadLocalImage(Path);	
			}
			
			else
			{
				return LoadExternalImage(Path);
			}
		}
	
	}
	
	public static ImageIcon LoadIcon(String Path, boolean Internal)
	{
		
		
		
		return null;	
	}
	
	public static Font LoadFont(String Path, boolean Internal)
	{
		
		
		
		return null;
	}
	
	public static Font LoadLocalFont(String Path)
	{
		if (IsAdded(Fonts, Path))
		{
			return Fonts.get(Path);
		}

		else
		{
			try
			{

				Font NewFont = Font.createFont(Font.TRUETYPE_FONT, EasyLoader.class.getResourceAsStream(Path));

				Fonts.put(Path, NewFont);

				return NewFont;

			} catch (FontFormatException | IOException e)
			{
				e.printStackTrace();
			}

			return null;
		}
	}

	public static BufferedImage LoadLocalImage(String Path)
	{
		if (IsAdded(Images, Path))
		{
			return Images.get(Path);
		}

		else
		{
			URL Location = EasyLoader.class.getResource(Path);
			BufferedImage Icon;
			try
			{
				Icon = ImageIO.read(Location);
				Images.put(Path, Icon);
				return Icon;
			} catch (IOException e)
			{
				return null;
			}
		}
	}

	public static BufferedImage LoadExternalImage(String Path)
	{
		BufferedImage Icon;
		try
		{
			Icon = ImageIO.read(new URL(Path));
			Images.put(Path, Icon);
			return Icon;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public static ImageIcon LoadLocalImageIcon(String Path)
	{
		if (IsAdded(Icons, Path))
		{
			return Icons.get(Path);
		}

		else
		{
			URL Location = EasyLoader.class.getResource(Path);
			ImageIcon Icon;

			Icon = new ImageIcon(Location);
			Icons.put(Path, Icon);
			return Icon;
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean IsAdded(HashMap List, String Path)
	{
		if (List.containsKey(Path))
		{
			return true;
		}

		else
		{
			return false;
		}
	}

}
