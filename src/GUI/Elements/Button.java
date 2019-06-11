package GUI.Elements;

import Rendering.Image.Texture;
import Vectors.Vector2f;

public class Button extends GUIElement
{

	String Text;

	Texture RegularButton;
	Texture ClickedButton;

	Texture RegularHoverButton;
	Texture ClickedHoverButton;

	public Button(Vector2f Position, Vector2f Scale, String Text, Texture Regular, Texture Clicked)
	{
		super(Position, Scale, true);

		this.Text = Text;

		this.RegularButton = Regular;
		this.ClickedButton = Clicked;

		this.RegularHoverButton = Regular;
		this.ClickedHoverButton = Clicked;
	}

	@Override
	public void PreRender()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void Render()
	{

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

}
