package AI.PathFinding;

import java.util.Comparator;

import Vectors.Vector2f;

public class GridNode 
{

	Vector2f Position;
	
	Vector2f Goal;
	
	GridNode Parent;
	
	float G = 0;
	float H = 0;
	
	float F = 0;
	
	public static final Comparator<GridNode> NodeComparator = new Comparator<GridNode>() {

		@Override
		public int compare(GridNode arg0, GridNode arg1) 
		{

			if (arg0.GetF() > arg1.GetF())
			{
				return 1;
			}
			
			else if (arg0.GetF() == arg1.GetF())
			{
				return 0;
			}
			
			else if (arg0.GetF() < arg1.GetF())
			{
				return -1;
			}
				
				
			return 0;
		}};

		
	
	// Should only be used for the starting node
	public GridNode(Vector2f Position, Vector2f Goal)
	{
		this.Position = Position;
		
		this.Goal = Goal;
		
		H = Math.abs(Goal.GetX() - Position.GetX()) + Math.abs(Goal.GetY() - Position.GetY());
		
		F = H;
	}
	
	public GridNode(GridNode Parent, Vector2f Position)
	{
		this.Position = Position;
		
		this.Goal = Parent.GetGoal();
		
		this.Parent = Parent;
		
		G = Parent.GetG() + 1;
		
		H = Math.abs(Goal.GetX() - Position.GetX()) + Math.abs(Goal.GetY() - Position.GetY());
		
		F = G + H;
	}
	
	public void SetParent(GridNode Parent)
	{
		this.Parent = Parent;
		
		this.Goal = Parent.GetGoal();
		
		System.out.println("Changed Parent!");
		
		this.G = Parent.GetG() + 1;
		
		F = G + H;
	}
	
	public Vector2f GetPosition()
	{
		return Position;
	}
	
	public float GetX()
	{
		return Position.GetX();
	}
	
	public float GetY()
	{
		return Position.GetY();
	}
	
	public Vector2f GetGoal()
	{
		return Goal;
	}
	
	public float GetG()
	{
		return G;
	}
	
	public float GetH()
	{
		return H;
	}
	
	public float GetF()
	{
		return F;
	}
	
	public GridNode GetParent()
	{
		return Parent;
	}
	
}
