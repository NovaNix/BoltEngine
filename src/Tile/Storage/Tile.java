package Tile.Storage;

import Geometry.Shapes.Shape;
import Rendering.Renderable;
import Rendering.Rendering;
import Rendering.Image.Sprite;
import Rendering.Image.Texture;
import Vectors.Vector2f;

@SuppressWarnings("rawtypes")
public class Tile implements Renderable
{

	Sprite Texture;
	Vector2f Scale;
	Shape Collision;

	Vector2f Position = new Vector2f(0, 0);

	// Should be used for creating the tile templates
	public Tile(Sprite Texture, Vector2f Scale, Shape Collision)
	{
		this.Texture = Texture;
		this.Scale = Scale;
		this.Collision = Collision;
	}

	// Should be used for creating the tiles in the world
	public Tile(Tile Type, Vector2f TilePosition)
	{
		this.Texture = Type.GetTexture();
		this.Scale = Type.GetScale();

		this.Position = TilePosition;

		TilePosition.Multiply(Scale);

		this.Collision = Type.GetCollision();

		if (Collision != null)
		{
			Collision = Collision.Clone();
			Collision.Move(Position);
		}

		Tex = Texture.GetImage();
	}

	public Sprite GetTexture()
	{
		return Texture;
	}

	public Vector2f GetScale()
	{
		return Scale;
	}

	public Shape GetCollision()
	{
		return Collision;
	}

	public boolean CollidesWith(Shape Collision)
	{
		return this.Collision.CollidesWith(Collision);
	}

	Texture Tex;

	@Override
	public void Render()
	{
		Rendering.RenderReferencedImage(Tex, Position, Scale, 0);
	}

}
