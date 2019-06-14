package AI.DecisionTree;

import Utils.Condition;

public abstract class DecisionNode extends Node
{

	Node YesNode;
	Node NoNode;

	Condition Test;

	public DecisionNode(Node YesNode, Node NoNode, Condition Test)
	{
		this.YesNode = YesNode;
		this.NoNode = NoNode;

		this.Test = Test;
	}

	@Override
	public void Run()
	{
		if (Test.Test())
		{
			YesNode.Run();
		}

		else
		{
			NoNode.Run();
		}
	}

}
