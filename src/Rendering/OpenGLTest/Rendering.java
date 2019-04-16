package Rendering.OpenGLTest;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.awt.Color;
import java.awt.Font;

import Geometry.Shapes.Rectangle;
import Vectors.Vector2f;

public class Rendering
{

	Shader CurrentShader;

	Shader DrawImage;
	Shader DrawOval;
	Shader DrawShape;
	Shader DrawPoint;

	private Vector2f ReferencePoint;

	private Vector2f RSOffset;
	private Vector2f RSScale;

	private Rectangle CameraCollision;

	public void Start(Vector2f Size, Vector2f Reference, Vector2f Offset, Vector2f Scale, Rectangle CameraPOV)
	{
		ReferencePoint = Reference;

		RSOffset = Offset;
		RSScale = Scale;

		CameraCollision = CameraPOV;
	}

	public void SetReferencePoint(Vector2f Point)
	{
		ReferencePoint = Point;
	}

	public Vector2f GetReferencePoint()
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

	public void RenderImage(RenderingType Type, Texture Sprite, Vector2f Position, Vector2f Scale, int Rotation)
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

	public void RenderBox(RenderingType Type, Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
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

	public void RenderLine(RenderingType Type, Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
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

	public void RenderOval(RenderingType Type, Vector2f Position, Vector2f Scale)
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

	public void RenderPoint(RenderingType Type, Vector2f Point, Color Hue)
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

	public void RenderText(RenderingType Type, Vector2f Position, String Text, Font Style, Color Hue)
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

	public void RenderRawImage(Texture Sprite, Vector2f Position, Vector2f Scale, int Rotation)
	{
		EnableRaw();

		DrawImage(Sprite, Position, Scale, Rotation);
	}

	public void RenderRawBox(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		EnableRaw();

		DrawBox(Point1, Point2, Thickness, Hue);
	}

	public void RenderRawLine(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		EnableRaw();

		DrawLine(Point1, Point2, Thickness, Hue);

	}

	public void RenderRawOval(Vector2f Position, Vector2f Scale)
	{
		EnableRaw();

		DrawOval(Position, Scale);
	}

	public void RenderRawPoint(Vector2f Point, Color Hue)
	{
		EnableRaw();

		DrawPoint(Point, Hue);
	}

	public void RenderRawText(Vector2f Position, String Text, Font Style, Color Hue)
	{
		EnableRaw();

		DrawText(Position, Text, Style, Hue);
	}

	// Rendering Supported Rendering

	public void RenderRSImage(Texture Sprite, Vector2f Position, Vector2f Scale, int Rotation)
	{
		EnableRS();

		DrawImage(Sprite, Position, Scale, Rotation);
	}

	public void RenderRSBox(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		EnableRS();

		DrawBox(Point1, Point2, Thickness, Hue);
	}

	public void RenderRSLine(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		EnableRS();

		DrawLine(Point1, Point2, Thickness, Hue);

	}

	public void RenderRSOval(Vector2f Position, Vector2f Scale)
	{
		EnableRS();

		DrawOval(Position, Scale);
	}

	public void RenderRSPoint(Vector2f Point, Color Hue)
	{
		EnableRS();

		DrawPoint(Point, Hue);
	}

	public void RenderRSText(Vector2f Position, String Text, Font Style, Color Hue)
	{
		EnableRS();

		DrawText(Position, Text, Style, Hue);
	}

	// Referenced Rendering

	public void RenderReferencedImage(Texture Sprite, Vector2f Position, Vector2f Scale, int Rotation)
	{
		EnableReferenced();

		DrawImage(Sprite, Position, Scale, Rotation);
	}

	public void RenderReferencedBox(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		EnableReferenced();

		DrawBox(Point1, Point2, Thickness, Hue);
	}

	public void RenderReferencedLine(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		EnableReferenced();

		DrawLine(Point1, Point2, Thickness, Hue);

	}

	public void RenderReferencedOval(Vector2f Position, Vector2f Scale)
	{
		EnableReferenced();

		DrawOval(Position, Scale);
	}

	public void RenderReferencedPoint(Vector2f Point, Color Hue)
	{
		EnableReferenced();

		DrawPoint(Point, Hue);
	}

	public void RenderReferencedText(Vector2f Position, String Text, Font Style, Color Hue)
	{
		EnableReferenced();

		DrawText(Position, Text, Style, Hue);
	}

	private void DrawImage(Texture Sprite, Vector2f Position, Vector2f Scale, int Rotation)
	{
		ApplyShader(DrawImage);
		ApplyTexture(Sprite, 0);
	}

	private void DrawBox(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		ApplyShader(DrawShape);
	}

	private void DrawLine(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		ApplyShader(DrawShape);
	}

	private void DrawOval(Vector2f Position, Vector2f Scale)
	{
		ApplyShader(DrawOval);
	}

	private void DrawPoint(Vector2f Point, Color Hue)
	{
		ApplyShader(DrawPoint);
	}

	private void DrawText(Vector2f Position, String Text, Font Style, Color Hue)
	{

	}

	public enum RenderingType
	{
		Raw, RS, Referenced
	}

	private void EnableRaw()
	{

	}

	private void EnableRS()
	{

	}

	private void EnableReferenced()
	{

	}

	private void ApplyTexture(Texture Apply, int Sampler)
	{
		if ((Sampler >= 0) && (Sampler <= 31))
		{
			glActiveTexture(GL_TEXTURE0 + Sampler);
			glBindTexture(GL_TEXTURE_2D, Apply.GetID());
		}
	}

	private void ApplyShader(Shader Apply)
	{
		glUseProgram(Apply.GetShaderID());
		this.CurrentShader = Apply;
	}

	private void ClearTexture()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
