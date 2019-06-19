package AI.DecisionTree;

import AI.AI;
import Utils.Tickable;

public class DecisionTree implements Tickable
{

	Node StartNode;

	public DecisionTree(Node StartNode)
	{
		this.StartNode = StartNode;
	}

	public void RunTree()
	{
		StartNode.Run();
	}

	@Override
	public void Tick()
	{
		RunTree();
	}

}
