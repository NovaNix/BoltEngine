package Physics;

import Geometry.Shapes.Shape;
import Vectors.Vector2f;

public class Particle extends PhysicsObject
{

	protected Vector2f Position;

	public Particle(Vector2f Position, float Mass, Shape AreaOfInfluence)
	{
		super(0, 0, Mass);

		this.Position = Position;

		this.Collision = AreaOfInfluence;
		Collision.SetCenter(Position);
	}

	@Override
	public Vector2f GetFollowingPosition()
	{
		return Position;
	}

	@Override
	public void Move(float X, float Y)
	{
		Position.Add(new Vector2f(X, Y));
		// Collision.SetCenter(Position);
		// Collision.Move(X, Y);
	}

	@Override
	public Vector2f GetPosition()
	{
		return Collision.GetPosition();
	}

	@Override
	public void CollisionWith(PhysicsObject Collision)
	{
		// TODO Auto-generated method stub

	}

}
