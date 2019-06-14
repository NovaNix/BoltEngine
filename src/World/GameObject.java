package World;

import AI.Puppetable;
import Rendering.Handling.Renderable;
import Vectors.Vector2f;
import World.Physics.Material;
import World.Physics.Solid;

public abstract class GameObject implements Renderable, Puppetable, Solid
{

	double LastUpdated;
	
	Vector2f Speed = new Vector2f();
	
	Vector2f Position;
	
	Material MaterialType;
	
	public GameObject(Material Type)
	{
		this.MaterialType = Type;
		
		this.LastUpdated = System.currentTimeMillis();
	}
	
	@Override
	public void Render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Move(Vector2f Translation) 
	{
		Position.Add(Translation);
	}

	@Override
	public void SetPosition(Vector2f Position)
	{
		Position.SetPosition(Position);
		
	}

}
