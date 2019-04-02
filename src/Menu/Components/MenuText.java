/*
 * 
 */
package Menu.Components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import Rendering.Rendering;
import Vectors.Vector2f;

public class MenuText extends MenuElement
{

	String Text;
	Font Style = new Font("Serif", Font.PLAIN, 18);
	Color Hue = new Color(255, 255, 255);
	
	boolean Centered = true;
	
	public MenuText(Vector2f Position, Vector2f Scale, String Text, boolean Centered)
	{
		this.Position = Position;
		this.Scale = Scale;
		
		this.Text = Text;
		
		this.Centered = Centered;
	}
	
	public MenuText(Vector2f Position, Vector2f Scale, String Text, Font Style, boolean Centered)
	{
		this.Position = Position;
		this.Scale = Scale;
		
		this.Text = Text;
		
		this.Style = Style;
		
		this.Centered = Centered;
	}
	
	public MenuText(Vector2f Position, Vector2f Scale, String Text, Color Hue, boolean Centered)
	{
		this.Position = Position;
		this.Scale = Scale;
		
		this.Text = Text;
		
		this.Hue = Hue;
		
		this.Centered = Centered;
	}
	
	public MenuText(Vector2f Position, Vector2f Scale, String Text, Font Style, Color Hue, boolean Centered)
	{
		this.Position = Position;
		this.Scale = Scale;
		
		this.Text = Text;
		
		this.Style = Style;
		this.Hue = Hue;
		
		this.Centered = Centered;
	}
	
	@Override
	public void Render()
	{

		if (Centered)
		{
			FontMetrics Metrics = Rendering.GetRenderingGraphics2D().getFontMetrics(Style);
			int TextX = (int) (Position.GetX() + (Scale.GetX() - Metrics.stringWidth(Text)) / 2);
			int TextY = (int) (Position.GetY() + ((Scale.GetY() - Metrics.getHeight()) / 2) + Metrics.getAscent());
			
			Rendering.RenderRSText(new Vector2f(TextX, TextY), Text, Style, Hue);
		}
		
		else
		{
			Rendering.RenderRSText(Position, Text, Style, Hue);
		}

		
	}

	@Override
	public void Update()
	{
		// TODO Auto-generated method stub
		
	}
}