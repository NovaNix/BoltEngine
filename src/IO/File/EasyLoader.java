/*
 *
 */
package IO.File;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class EasyLoader
{

	static HashMap<String, Font> Fonts = new HashMap<String, Font>();
	static HashMap<String, BufferedImage> Images = new HashMap<String, BufferedImage>();
	static HashMap<String, ImageIcon> Icons = new HashMap<String, ImageIcon>();

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
		if (IsAdded(Icons, Path))
		{
			return Icons.get(Path);
		}

		else
		{
			if (Internal)
			{
				return LoadLocalImageIcon(Path);
			}

			else
			{
				// return LoadExternalImageIcon(Path);
			}
		}

		return null;
	}

	public static Font LoadFont(String Path, boolean Internal)
	{
		if (IsAdded(Fonts, Path))
		{
			return Fonts.get(Path);
		}

		else
		{
			if (Internal)
			{
				return LoadLocalFont(Path);
			}

			else
			{
				// return LoadExternalFont(Path);
			}
		}

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

	// Returns the line data of the file excluding empty spaces
	public static String[] ExtractFileLines(String File)
	{

		BufferedReader Reader = null;

		InputStream Input = EasyLoader.class.getResourceAsStream(File);

		InputStreamReader InputReader = new InputStreamReader(Input);

		Reader = new BufferedReader(InputReader);

		ArrayList<String> Lines = new ArrayList<String>();

		String Line;

		try
		{
			while ((Line = Reader.readLine()) != null)
			{
				if (!Line.equals(""))
				{
					Lines.add(Line);
				}
			}

			Reader.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] LineArray = new String[Lines.size()];

		LineArray = Lines.toArray(LineArray);

		return LineArray;

	}

	public static String[] ExtractFileLines(String File, String Comment)
	{

		BufferedReader Reader = null;

		InputStream Input = EasyLoader.class.getResourceAsStream(File);

		InputStreamReader InputReader = new InputStreamReader(Input);

		Reader = new BufferedReader(InputReader);

		ArrayList<String> Lines = new ArrayList<String>();

		String Line;

		try
		{
			while ((Line = Reader.readLine()) != null)
			{
				if (!Line.equals("") && !Line.startsWith(Comment))
				{
					Lines.add(Line);
				}
			}

			Reader.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] LineArray = new String[Lines.size()];

		LineArray = Lines.toArray(LineArray);

		return LineArray;

	}

}
