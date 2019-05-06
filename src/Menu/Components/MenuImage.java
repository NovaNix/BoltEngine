/*
 *
 */
package Menu.Components;

import java.awt.Image;

import Rendering.Rendering;
import Vectors.Vector2f;

public class MenuImage extends MenuElement
{

	Image Icon;

	float Rotation;

	public MenuImage(Vector2f Position, Vector2f Scale, Image Icon, float Rotation)
	{
		this.Position = Position;
		this.Scale = Scale;

		this.Icon = Icon;
		this.Rotation = Rotation;
	}

	@Override
	public void Render()
	{
		Rendering.RenderRSImage(Icon, Position, Scale, (int) Rotation);
	}

	@Override
	public void Update()
	{

	}

	@Override
	public void LeftClick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void RightClick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void MiddleClick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void Hover()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void Dehover()
	{
		// TODO Auto-generated method stub

	}

}
