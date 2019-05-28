package Rendering;

import java.awt.Color;

import Rendering.Rendering.RenderingType;

// Specified that something can be rendered
public interface Renderable
{
	// Renders the object
	void Render();

	// Renders the object a specific color
	default void Render(Color Hue)
	{
		Render();
	}

	// Renders the object a specific color and renderingtype
	default void Render(Color Hue, RenderingType Type)
	{
		Render();
	}
}
