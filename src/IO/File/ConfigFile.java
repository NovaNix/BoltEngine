package IO.File;

import java.util.HashMap;

public class ConfigFile
{

	HashMap<String, String> Values = new HashMap<String, String>();

	public ConfigFile(String File, String Comment, String Split)
	{
		String[] Lines = EasyLoader.ExtractFileLines(File, Comment);

		for (int i = 0; i < Lines.length; i++)
		{
			String Name = Lines[i].split(Split)[0];

			String Value = Lines[i].replaceFirst(Name + Split, "");

			while (Value.startsWith(" "))
			{
				Value = Value.replaceFirst(" ", "");
			}

			Values.put(Name, Value);
		}
	}

	public String GetValue(String VarName)
	{
		return Values.get(VarName);
	}
}
