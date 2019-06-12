package AI.DecisionTree;

public class Tree
{

	Node StartNode;

	public Tree(Node StartNode)
	{
		this.StartNode = StartNode;
	}

	public void RunTree()
	{
		StartNode.Run();
	}

}
