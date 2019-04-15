package Rendering.OpenGLTest;

import Geometry.Shapes.Polygon;
import Rendering.Renderable;
import Utils.Vector2fUtils;
import Vectors.Vector2f;

public class VertexBufferObject implements Renderable
{

	float[] Verticies;
	float[] TextureVerticies;

	int[] Indicies;

	public VertexBufferObject(Polygon Poly)
	{
		this.GenerateVBO(Poly);
	}

	private void GenerateVBO(Polygon Poly)
	{
		this.TextureVerticies = Vector2fUtils.GetCoordList(this.GenerateTexCoords(Poly));
	}

	private Vector2f[] GenerateTexCoords(Polygon Poly)
	{

		Vector2f[] PolygonCorners = Poly.GetCorners();

		Vector2f BottemLeft = new Vector2f(Vector2fUtils.GetFurthestWest(PolygonCorners).GetX(), Vector2fUtils.GetFurthestSouth(PolygonCorners).GetY());
		Vector2f TopRight = new Vector2f(Vector2fUtils.GetFurthestEast(PolygonCorners).GetX(), Vector2fUtils.GetFurthestNorth(PolygonCorners).GetY());

		float PolygonWidth = BottemLeft.GetXDistanceTo(TopRight);
		float PolygonHeight = BottemLeft.GetYDistanceTo(TopRight);

		Vector2f[] TexCoords = new Vector2f[PolygonCorners.length];

		for (int i = 0; i < PolygonCorners.length; i++)
		{
			Vector2f Point = PolygonCorners[i].Derive();

			Point.Subtract(BottemLeft);

			Point.Divide(new Vector2f(PolygonWidth, PolygonHeight));

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

	@Override
	public void Render()
	{

	}

}
