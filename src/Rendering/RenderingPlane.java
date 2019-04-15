/*
 * 
 */
package Rendering;

import java.util.ArrayList;
import java.util.Comparator;

import Rendering.Renderable;

import Algorithms.Sorting.Sortable;
import Algorithms.Sorting.SortingAlgorithm;

public class RenderingPlane implements Renderable, Sortable
{

	String Name;

	ArrayList<Renderable> ToRender = new ArrayList<Renderable>();

	public RenderingPlane(String Name)
	{
		this.Name = Name;
	}

	public void AddRenderable(Renderable Rendered)
	{
		ToRender.add(Rendered);
	}

	public void RemoveRenderable(Renderable Rendered)
	{
		ToRender.remove(Rendered);
	}

	public void Render()
	{
		for (int i = 0; i < ToRender.size(); i++)
		{
			ToRender.get(i).Render();
		}
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	@Override
	public <Renderable> void Sort(SortingAlgorithm Algorithm, Comparator<Renderable> Testing)
	{

		// ToRender = Algorithm.Sort(ToRender, Testing);
	}

}
