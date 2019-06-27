package Storage;

import java.util.HashMap;

public class Asset<H>
{

	static HashMap<String, Asset<?>> Assets = new HashMap<String, Asset<?>>();

	String ID;
	H AssetObject;

	public Asset(String ID, H AssetObject)
	{
		this.ID = ID;
		this.AssetObject = AssetObject;

		Assets.put(ID, this);
	}

	public String GetID()
	{
		return ID;
	}

	public H GetAssetObject()
	{
		return AssetObject;
	}

	public static Asset<?> GetAsset(String ID)
	{
		return Assets.get(ID);
	}

}
