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
