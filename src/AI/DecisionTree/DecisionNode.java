package AI.DecisionTree;

import Utils.Question;

public abstract class DecisionNode extends Node
{

	Node YesNode;
	Node NoNode;

	Question Test;

	public DecisionNode(Node YesNode, Node NoNode, Question Test)
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
