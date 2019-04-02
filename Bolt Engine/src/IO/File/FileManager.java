package IO.File;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager
{

	String Directory;
	
	ArrayList<String> FileData = new ArrayList<String>();
	
	public FileManager(String Directory)
	{
		this.Directory = Directory;
		
		InitiateFile();
	}
	
	private void InitiateFile()
	{
		File Location = new File(Directory);

		if (Location.exists() && !Location.isDirectory())
		{
			BufferedReader Reader;
			int CurrentLine = 0;
			
			try
			{

				Reader = new BufferedReader(new FileReader(new File(Directory)));
				String Line;

				while ((Line = Reader.readLine()) != null)
				{
					FileData.add(Line);

					CurrentLine++;
				}

				Reader.close();
			} catch (Exception e)
			{
				System.out.println("Issue reading line " + CurrentLine + "!");
				e.printStackTrace();
			} 
		}
		
		else if (!Location.exists())
		{
			try
			{
				Location.createNewFile();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		else
		{
			// THROW ERROR
		}
	}
	
	public void Save()
	{
		SaveTo(Directory);
	}
	
	public void SaveTo(String Directory)
	{
		BufferedWriter BufferedOutputStream;
		
		try {
			
			BufferedOutputStream = new BufferedWriter(new FileWriter(Directory + ".temp"));

			for (int i = 0; i < FileData.size(); i++)
			{
				BufferedOutputStream.write(FileData.get(i) + "\n");
			}

			BufferedOutputStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File OriginalFile = new File(Directory);
	    OriginalFile.delete();

	    File NewFile = new File(Directory + ".temp");
	    NewFile.renameTo(OriginalFile);
	}
	
	public void WriteToLine(int Line, String Data)
	{
		if (HasLine(Line))
		{
			FileData.set(Line, FileData.get(Line) + Data);
		}
	}
	
	public void ReplaceLine(int Line, String Data)
	{
		if (HasLine(Line))
		{
			FileData.set(Line, Data);
		}
	}
	
	public void Write(String Line)
	{
		FileData.set(FileData.size(), FileData.get(FileData.size()) + Line);
	}

	public void WriteLine(String Line)
	{
		FileData.add(Line);
	}
	
	public void WriteLines(String[] Lines)
	{
		for (int i = 0; i < Lines.length; i++)
		{
			FileData.add(Lines[i]);
		}
	}

	public String GetLine(int Line)
	{
		if (HasLine(Line))
		{
			return FileData.get(Line);
		}
		
		return null;
	}
	
	public String[] GetLines(int Start, int Stop)
	{
		if (HasLine(Start) && HasLine(Stop))
		{
			String[] SelectedLines = new String[Stop - Start];
			
			for (int p = Start, i = 0; p <= Stop; p++, i++)
			{
				SelectedLines[i] = FileData.get(p);
			} 
			
			return SelectedLines;
		}
		
		else
		{
			return null;
		}
	}
	
	public String[] GetLines(int[] Lines)
	{
		String[] SelectedLines = new String[Lines.length];
		
		for (int i = 0; i < Lines.length; i++)
		{
			if (HasLine(Lines[i]))
			{
				SelectedLines[i] = FileData.get(Lines[i]);
			}
			
			else
			{
				return null;
			}
		}
		
		return SelectedLines;
	}

	public String[] GetAllLines()
	{
		String[] Lines = new String[FileData.size()];
		
		Lines = FileData.toArray(Lines);
		
		return Lines;
	}
	
	public boolean HasLine(int Line)
	{
		return 0 <= Line && Line < FileData.size();
	}
	
	public int GetLineCount()
	{
		return FileData.size();
	}
	
	public void Clear()
	{
		FileData.clear();
	}
	
}
