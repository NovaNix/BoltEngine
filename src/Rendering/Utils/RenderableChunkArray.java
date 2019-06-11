/*
 * 
 */
package Rendering.Utils;

import java.util.ArrayList;

import Rendering.Handling.Renderable;
import Tile.Storage.ChunkArray;

public class RenderableChunkArray extends ChunkArray<Renderable> implements Renderable
{

	public RenderableChunkArray(boolean AutoClean, int Distance)
	{
		super(AutoClean, Distance);
	}

	@Override
	public void Render()
	{
		ArrayList<Renderable> Chunks = GetAllChunks();

		for (int i = 0; i < Chunks.size(); i++)
		{
			Chunks.get(i).Render();
		}

	}

}
