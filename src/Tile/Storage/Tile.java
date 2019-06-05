package Tile.Storage;

import Geometry.Shapes.Shape;
import Rendering.Image.Sprite;
import Vectors.Vector2f;

@SuppressWarnings("rawtypes")
public class Tile
{

	Sprite Texture;
	Vector2f Scale;
	Shape Collision;

	// Should be used for creating the tile templates
	public Tile(Sprite Texture, Vector2f Scale, Shape Collision)
	{
		this.Texture = Texture;
		this.Scale = Scale;
		this.Collision = Collision;
	}

	// Should be used for creating the tiles in the world
	public Tile(Tile Type, Vector2f Position)
	{
		this.Texture = Type.GetTexture();
		this.Scale = Type.GetScale();
		this.Collision = Type.GetCollision().Clone();
		Collision.SetPosition(Position);
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

}
