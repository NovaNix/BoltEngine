/*
 *
 */
package Rendering;

import java.util.ArrayList;
import java.util.Comparator;

import Algorithms.Sorting.Sortable;
import Vectors.Vector2f;

public class RenderingPlane implements Renderable, Sortable<Renderable>
{

	String Name;

	// Used to decide what gets rendered
	float Depth = 0;

	float XParallax = 1;
	float YParallax = 1;

	Vector2f FocalPoint = new Vector2f(0, 0);

	ArrayList<Renderable> ToRender = new ArrayList<Renderable>();

	public RenderingPlane(String Name)
	{
		this.Name = Name;
	}

	public RenderingPlane(String Name, float Depth, float XParallax, float YParallax, Vector2f FocalPoint)
	{
		this.Name = Name;

		this.Depth = Depth;

		this.XParallax = XParallax;
		this.YParallax = YParallax;

		this.FocalPoint = FocalPoint;
	}

	public RenderingPlane(String Name, float XParallax, float YParallax, Vector2f FocalPoint)
	{
		this.Name = Name;

		this.XParallax = XParallax;
		this.YParallax = YParallax;

		this.FocalPoint = FocalPoint;
	}

	public RenderingPlane(String Name, float XParallax, float YParallax)
	{
		this.Name = Name;

		this.XParallax = XParallax;
		this.YParallax = YParallax;
	}

	public void AddRenderable(Renderable Rendered)
	{
		ToRender.add(Rendered);
	}

	public void RemoveRenderable(Renderable Rendered)
	{
		ToRender.remove(Rendered);
	}

	@Override
	public void Render()
	{
		for (int i = 0; i < ToRender.size(); i++)
		{
			ToRender.get(i).Render();
		}
	}

	public Vector2f GetParallaxTranslation(Vector2f CameraCenter)
	{
		float XTranslation = CameraCenter.GetXDistanceTo(FocalPoint) * XParallax;
		float YTranslation = CameraCenter.GetYDistanceTo(FocalPoint) * YParallax;

		return new Vector2f(XTranslation, YTranslation);
	}

	@Override
	public ArrayList<Renderable> GetToSort()
	{
		return ToRender;
	}

	@Override
	public Comparator<Renderable> GetTester()
	{
		return new Comparator<Renderable>()
		{

			@Override
			public int compare(Renderable o1, Renderable o2)
			{
				return 0;
			}
		};
	}

	@Override
	public void SetSorted(ArrayList<Renderable> Sorted)
	{
		ToRender = Sorted;
	}

}
