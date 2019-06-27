package GUI.Elements;

import java.awt.Color;
import java.awt.Font;

import Rendering.Handling.Rendering;
import Rendering.Image.Texture;
import Rendering.Text.TextImageCreator;
import Vectors.Vector2f;

public class GUIText extends GUIElement
{

	Texture PreRendered;

	String Text;
	Font Style;
	Color Hue;

	public GUIText(Vector2f Position, String Text, Font Style, Color Hue)
	{
		super(Position, TextImageCreator.GetTextSize(Text, Style), true);

		this.Text = Text;
		this.Style = Style;

		this.Hue = Hue;

	}

	@Override
	public void PreRender()
	{
		PreRendered = TextImageCreator.GenerateTextImage(Text, Hue, Style);
	}

	@Override
	public void Render()
	{
		Rendering.RenderRawImage(PreRendered, Position, Scale, 0);
	}

	@Override
	public void Tick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void Hover()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void UnHover()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void LeftClick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void MiddleClick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void RightClick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void Select()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void Deselect()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ShouldDeselect()
	{
		return true;
	}

}
