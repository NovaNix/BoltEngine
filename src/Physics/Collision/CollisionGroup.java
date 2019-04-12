package Physics.Collision;

import java.util.ArrayList;

import Physics.PhysicsObject;

public class CollisionGroup
{
	
	ArrayList<PhysicsObject> CollisionList = new ArrayList<PhysicsObject>();
	
	public CollisionGroup()
	{
		
	}
	
	public void AddCollision(PhysicsObject Collision)
	{
		CollisionList.add(Collision);
		Collision.AddTo(this);
	}
	
	public void RemoveCollision(PhysicsObject Collision)
	{
		CollisionList.remove(Collision);
		Collision.RemoveFrom(this);
	}
	
	public void UpdateCollisions()
	{
		 for (int i = 0; i < CollisionList.size() - 1; i++)
		 {
			 for (int j = i + 1; j < CollisionList.size(); j++)
			 {
				 if (CollisionList.get(i).GetCollision().CollidesWith(CollisionList.get(j).GetCollision()))
				 {
					 CollisionList.get(i).CollisionWith(CollisionList.get(j));
					 CollisionList.get(j).CollisionWith(CollisionList.get(i));
				 }
			 }
		 }
	}
	
	public ArrayList<PhysicsObject> GetCollisionsWith(PhysicsObject Collision)
	{
		ArrayList<PhysicsObject> Collisions = new ArrayList<PhysicsObject>();
		
		for (int i = 0; i < CollisionList.size(); i++)
		{
			if (CollisionList.get(i).equals(Collision))
			{
				if (CollisionList.get(i).GetCollision().CollidesWith(Collision.GetCollision()))
				{
					Collisions.add(CollisionList.get(i));
				}
			}
		}
		
		return Collisions;
	}
	
}
