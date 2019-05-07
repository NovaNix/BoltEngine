/*
 *
 */
package Rendering;

import java.awt.Color;

import Rendering.Rendering.RenderingType;

public interface Renderable
{
	void Render();

	default void Render(Color Hue)
	{
		Render();
	}

	default void Render(Color Hue, RenderingType Type)
	{
		Render();
	}
}
