package Tile.Storage;

import java.util.HashMap;

import Geometry.Shapes.Shape;
import Rendering.Handling.Renderable;
import Rendering.Handling.Rendering;
import Rendering.Image.Texture;
import Vectors.Vector2f;

@SuppressWarnings("rawtypes")
public class Tile implements Renderable
{

	String ID;
	String Name;

	Texture Texture;
	Vector2f Scale;
	Shape Collision;

	Vector2f Position = new Vector2f(0, 0);

	static HashMap<String, Tile> TileTemplates = new HashMap<String, Tile>();

	// Should be used for creating the tile templates
	public Tile(String ID, String Name, Texture Tex, Vector2f Scale, Shape Collision)
	{
		this.ID = ID;
		this.Name = Name;

		this.Texture = Tex;
		this.Scale = Scale;
		this.Collision = Collision;

		TileTemplates.put(ID, this);
	}

	// Returns the created Tile Template with the ID
	public static Tile GetTile(String ID)
	{
		return TileTemplates.get(ID);
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

		this.ID = Type.GetID();
		this.Name = Type.GetName();
	}

	public String GetID()
	{
		return ID;
	}

	public String GetName()
	{
		return Name;
	}

	public Texture GetTexture()
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

	@Override
	public void Render()
	{
		Rendering.RenderReferencedImage(Texture, Position, Scale, 0);
	}

}
