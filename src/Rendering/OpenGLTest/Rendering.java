package Rendering.OpenGLTest;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.awt.Color;
import java.awt.Font;

import org.joml.Matrix4f;

import Vectors.Vector2f;

public class Rendering
{

	private static Shader CurrentShader;

	private static Shader DrawImage = new Shader("/vertexshaders/defaultshader.vert", true, "/fragmentshaders/drawimage.frag", true);
	private static Shader DrawCamera = new Shader("/vertexshaders/drawcamera.vert", true, "/fragmentshaders/drawimage.frag", true);
	private static Shader DrawOval = new Shader("/vertexshaders/defaultshader.vert", true, "/fragmentshaders/drawoval.frag", true);
	private static Shader DrawShape = new Shader("/vertexshaders/defaultshader.vert", true, "/fragmentshaders/drawshape.frag", true);
	private static Shader DrawPoint = new Shader("/vertexshaders/defaultshader.vert", true, "/fragmentshaders/drawpoint.frag", true);

	private static Shader DrawSprite = new Shader("/vertexshaders/defaultshader.vert", true, "/fragmentshaders/drawsprite.frag", true);
	
	private static Matrix4f Projection;
	private static Matrix4f CameraModel;

	public static void Start(Matrix4f CamModel, Matrix4f CamProjection)
	{
		CameraModel = CamModel;
		Projection = CamProjection;
	}

	public static void SetCameraModel(Matrix4f Model)
	{
		CameraModel = Model;
	}

	public static void SetCameraProjection(Matrix4f CamProjection)
	{
		Projection = CamProjection;
	}

	// All renderable types

	// Raw (The rendering location is not changed by the observer's size)
	// RS (The rendering location is shifted depending on the observer's size)
	// Referenced (The rendering location is shifted depending on the reference
	// point)

	// All renderable objects

	// Images
	// Lines
	// Rectangles
	// Text
	// Point
	// Oval

	// Dynamic

	public static void RenderImage(RenderingType Type, Texture Sprite, Vector2f Position, Vector2f Scale, int Rotation)
	{
		switch (Type)
		{
			case Raw:
				RenderRawImage(Sprite, Position, Scale, Rotation);
				break;
			case RS:
				RenderRSImage(Sprite, Position, Scale, Rotation);
				break;
			case Referenced:
				RenderReferencedImage(Sprite, Position, Scale, Rotation);
				break;
		}
	}

	public static void RenderBox(RenderingType Type, Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		switch (Type)
		{
			case Raw:
				RenderRawBox(Point1, Point1, Thickness, Hue);
				break;
			case RS:
				RenderRSBox(Point1, Point1, Thickness, Hue);
				break;
			case Referenced:
				RenderReferencedBox(Point1, Point1, Thickness, Hue);
				break;
		}
	}

	public static void RenderLine(RenderingType Type, Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		switch (Type)
		{
			case Raw:
				RenderRawLine(Point1, Point1, Thickness, Hue);
				break;
			case RS:
				RenderRSLine(Point1, Point1, Thickness, Hue);
				break;
			case Referenced:
				RenderReferencedLine(Point1, Point1, Thickness, Hue);
				break;
		}
	}

	public static void RenderOval(RenderingType Type, Vector2f Position, Vector2f Scale)
	{
		switch (Type)
		{
			case Raw:
				RenderRawOval(Position, Scale);
				break;
			case RS:
				RenderRSOval(Position, Scale);
				break;
			case Referenced:
				RenderReferencedOval(Position, Scale);
				break;
		}
	}

	public static void RenderPoint(RenderingType Type, Vector2f Point, Color Hue)
	{
		switch (Type)
		{
			case Raw:
				RenderRawPoint(Point, Hue);
				break;
			case RS:
				RenderRSPoint(Point, Hue);
				break;
			case Referenced:
				RenderReferencedPoint(Point, Hue);
				break;
		}
	}

	public static void RenderText(RenderingType Type, Vector2f Position, String Text, Font Style, Color Hue)
	{
		switch (Type)
		{
			case Raw:
				RenderRawText(Position, Text, Style, Hue);
				break;
			case RS:
				RenderRSText(Position, Text, Style, Hue);
				break;
			case Referenced:
				RenderReferencedText(Position, Text, Style, Hue);
				break;
		}
	}

	// RAW RENDERING

	public static void RenderRawImage(Texture Sprite, Vector2f Position, Vector2f Scale, int Rotation)
	{
		EnableRaw();

		DrawImage(Sprite, Position, Scale, Rotation);
	}

	public static void RenderRawImage(int Sprite, Vector2f Position, Vector2f Scale, int Rotation)
	{
		EnableRaw();

		DrawImage(Sprite, Position, Scale, Rotation);
	}

	public static void RenderRawBox(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		EnableRaw();

		DrawBox(Point1, Point2, Thickness, Hue);
	}

	public static void RenderRawLine(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		EnableRaw();

		DrawLine(Point1, Point2, Thickness, Hue);

	}

	public static void RenderRawOval(Vector2f Position, Vector2f Scale)
	{
		EnableRaw();

		DrawOval(Position, Scale);
	}

	public static void RenderRawPoint(Vector2f Point, Color Hue)
	{
		EnableRaw();

		DrawPoint(Point, Hue);
	}

	public static void RenderRawText(Vector2f Position, String Text, Font Style, Color Hue)
	{
		EnableRaw();

		DrawText(Position, Text, Style, Hue);
	}

	// Rendering Supported Rendering

	public static void RenderRSImage(Texture Sprite, Vector2f Position, Vector2f Scale, int Rotation)
	{
		EnableRS();

		DrawImage(Sprite, Position, Scale, Rotation);
	}

	public static void RenderRSImage(int Sprite, Vector2f Position, Vector2f Scale, int Rotation)
	{
		EnableRS();

		DrawImage(Sprite, Position, Scale, Rotation);
	}

	public static void RenderRSBox(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		EnableRS();

		DrawBox(Point1, Point2, Thickness, Hue);
	}

	public static void RenderRSLine(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		EnableRS();

		DrawLine(Point1, Point2, Thickness, Hue);

	}

	public static void RenderRSOval(Vector2f Position, Vector2f Scale)
	{
		EnableRS();

		DrawOval(Position, Scale);
	}

	public static void RenderRSPoint(Vector2f Point, Color Hue)
	{
		EnableRS();

		DrawPoint(Point, Hue);
	}

	public static void RenderRSText(Vector2f Position, String Text, Font Style, Color Hue)
	{
		EnableRS();

		DrawText(Position, Text, Style, Hue);
	}

	// Referenced Rendering

	public static void RenderReferencedImage(Texture Sprite, Vector2f Position, Vector2f Scale, int Rotation)
	{
		EnableReferenced();

		DrawImage(Sprite, Position, Scale, Rotation);
	}

	public static void RenderReferencedImage(int Sprite, Vector2f Position, Vector2f Scale, int Rotation)
	{
		EnableReferenced();

		DrawImage(Sprite, Position, Scale, Rotation);
	}

	public static void RenderReferencedBox(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		EnableReferenced();

		DrawBox(Point1, Point2, Thickness, Hue);
	}

	public static void RenderReferencedLine(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		EnableReferenced();

		DrawLine(Point1, Point2, Thickness, Hue);

	}

	public static void RenderReferencedOval(Vector2f Position, Vector2f Scale)
	{
		EnableReferenced();

		DrawOval(Position, Scale);
	}

	public static void RenderReferencedPoint(Vector2f Point, Color Hue)
	{
		EnableReferenced();

		DrawPoint(Point, Hue);
	}

	public static void RenderReferencedText(Vector2f Position, String Text, Font Style, Color Hue)
	{
		EnableReferenced();

		DrawText(Position, Text, Style, Hue);
	}

	private static void DrawImage(Texture Sprite, Vector2f Position, Vector2f Scale, int Rotation)
	{
		DrawImage(Sprite.GetID(), Position, Scale, Rotation);
	}

	private static float[] BoxV = { -1.f, 1.0f, // TOP LEFT
			1.0f, 1.0f, // TOP RIGHT
			1.0f, -1.0f, // BOTTEM RIGHT
			-1.0f, -1.0f // BOTTEM LEFT
	};

	private static float[] BoxT = { 0, 0, 1, 0, 1, 1, 0, 1 };

	private static int[] BoxI = { 0, 1, 2, 2, 3, 0 };

	private static VertexBufferObject Box = new VertexBufferObject(new ArrayBuffer[] { new ArrayBuffer(BoxV, 2), new ArrayBuffer(BoxT, 2) }, BoxI);

	private static void DrawImage(int Sprite, Vector2f Position, Vector2f Scale, int Rotation)
	{
		ApplyTexture(Sprite, 0);
		ApplyShader(DrawImage);

		Matrix4f ObjectModel = new Matrix4f();
		ObjectModel.scale(Scale.GetX(), Scale.GetY(), 0);
		ObjectModel.rotateZ((float) Math.toRadians(Rotation));
		ObjectModel.translate(Position.GetX(), Position.GetY(), 0);

		if (Raw)
		{
			// DrawImage.SetUniform("CameraModel", new Matrix4f());
		}

		else if (RS)
		{
			DrawImage.SetUniform("CameraModel", new Matrix4f());
		}

		else
		{
			DrawImage.SetUniform("CameraModel", CameraModel);
		}

		// DrawImage.SetUniform("ObjectModel", ObjectModel);
		DrawImage.SetUniform("Texture1", 0);
		// DrawImage.SetUniform("Projection", Projection);
		// DrawImage.SetUniform("Hue", 0f, 0f, 0f, 1f);
		// DrawImage.SetUniform("LayerDepth", 0f);

		Draw(Box, GL_TRIANGLES);

	}

	private static void DrawBox(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		ApplyShader(DrawShape);
	}

	private static void DrawLine(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		ApplyShader(DrawShape);
	}

	private static void DrawOval(Vector2f Position, Vector2f Scale)
	{
		ApplyShader(DrawOval);
	}

	private static void DrawPoint(Vector2f Point, Color Hue)
	{
		ApplyShader(DrawPoint);
	}

	private static void DrawText(Vector2f Position, String Text, Font Style, Color Hue)
	{

	}

	public enum RenderingType
	{
		Raw, RS, Referenced
	}

	private static Matrix4f GenerateModel(Vector2f Position, Vector2f Scale, float Rotation)
	{
		return null;	
	}
	
	public static void Draw(VertexBufferObject VBO, Shader S, int Type)
	{
		ApplyShader(S);

		Draw(VBO, Type);
	}

	public static void Draw(VertexBufferObject VBO, int Type)
	{
		ArrayBuffer[] Buffers = VBO.GetBuffers();

		for (int i = 0; i < Buffers.length; i++)
		{
			glBindBuffer(GL_ARRAY_BUFFER, Buffers[i].GetID());
			glBufferData(GL_ARRAY_BUFFER, Buffers[i].GetDataBuffer(), GL_STATIC_DRAW);
			glEnableVertexAttribArray(i);
			glVertexAttribPointer(i, Buffers[i].GetGroupSize(), GL_FLOAT, false, 0, 0);
		}

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, VBO.GetIndexID());

		// Type for images and shapes should be GL_TRIANGLES
		// Type for lines should be GL_LINES
		// Type for points should be GL_POINTS

		glDrawElements(Type, VBO.GetIndex().length, GL_UNSIGNED_INT, 0);

		for (int i = 0; i < Buffers.length; i++)
		{
			glDisableVertexAttribArray(i);
			System.out.println("Unbound buffer " + i);
		}
	}

	static float[] BoxT2 = { 0, 1, 1, 1, 1, 0, 0, 0 };

	static VertexBufferObject CamBox = new VertexBufferObject(new ArrayBuffer[] { new ArrayBuffer(BoxV, 2), new ArrayBuffer(BoxT2, 2) }, BoxI);

	public static void DrawCamera(int CamTexture)
	{
		ApplyTexture(CamTexture, 0);
		ApplyShader(DrawCamera);

		Draw(CamBox, GL_TRIANGLES);
	}

	private static boolean Raw;
	private static boolean RS;
	private static boolean Referenced;

	private static void EnableRaw()
	{
		Raw = true;
		RS = false;
		Referenced = false;
	}

	private static void EnableRS()
	{
		Raw = false;
		RS = true;
		Referenced = false;
	}

	private static void EnableReferenced()
	{
		Raw = false;
		RS = false;
		Referenced = true;
	}

	private static void ApplyTexture(Texture Apply, int Sampler)
	{
		if ((Sampler >= 0) && (Sampler <= 31))
		{
			glActiveTexture(GL_TEXTURE0 + Sampler);
			glBindTexture(GL_TEXTURE_2D, Apply.GetID());
		}
	}

	private static void ApplyTexture(int Apply, int Sampler)
	{
		if ((Sampler >= 0) && (Sampler <= 31))
		{
			glActiveTexture(GL_TEXTURE0 + Sampler);
			glBindTexture(GL_TEXTURE_2D, Apply);
		}
	}

	private static void ApplyShader(Shader Apply)
	{
		glUseProgram(Apply.GetShaderID());
		CurrentShader = Apply;
	}

	private static void ClearTexture()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
