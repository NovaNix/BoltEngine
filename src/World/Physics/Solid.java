package World.Physics;

import Geometry.Line;
import Geometry.Shapes.Shape;

public interface Solid 
{

	public void OnCollision(Solid Collided);
	
	public void CollidesWith(Shape Collision);
	public void CollidesWith(Line Collision);
	
}
