package Rendering.OpenGLTest;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.awt.Color;
import java.awt.Font;

import Geometry.Shapes.Rectangle;
import Vectors.Vector2f;

public class Rendering
{

	static Shader CurrentShader;

	static Shader DrawImage;
	static Shader DrawOval;
	static Shader DrawShape;
	static Shader DrawPoint;

	private static Vector2f ReferencePoint;

	private static Vector2f RSOffset;
	private static Vector2f RSScale;

	private static Rectangle CameraCollision;

	public void Start(Vector2f Size, Vector2f Reference, Vector2f Offset, Vector2f Scale, Rectangle CameraPOV)
	{
		ReferencePoint = Reference;

		RSOffset = Offset;
		RSScale = Scale;

		CameraCollision = CameraPOV;
	}

	public static void SetReferencePoint(Vector2f Point)
	{
		ReferencePoint = Point;
	}

	public static Vector2f GetReferencePoint()
	{
		return ReferencePoint;
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
		ApplyShader(DrawImage);
		ApplyTexture(Sprite, 0);
	}

	private static void DrawImage(int Sprite, Vector2f Position, Vector2f Scale, int Rotation)
	{
		ApplyShader(DrawImage);
		ApplyTexture(Sprite, 0);
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
			glEnableVertexAttribArray(i);
			glBindBuffer(GL_ARRAY_BUFFER, Buffers[i].GetID());
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
		}
	}

	private static void EnableRaw()
	{

	}

	private static void EnableRS()
	{

	}

	private static void EnableReferenced()
	{

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
