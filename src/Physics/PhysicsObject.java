package Physics;

import java.util.ArrayList;

import Geometry.Shapes.Shape;
import Physics.Collision.CollisionGroup;
import Physics.Forces.Force;
import Rendering.Renderable;
import Rendering.Cameras.Followable;
import Vectors.Vector2f;

public abstract class PhysicsObject implements Followable
{

	protected Vector2f LastSpeed;

	protected Vector2f Speed;

	protected Vector2f LastPosition = null;

	protected float Mass;

	protected Shape Collision;

	protected ArrayList<CollisionGroup> CollisionGroups = new ArrayList<CollisionGroup>();

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
		// Move(Speed.GetX(), Speed.GetY());

		if (IsMovingUp())
		{
			for (int i = 0; i < MovementBreaking; i++)
			{
				Move(0, Speed.GetY() / MovementBreaking);

				boolean Collides = false;

				for (int j = 0; j < CollisionGroups.size(); j++)
				{
					if (CollisionGroups.get(j).GetCollisionsWith(this).size() != 0)
					{
						Move(0, -Speed.GetY() / MovementBreaking);
						Collides = true;
						break;
					}
				}

				if (Collides)
				{
					// Collision(Direction.North);
					break;
				}
			}
		}

		else if (IsMovingDown())
		{
			for (int i = 0; i < MovementBreaking; i++)
			{
				Move(0, Speed.GetY() / MovementBreaking);

				boolean Collides = false;

				for (int j = 0; j < CollisionGroups.size(); j++)
				{
					if (CollisionGroups.get(j).GetCollisionsWith(this).size() != 0)
					{
						Move(0, -Speed.GetY() / MovementBreaking);
						Collides = true;
						break;
					}
				}

				if (Collides)
				{
					// Collision(Direction.South);
					Speed.SetY(0);
					break;
				}
			}
		}

		if (IsMovingLeft())
		{
			for (int i = 0; i < MovementBreaking; i++)
			{
				Move(Speed.GetX() / MovementBreaking, 0);

				boolean Collides = false;

				for (int j = 0; j < CollisionGroups.size(); j++)
				{
					if (CollisionGroups.get(j).GetCollisionsWith(this).size() != 0)
					{
						Move(-Speed.GetX() / MovementBreaking, 0);
						Collides = true;
						break;
					}
				}

				if (Collides)
				{
					// Collision(Direction.West);
					Speed.SetPosition(new Vector2f(0, 0));
					break;
				}
			}
		}

		else if (IsMovingRight())
		{
			for (int i = 0; i < MovementBreaking; i++)
			{
				Move(Speed.GetX() / MovementBreaking, 0);

				boolean Collides = false;

				for (int j = 0; j < CollisionGroups.size(); j++)
				{
					if (CollisionGroups.get(j).GetCollisionsWith(this).size() != 0)
					{
						Move(-Speed.GetX() / MovementBreaking, 0);
						Collides = true;
						break;
					}
				}

				if (Collides)
				{
					// Collision(Direction.East);
					Speed.SetPosition(new Vector2f(0, 0));
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

	public void AddTo(CollisionGroup Group)
	{
		CollisionGroups.add(Group);
	}

	public void RemoveFrom(CollisionGroup Group)
	{
		CollisionGroups.add(Group);
	}

}
