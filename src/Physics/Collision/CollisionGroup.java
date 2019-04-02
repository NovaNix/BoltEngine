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
	}
	
	public void RemoveCollision(PhysicsObject Collision)
	{
		CollisionList.remove(Collision);
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
	
	
}
