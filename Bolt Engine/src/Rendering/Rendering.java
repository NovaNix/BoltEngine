package Rendering;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import Geometry.Segmant;
import Geometry.Shapes.Rectangle;
import Vectors.Vector2f;

public class Rendering
{

	private static BufferedImage FinalImage;

	private static Vector2f ReferencePoint;

	public static RenderingHints[] Hints = { new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON), new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY), new RenderingHints(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE) };

	private static Graphics RenderingGraphics;
	private static Graphics2D RenderingGraphics2D;

	private static Vector2f RSOffset;
	private static Vector2f RSScale;

	private static Rectangle CameraCollision;

	public static void Start(Vector2f Size, Vector2f Reference, Vector2f Offset, Vector2f Scale, Rectangle CameraPOV)
	{
		FinalImage = new BufferedImage((int) Size.GetX(), (int) Size.GetY(), BufferedImage.TYPE_4BYTE_ABGR);

		ReferencePoint = Reference;

		RenderingGraphics = FinalImage.getGraphics();

		RenderingGraphics2D = (Graphics2D) RenderingGraphics.create();

		RenderingGraphics2D.addRenderingHints(Hints[0]);
		RenderingGraphics2D.addRenderingHints(Hints[1]);
		RenderingGraphics2D.addRenderingHints(Hints[2]);

		RSOffset = Offset;
		RSScale = Scale;

		CameraCollision = CameraPOV;
	}

	public static void SetReferencePoint(Vector2f Point)
	{
		Rendering.ReferencePoint = Point;
	}

	public static Vector2f GetReferencePoint()
	{
		return ReferencePoint;
	}

	public static Image GetProduct()
	{
		RenderingGraphics.dispose();

		return FinalImage;
	}

	public static void Clear()
	{
		FinalImage = null;
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

	public static void RenderImage(RenderingType Type, Image Texture, Vector2f Position, Vector2f Scale, int Rotation)
	{
		switch (Type)
		{
			case Raw:
				RenderRawImage(Texture, Position, Scale, Rotation);
				break;
			case RS:
				RenderRSImage(Texture, Position, Scale, Rotation);
				break;
			case Referenced:
				RenderReferencedImage(Texture, Position, Scale, Rotation);
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

	public static void RenderRawImage(Image Texture, Vector2f Position, Vector2f Scale, int Rotation)
	{

		int X1 = (int) (Position.GetX() + Scale.GetX() / 2);
		int Y1 = (int) (Position.GetY() + Scale.GetY() / 2);
		RenderingGraphics2D.rotate(Math.toRadians(Rotation), X1, Y1);
		RenderingGraphics2D.drawImage(Texture, (int) Position.GetX(), (int) Position.GetY(), (int) Scale.GetX(), (int) Scale.GetY(), null);
		RenderingGraphics2D.rotate(Math.toRadians(-Rotation), X1, Y1);

	}

	public static void RenderRawBox(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{

		RenderingGraphics2D.setStroke(new BasicStroke(Thickness));
		RenderingGraphics2D.setColor(Hue);
		RenderingGraphics2D.drawRect((int) Point1.GetX(), (int) Point1.GetY(), (int) Point2.GetX(), (int) Point2.GetY());

	}

	public static void RenderRawLine(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{

		RenderingGraphics2D.setStroke(new BasicStroke(Thickness));
		RenderingGraphics2D.setColor(Hue);
		RenderingGraphics2D.drawLine((int) Point1.GetX(), (int) Point1.GetY(), (int) Point2.GetX(), (int) Point2.GetY());

	}

	public static void RenderRawOval(Vector2f Position, Vector2f Scale)
	{
		RenderingGraphics2D.drawOval((int) Position.GetX(), (int) Position.GetY(), (int) Scale.GetX(), (int) Scale.GetY());
	}

	public static void RenderRawPoint(Vector2f Point, Color Hue)
	{

		RenderingGraphics2D.setColor(Hue);
		RenderingGraphics2D.fillRect((int) Point.GetX(), (int) Point.GetY(), 1, 1);

	}

	public static void RenderRawText(Vector2f Position, String Text, Font Style, Color Hue)
	{
		RenderingGraphics2D.setFont(Style);

		RenderingGraphics2D.setColor(Hue);

		RenderingGraphics2D.drawString(Text, Position.GetX(), Position.GetY());
	}

	// Rendering Supported Rendering

	public static void RenderRSImage(Image Texture, Vector2f Position, Vector2f Scale, int Rotation)
	{
		RSShift();

		RenderRawImage(Texture, Position, Scale, Rotation);

		UndoRSShift();
	}

	public static void RenderRSBox(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		RSShift();

		RenderRawBox(Point1, Point2, Thickness, Hue);

		UndoRSShift();
	}

	public static void RenderRSLine(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		RSShift();

		RenderRawLine(Point1, Point2, Thickness, Hue);

		UndoRSShift();
	}

	public static void RenderRSOval(Vector2f Position, Vector2f Scale)
	{
		RSShift();

		RenderRawOval(Position, Scale);

		UndoRSShift();
	}

	public static void RenderRSPoint(Vector2f Point, Color Hue)
	{
		RSShift();

		RenderRawPoint(Point, Hue);

		UndoRSShift();
	}

	public static void RenderRSText(Vector2f Position, String Text, Font Style, Color Hue)
	{
		RSShift();

		RenderRawText(Position, Text, Style, Hue);

		UndoRSShift();
	}

	private static void RSShift()
	{
		RenderingGraphics2D.translate(RSOffset.GetX(), RSOffset.GetY());
		RenderingGraphics2D.scale(RSScale.GetX(), RSScale.GetY());
	}

	private static void UndoRSShift()
	{
		RenderingGraphics2D.scale(1.0 / RSScale.GetX(), 1.0 / RSScale.GetY());
		RenderingGraphics2D.translate(-RSOffset.GetX(), -RSOffset.GetY());
	}

	// Referenced Rendering

	public static void RenderReferencedImage(Image Texture, Vector2f Position, Vector2f Scale, int Rotation)
	{

		ReferencedShift();
		RenderRSImage(Texture, Position, Scale, Rotation);
		UndoReferencedShift();

	}

	public static void RenderReferencedBox(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{

		ReferencedShift();
		RenderRSBox(Point1, Point2, Thickness, Hue);
		UndoReferencedShift();
		
	}

	public static void RenderReferencedLine(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		ReferencedShift();
		RenderRSLine(Point1, Point2, Thickness, Hue);
		UndoReferencedShift();
	}

	public static void RenderReferencedOval(Vector2f Position, Vector2f Scale)
	{

		ReferencedShift();
		RenderRSOval(Position, Scale);
		UndoReferencedShift();

	}

	public static void RenderReferencedPoint(Vector2f Point, Color Hue)
	{
		if (CameraCollision.CollidesWith(Point))
		{
			ReferencedShift();
			RenderRSPoint(Point, Hue);
			UndoReferencedShift();
		}
	}

	public static void RenderReferencedText(Vector2f Position, String Text, Font Style, Color Hue)
	{
		ReferencedShift();

		RenderRSText(Position, Text, Style, Hue);

		UndoReferencedShift();
	}

	static float TempXReferencedOffset;
	static float TempYReferencedOffset;

	private static void ReferencedShift()
	{
		Vector2f FlippedReference = ReferencePoint.Invert();

		TempXReferencedOffset = FlippedReference.GetX();
		TempYReferencedOffset = FlippedReference.GetY();

		RenderingGraphics2D.translate(TempXReferencedOffset, TempYReferencedOffset);
	}

	private static void UndoReferencedShift()
	{
		RenderingGraphics2D.translate(-TempXReferencedOffset, -TempYReferencedOffset);
	}

	public static Graphics2D GetRenderingGraphics2D()
	{
		return RenderingGraphics2D;
	}

	public enum RenderingType
	{
		Raw, RS, Referenced
	}

}