package Physics;

import Geometry.Shapes.Shape;
import Vectors.Vector2f;

public class StaticBody extends PhysicsObject
{

	public StaticBody(Vector2f Position, float Mass, Shape Collision)
	{
		super(0, 0, Mass);

		this.Collision = Collision;
		Collision.SetPosition(Position);
	}

	@Override
	public Vector2f GetFollowingPosition()
	{
		return Collision.GetCenter();
	}

	@Override
	public void CollisionWith(PhysicsObject Collision)
	{
		
		
	}

	@Override
	public void Move(float X, float Y)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector2f GetPosition()
	{
		return Collision.GetPosition();
	}
}
