package Rendering.Handling;

import java.awt.Color;

import Rendering.Handling.Rendering.RenderingType;

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
