package Physics;

import Geometry.Shapes.Shape;
import Physics.Forces.Force;
import Rendering.Renderable;
import Utils.Followable;
import Vectors.Vector2f;

public abstract class PhysicsObject implements Followable
{

	protected Vector2f LastSpeed;
	
	protected Vector2f Speed;
	
	protected Vector2f LastPosition = null;
	
	protected float Mass;

	protected Shape Collision;
	
	public PhysicsObject(float XSpeed, float YSpeed, float Mass)
	{
		this.Speed = new Vector2f(XSpeed, YSpeed);
		
		this.Mass = Mass;
	}

	public PhysicsObject(Vector2f Speed, float Mass)
	{
		this.Speed = Speed;
		
		this.Mass = Mass;
	}
	
	public void SetXSpeed(float Speed)
	{
		this.Speed.SetX(Speed);
	}
	
	public void SetYSpeed(float Speed)
	{
		this.Speed.SetY(Speed);
	}
	
	public void ModifyXSpeed(float Amount)
	{
		Speed.Add(new Vector2f(Amount, 0));
	}
	
	public void ModifyYSpeed(float Amount)
	{
		Speed.Add(new Vector2f(0, Amount));
	}
	
	public void ModifySpeed(Vector2f Amount)
	{
		Speed.Add(Amount);
	}
	
	public Vector2f GetSpeed()
	{
		return Speed;
	}
	
	public float GetXSpeed()
	{
		return Speed.GetX();
	}
	
	public float GetYSpeed()
	{
		return Speed.GetY();
	}
	
	public void UpdatePhysics()
	{
		LastPosition = GetPosition().Derive();
		
		LastSpeed = Speed.Derive();

		UpdatePositions();
	}

	private void UpdateSpeeds()
	{
		LastSpeed = Speed;
	}
	
	private int MovementBreaking = 10; 
	
	private void UpdatePositions()
	{
//		Move(Speed.GetX(), Speed.GetY());
		
		if (IsMovingUp())
		{
			for (int i = 0; i < MovementBreaking; i++)
			{
				Move(0, YSpeed / MovementBreaking);
				
				boolean Collides = false;
				
				for (int j = 0; j < CollisionGroups.size(); j++)
				{
					if (CollidesWith(CollisionGroups.get(j)))
					{
						Move(0, -YSpeed / MovementBreaking);
						Collides = true;
						break;
					}
				}
				
				if (Collides)
				{
					Collision(Direction.North);
					break;
				}
			}
		}
		
		else if (IsMovingDown())
		{
			for (int i = 0; i < MovementBreaking; i++)
			{
				Move(0, YSpeed / MovementBreaking);
				
				boolean Collides = false;
				
				for (int j = 0; j < CollisionGroups.size(); j++)
				{
					if (CollidesWith(CollisionGroups.get(j)))
					{
						Move(0, -YSpeed / MovementBreaking);
						Collides = true;
						break;
					}
				}
				
				if (Collides)
				{
					Collision(Direction.South);
					YSpeed = 0;
					break;
				}
			}
		}
		
		if (IsMovingLeft())
		{
			for (int i = 0; i < MovementBreaking; i++)
			{
				Move(XSpeed / MovementBreaking, 0);
				
				boolean Collides = false;
				
				for (int j = 0; j < CollisionGroups.size(); j++)
				{
					if (CollidesWith(CollisionGroups.get(j)))
					{
						Move(-XSpeed / MovementBreaking, 0);
						Collides = true;
						break;
					}
				}
				
				if (Collides)
				{
					Collision(Direction.West);
					YSpeed = 0;
					break;
				}
			}
		}
		
		else if (IsMovingRight())
		{
			for (int i = 0; i < MovementBreaking; i++)
			{
				Move(XSpeed / MovementBreaking, 0);
				
				boolean Collides = false;
				
				for (int j = 0; j < CollisionGroups.size(); j++)
				{
					if (CollidesWith(CollisionGroups.get(j)))
					{
						Move(-Speed.GetX() / MovementBreaking, 0);
						Collides = true;
						break;
					}
				}
				
				if (Collides)
				{
					Collision(Direction.East);
					YSpeed = 0;
					break;
				}
			}
		}
	}
	
	public void ApplyForce(Force ActiveForce)
	{
		Speed.Add(ActiveForce.GetDirection());
	}

	public Shape GetCollision()
	{
		return Collision;
	}
	
	public abstract void CollisionWith(PhysicsObject Collision);
	
	public abstract void Move(float X, float Y);
	
	public abstract Vector2f GetPosition();
	
	public boolean IsMovingUp()
	{
		return Speed.GetY() < 0.0;
	}
	
	public boolean IsMovingDown()
	{
		return Speed.GetY() > 0.0;
	}
	
	public boolean IsMovingLeft()
	{
		return Speed.GetX() < 0.0;
	}
	
	public boolean IsMovingRight()
	{
		return Speed.GetX() > 0.0;
	}
	
	public boolean IsStationary()
	{
		return Speed.equals(new Vector2f(0, 0));
	}

}
