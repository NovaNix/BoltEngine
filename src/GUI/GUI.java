package GUI;

import java.util.ArrayList;

import GUI.Elements.GUIElement;
import IO.Mouse;
import Rendering.Handling.PreRenderable;
import Rendering.Handling.Rendering;
import Rendering.OpenGL.FrameBufferObject;
import Utils.Tickable;
import Vectors.Vector2f;

public class GUI implements PreRenderable, Tickable
{

	ArrayList<GUIElement> Elements = new ArrayList<GUIElement>();
	ArrayList<GUIElement> NonStaticElements = new ArrayList<GUIElement>();
	ArrayList<GUIElement> StaticElements = new ArrayList<GUIElement>();

	Vector2f Position;
	Vector2f Size;

	FrameBufferObject FBO;

	GUIElement Selected;

	public GUI(Vector2f Size)
	{
		this.Position = new Vector2f();
		this.Size = Size;
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

	public Vector2f GetPosition()
	{
		return Position;
	}

	public Vector2f GetScale()
	{
		return Size;
	}

	public void SetSize(Vector2f Size)
	{
		this.Size = Size;

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
		Rendering.RenderRawImage(FBO.GetTextureHandle(), Size, Position, Size, 0f);

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

}
