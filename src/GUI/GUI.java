package GUI;

import java.util.ArrayList;

import GUI.Elements.GUIElement;
import IO.Mouse;
import Vectors.Vector2f;

public class GUI extends GUIElementContainer
{

	ArrayList<GUIElement> Elements = new ArrayList<GUIElement>();

	GUIElement Selected;

	public GUI(Vector2f Size)
	{
		super(new Vector2f(), Size);
	}

	public void Update()
	{
		Tick();

		Vector2f MousePosition = Mouse.GetMousePosition();

		boolean LeftMouse = Mouse.LeftMouseClicked();
		boolean MiddleMouse = Mouse.MiddleMouseClicked();
		boolean RightMouse = Mouse.RightMouseClicked();

		for (int i = 0; i < Elements.size(); i++)
		{
			GUIElement Element = Elements.get(i);

			if (Element.GetInteractBoundary().CollidesWith(MousePosition))
			{
				if (LeftMouse)
				{
					Selected = Element;

					Element.SetSelected(true);
					Element.LeftClick();
				}

				else if (RightMouse)
				{
					Element.RightClick();
				}

				else if (MiddleMouse)
				{
					Element.MiddleClick();
				}

				else
				{
					Element.SetHover(true);
				}
			}

			else
			{
				Element.SetHover(false);
			}
		}

	}

}
