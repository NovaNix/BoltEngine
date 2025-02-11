package Rendering.OpenGL;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1fv;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform2i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform3i;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform4i;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.HashMap;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

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

		glDeleteShader(VertexHandle);
		glDeleteShader(FragmentHandle);

	}

	protected int GenerateShader(String FilePath, boolean Internal, int ShaderType)
	{

		int ID = glCreateShader(ShaderType);

		String ShaderCode = ReadShaderFile(FilePath, Internal);
		glShaderSource(ID, ShaderCode);
		glCompileShader(ID);

		// System.out.println(glGetShaderInfoLog(ID));

		if (glGetShaderi(ID, GL_COMPILE_STATUS) != 1)
		{
			System.err.println("Error making shader" + FilePath);
			System.err.println(glGetShaderInfoLog(ID));
			System.exit(1);
		}

		return ID;
	}

	protected String ReadShaderFile(String FilePath, boolean Internal)
	{
		BufferedReader Reader = null;

		if (Internal)
		{
			InputStream Input = getClass().getResourceAsStream(FilePath);

			InputStreamReader InputReader = new InputStreamReader(Input);

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
			}

			Reader.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ShaderText.toString();

	}

	public void SetUniform(String Name, Matrix4f Value)
	{
		int Location = glGetUniformLocation(ShaderID, Name);

		UniformErrorCheck(Location, Name);

		FloatBuffer Data = BufferUtils.createFloatBuffer(16);
		Value.get(Data);

		glUniformMatrix4fv(Location, false, Data);

	}

	public void SetUniform(String Name, int Value)
	{
		int Location = glGetUniformLocation(ShaderID, Name);

		UniformErrorCheck(Location, Name);

		glUniform1i(Location, Value);

	}

	public void SetUniform(String Name, int Value1, int Value2)
	{
		int Location = glGetUniformLocation(ShaderID, Name);

		UniformErrorCheck(Location, Name);

		glUniform2i(Location, Value1, Value2);

	}

	public void SetUniform(String Name, int Value1, int Value2, int Value3)
	{
		int Location = glGetUniformLocation(ShaderID, Name);

		UniformErrorCheck(Location, Name);

		glUniform3i(Location, Value1, Value2, Value3);

	}

	public void SetUniform(String Name, int Value1, int Value2, int Value3, int Value4)
	{
		int Location = glGetUniformLocation(ShaderID, Name);

		UniformErrorCheck(Location, Name);

		glUniform4i(Location, Value1, Value2, Value3, Value4);

	}

	public void SetUniform(String Name, float Value)
	{
		int Location = glGetUniformLocation(ShaderID, Name);

		UniformErrorCheck(Location, Name);

		glUniform1f(Location, Value);

	}

	public void SetUniform(String Name, float Value1, float Value2)
	{
		int Location = glGetUniformLocation(ShaderID, Name);

		UniformErrorCheck(Location, Name);

		glUniform2f(Location, Value1, Value2);

	}

	public void SetUniform(String Name, float Value1, float Value2, float Value3)
	{
		int Location = glGetUniformLocation(ShaderID, Name);

		UniformErrorCheck(Location, Name);

		glUniform3f(Location, Value1, Value2, Value3);

	}

	public void SetUniform(String Name, float Value1, float Value2, float Value3, float Value4)
	{
		int Location = glGetUniformLocation(ShaderID, Name);

		UniformErrorCheck(Location, Name);

		glUniform4f(Location, Value1, Value2, Value3, Value4);

	}

	public void SetUniform(String Name, float[] Value)
	{
		int Location = glGetUniformLocation(ShaderID, Name);

		UniformErrorCheck(Location, Name);

		glUniform1fv(Location, Value);

	}

	// Using this method to quickly comment out for speed reasons
	private static void UniformErrorCheck(int Location, String Name)
	{
		if (Location == -1)
		{
			System.err.println("Tried to set an uniform that didnt exist: " + Name);
		}
	}

	public int GetShaderID()
	{
		return ShaderID;
	}

	public void Bind()
	{
		glUseProgram(ShaderID);
	}

	@Override
	public void finalize()
	{

	}

}
