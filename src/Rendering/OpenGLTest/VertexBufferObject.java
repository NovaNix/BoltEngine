package Rendering.OpenGLTest;

import Geometry.Shapes.Polygon;
import Rendering.Renderable;
import Utils.Vector2fUtils;
import Vectors.Vector2f;

public class VertexBufferObject
{

	float[] Verticies;
	float[] TextureVerticies;

	int[] Indicies;

	public VertexBufferObject(float[] V, float[] T, int[] I)
	{
		this.Verticies = V;
		this.TextureVerticies = T;
		this.Indicies = I;
	}

	private void GenerateVBO(Polygon Poly)
	{
		this.Verticies = Vector2fUtils.GetCoordList(Poly.GetCompressed());
		this.TextureVerticies = Vector2fUtils.GetCoordList(this.GenerateTexCoords(Poly));
	}

	private Vector2f[] GenerateTexCoords(Polygon Poly)
	{

		Vector2f[] PolygonCorners = Poly.GetCorners();

		Vector2f PolygonScale = Poly.GetScale();

		float PolygonWidth = PolygonScale.GetX();
		float PolygonHeight = PolygonScale.GetY();

		Vector2f Translation = PolygonScale.Derive().Divide(new Vector2f(2, 2));

		Vector2f[] TexCoords = new Vector2f[PolygonCorners.length];

		for (int i = 0; i < PolygonCorners.length; i++)
		{
			Vector2f Point = PolygonCorners[i].Derive();

			Point.Subtract(Translation);

			Point.Divide(PolygonScale);

			TexCoords[i] = Point;
		}

		return TexCoords;
	}

	public float[] GetVerticies()
	{
		return this.Verticies;
	}

	public float[] GetTextureVerticies()
	{
		return this.TextureVerticies;
	}

	public int[] GetIndicies()
	{
		return this.Indicies;
	}

}
