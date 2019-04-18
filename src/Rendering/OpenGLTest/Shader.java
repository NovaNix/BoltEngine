package Rendering.OpenGLTest;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

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

		VertexHandle = GenerateShader(VertexPath, VertexInternal, GL_VERTEX_SHADER);
		FragmentHandle = GenerateShader(FragmentPath, FragmentInternal, GL_FRAGMENT_SHADER);

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
			if (InternalShaders.containsKey(FilePath))
			{
				return InternalShaders.get(FilePath);
			}
		}

		else
		{
			if (ExternalShaders.containsKey(FilePath))
			{
				return ExternalShaders.get(FilePath);
			}
		}

		int ID = glCreateShader(ShaderType);

		String ShaderCode = ReadShaderFile(FilePath, Internal);
		glShaderSource(ID, ShaderCode);
		glCompileShader(ID);

		if (glGetShaderi(ID, GL_COMPILE_STATUS) != 1)
		{
			System.err.println(glGetShaderInfoLog(VertexHandle));
			System.exit(1);
		}

		if (Internal)
		{
			InternalShaders.put(FilePath, ID);
		}

		else
		{
			ExternalShaders.put(FilePath, ID);
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
