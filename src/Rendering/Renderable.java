/*
 * 
 */
package Rendering;

import java.awt.Color;

import Rendering.Rendering.RenderingType;

public interface Renderable
{
	public void Render();

	public default void Render(Color Hue, RenderingType Type)
	{
		Render();
	}
}
