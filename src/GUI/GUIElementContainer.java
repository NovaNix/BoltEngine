package GUI;

import java.util.ArrayList;

import GUI.Elements.GUIElement;
import IO.Mouse;
import Rendering.Handling.Rendering2D;
import Rendering.OpenGL.FrameBufferObject;
import Vectors.Vector2f;

public class GUIElementContainer extends GUIElement
{

	ArrayList<GUIElement> Elements = new ArrayList<GUIElement>();
	ArrayList<GUIElement> NonStaticElements = new ArrayList<GUIElement>();
	ArrayList<GUIElement> StaticElements = new ArrayList<GUIElement>();

	FrameBufferObject FBO;

	GUIElement Selected;

	public GUIElementContainer(Vector2f Position, Vector2f Size)
	{
		super(Position, Size, false);

		this.Position = Position;
		this.Scale = Size;
	}

	@Override
	public void Tick()
	{

		Update();

		for (int i = 0; i < Elements.size(); i++)
		{
			Elements.get(i).Tick();
		}

		boolean ElementUpdated = false;

		for (int i = 0; i < StaticElements.size(); i++)
		{
			if (StaticElements.get(i).IsUpdated())
			{
				ElementUpdated = true;
				StaticElements.get(i).PreRender();
			}
		}

		if (ElementUpdated)
		{
			PreRender();
		}
	}

	public void Update()
	{
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

	public void SetSize(Vector2f Size)
	{
		this.Scale = Size;

		UpdateScale(Size);

		for (int i = 0; i < Elements.size(); i++)
		{
			Elements.get(i).UpdateScale(Size);
		}
	}

	public void AddElement(GUIElement Element)
	{
		Elements.add(Element);

		if (Element.IsStatic())
		{
			StaticElements.add(Element);
		}

		else
		{
			NonStaticElements.add(Element);
		}
	}

	public void RemoveElement(GUIElement Element)
	{
		Elements.remove(Element);

		if (Element.IsStatic())
		{
			StaticElements.remove(Element);
		}

		else
		{
			NonStaticElements.remove(Element);
		}
	}

	@Override
	public void Render()
	{
		Rendering2D.RenderRawImage(FBO.GetTextureHandle(), Scale, Position, Scale, 0f);

		for (int i = 0; i < NonStaticElements.size(); i++)
		{
			NonStaticElements.get(i).Render();
		}
	}

	@Override
	public void PreRender()
	{
		FBO.Bind();

		for (int i = 0; i < StaticElements.size(); i++)
		{
			StaticElements.get(i).Render();
		}

		FBO.UnBind();

	}

	@Override
	protected void Hover()
	{
		Update();
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
		// TODO Auto-generated method stub
		return false;
	}

}
