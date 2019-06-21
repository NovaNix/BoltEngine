package AI.PathFinding;

import java.util.ArrayList;

import Vectors.Vector2f;

public class AStar
{

	private static GridNode[][] OpenList;

	private static GridNode[][] ClosedList;

	private static boolean[][] WallMap;

	private static ArrayList<GridNode> OpenNodes;

	// Returns the path in array units to the specified target
	// The target position and start position should be in array units
	// The grid consists of a 2d array of booleans, where true means the position is
	// solid
	public static Path PathFind(boolean[][] Grid, int StartX, int StartY, int TargetX, int TargetY, boolean AllowDiagonal, float Multiplyer)
	{
		OpenList = new GridNode[Grid.length][Grid[0].length];

		ClosedList = new GridNode[Grid.length][Grid[0].length];

		OpenNodes = new ArrayList<GridNode>();

		WallMap = Grid;

		int CurrentX = StartX;
		int CurrentY = StartY;

		GridNode Start = new GridNode(new Vector2f(CurrentX, CurrentY), new Vector2f(TargetX, TargetY));

		OpenList[CurrentX][CurrentY] = Start;

		OpenNodes.add(Start);

		boolean Done = false;

		while (!Done)
		{

			GridNode CurrentNode = GetLowestBox();

			CurrentX = (int) CurrentNode.GetX();
			CurrentY = (int) CurrentNode.GetY();

			ClosedList[CurrentX][CurrentY] = CurrentNode;
			OpenList[CurrentX][CurrentY] = null;

			OpenNodes.remove(CurrentNode);

			GridNode[][] Score = Score(CurrentNode, WallMap, AllowDiagonal);

			for (int x = 0; x < 3; x++)
			{
				for (int y = 0; y < 3; y++)
				{

					int PosX = CurrentX + (x - 1);
					int PosY = CurrentY + (y - 1);

					if ((PosX < 0 || PosX >= Grid.length) || (PosY < 0 || PosY >= Grid[0].length))
					{

					}

					else if (Score[x][y] == null)
					{

					}

					else if (PosX == TargetX && PosY == TargetY)
					{
						Done = true;

						ClosedList[PosX][PosY] = Score[x][y];

						break;
					}

					else if (ClosedList[PosX][PosY] != null)
					{

					}

					else if (OpenList[PosX][PosY] == null)
					{
						OpenList[PosX][PosY] = Score[x][y];
						OpenNodes.add(Score[x][y]);
					}

					else if (OpenList[PosX][PosY].GetF() > Score[x][y].GetF())
					{
						OpenNodes.remove(OpenList[PosX][PosY]);

						OpenList[PosX][PosY] = Score[x][y];

						OpenNodes.add(OpenList[PosX][PosY]);

					}

				}
			}

		}

		ArrayList<GridNode> FinalNodes = new ArrayList<GridNode>();

		boolean PathDone = false;

		GridNode LatestNode = ClosedList[TargetX][TargetY];

		while (!PathDone)
		{
			FinalNodes.add(LatestNode);

			if (LatestNode != Start)
			{
				LatestNode = LatestNode.GetParent();
			}

			else
			{
				PathDone = true;
			}
		}

		Path FinalPath = new Path();

		for (int i = FinalNodes.size(); i > 0; i--)
		{
			Vector2f Node = FinalNodes.get(i - 1).GetPosition();

			Node.Multiply(Multiplyer);

			FinalPath.AddPoint(Node);
		}

		return FinalPath;
	}

	private static GridNode[][] Score(GridNode ParentNode, boolean[][] Grid, boolean AllowDiagonal)
	{
		GridNode[][] Score = new GridNode[3][3];

		for (int x = 0; x < 3; x++)
		{
			for (int y = 0; y < 3; y++)
			{

				int PosX = ((int) ParentNode.GetX()) + (x - 1);
				int PosY = ((int) ParentNode.GetY()) + (y - 1);

				if ((PosX < 0 || PosX >= Grid.length) || (PosY < 0 || PosY >= Grid[0].length))
				{

				}

				else if (Grid[PosX][PosY] == true)
				{

				}

				else if (Math.abs(x - 1) == 1 && Math.abs(y - 1) == 1 && !AllowDiagonal)
				{

				}

				else
				{
					Score[x][y] = new GridNode(ParentNode, new Vector2f(PosX, PosY));
					;
				}

			}
		}

		return Score;
	}

	private static GridNode GetLowestBox()
	{
		GridNode Lowest = OpenNodes.get(0);

		for (int i = 0; i < OpenNodes.size(); i++)
		{
			GridNode Node = OpenNodes.get(i);

			if (Lowest.GetF() >= Node.GetF())
			{
				Lowest = Node;
			}
		}

		return Lowest;
	}

}
