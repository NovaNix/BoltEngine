package AI.DecisionTree;

import AI.AI;

public class DecisionTree extends AI
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
