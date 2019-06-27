package Rendering.Text;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import Rendering.Image.Texture;
import Vectors.Vector2f;

public class TextImageCreator
{

	static HashMap<CashedTextImage, Texture> Cashes = new HashMap<CashedTextImage, Texture>();

	static Canvas FontMetricGrabber = new Canvas();

	public static Texture GenerateTextImage(String Text, Color Hue, Font Style)
	{
		CashedTextImage CasheKey = new CashedTextImage(Text, Hue, Style);

		if (Cashes.containsKey(CasheKey))
		{
			return Cashes.get(CasheKey);
		}

		else
		{

			FontMetrics Metrics = FontMetricGrabber.getFontMetrics(Style);
			int Width = Metrics.stringWidth(Text);
			int Height = Metrics.getHeight() + Metrics.getMaxDescent();

			BufferedImage ReturnedTexture = new BufferedImage(Width, Height, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = (Graphics2D) ReturnedTexture.getGraphics().create();

			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			g.setFont(Style);
			g.setColor(Hue);
			g.drawString(Text, 0, Metrics.getHeight());

			g.dispose();

			Texture TexImage = new Texture(ReturnedTexture);

			Cashes.put(CasheKey, TexImage);

			return TexImage;
		}

	}

	public static Vector2f GetTextSize(String Text, Font Style)
	{
		FontMetrics Metrics = FontMetricGrabber.getFontMetrics(Style);
		int Width = Metrics.stringWidth(Text);
		int Height = Metrics.getHeight() + Metrics.getMaxDescent();

		return new Vector2f(Width, Height);
	}

}
