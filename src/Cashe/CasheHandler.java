package Cashe;

import java.util.HashMap;

public class CasheHandler
{

	String Directory;

	CasheSavingType SavingType;

	HashMap<String, Object[]> SavedCashes = new HashMap<String, Object[]>();

	HashMap<String, Integer> PotentialCashes = new HashMap<String, Integer>();

	public CasheHandler(String Directory)
	{

	}

	public enum CasheSavingType
	{
		RAM, SingleFile, MultiFile, RAMSave, BrokenFiles
	}

}
