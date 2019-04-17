package Rendering.OpenGLTest;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Shader
{

	static HashMap<String, Integer> InternalShaders = new HashMap<String, Integer>();
	static HashMap<String, Integer> ExternalShaders = new HashMap<String, Integer>();
	
	int ShaderID;

	int VertexHandle;
	int FragmentHandle;

	public Shader(String VertexPath, boolean VertexInternal, String FragmentPath, boolean FragmentInternal)
	{
		ShaderID = glCreateProgram();

		String VertexShader = ReadShaderFile(VertexPath, VertexInternal);

		String FragmentShader = ReadShaderFile(FragmentPath, FragmentInternal);

		VertexHandle = glCreateShader(GL_VERTEX_SHADER);
		FragmentHandle = glCreateShader(GL_FRAGMENT_SHADER);

		glShaderSource(VertexHandle, VertexShader);
		glCompileShader(VertexHandle);

		if (glGetShaderi(VertexHandle, GL_COMPILE_STATUS) != 1)
		{
			System.err.println(glGetShaderInfoLog(VertexHandle));
			System.exit(1);
		}

		glShaderSource(FragmentHandle, FragmentShader);
		glCompileShader(FragmentHandle);

		if (glGetShaderi(FragmentHandle, GL_COMPILE_STATUS) != 1)
		{
			System.err.println(glGetShaderInfoLog(FragmentHandle));
			System.exit(1);
		}

		glAttachShader(ShaderID, VertexHandle);
		glAttachShader(ShaderID, FragmentHandle);

		glBindAttribLocation(ShaderID, 0, "vertices");
		glBindAttribLocation(ShaderID, 1, "textures");

		glLinkProgram(ShaderID);

		if (glGetProgrami(ShaderID, GL_LINK_STATUS) != 1)
		{
			System.err.println(glGetProgramInfoLog(ShaderID));
			System.exit(1);
		}

		glValidateProgram(ShaderID);

		if (glGetProgrami(ShaderID, GL_VALIDATE_STATUS) != 1)
		{
			System.err.println(glGetProgramInfoLog(ShaderID));
			System.exit(1);
		}

	}

	protected int GenerateShader(String FilePath, boolean Internal, int ShaderType)
	{
		
		if (Internal)
		{
			if (Internal.containsKey(FilePath)
			{
				return Internal.get(FilePath);
			}
		}
		
		else 
		{
			if (External.containsKey(FilePath)
			{
				return External.get(FilePath);
			}
		}
			    
		int ID = glCreateShader(ShaderType);
			
		String ShaderCode = ReadShaderFile(FilePath, Internal);;
		glShaderSource(ID, ShaderCode);
		glCompileShader(ID);
	
		if (glGetShaderi(ID, GL_COMPILE_STATUS) != 1)
		{
			System.err.println(glGetShaderInfoLog(VertexHandle));
			System.exit(1);
		}
				
		return ID;	    
	}
	
	protected String ReadShaderFile(String FilePath, boolean Internal)
	{
		BufferedReader Reader = null;

		if (Internal)
		{

			ClassLoader Loader = Thread.currentThread().getContextClassLoader();

			InputStream Input = Loader.getResourceAsStream(FilePath);

			Reader InputReader = new InputStreamReader(Input);
			Reader = new BufferedReader(InputReader);
		}

		else
		{
			FileReader FReader;
			try
			{
				FReader = new FileReader(FilePath);
				Reader = new BufferedReader(FReader);
			} catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		StringBuilder ShaderText = new StringBuilder();

		String Line;

		try
		{
			while ((Line = Reader.readLine()) != null)
			{
				ShaderText.append(Line);
				ShaderText.append("\n");

				Reader.close();
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ShaderText.toString();

	}

	public int GetShaderID()
	{
		return ShaderID;
	}

}
