package Rendering.Handling;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.awt.Color;
import java.awt.Font;

import org.joml.Matrix4f;

import Rendering.Image.Texture;
import Rendering.OpenGL.ArrayBuffer;
import Rendering.OpenGL.Shader;
import Rendering.OpenGL.VertexArrayObject;
import Rendering.OpenGL.VertexBufferObject;
import Rendering.Text.TextImageCreator;
import Vectors.Vector2f;

public class Rendering
{

	// All the VBOs for the draw methods

	private static float[] BoxVertex = { -1f, 1f, 1f, 1f, 1f, -1f, -1f, -1f };
	private static float[] LineVertex = new float[] { 0, 0, 1, 1 };
	private static float[] BoxTexture = { 0, 1, 1, 1, 1, 0, 0, 0 };
	private static float[] PointVertex = { 0, 0 };

	private static int[] BoxIndex = { 0, 1, 2, 0, 2, 3 };

	private static float[] IdentityKernel = new float[] { 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f };
	private static float[] BlurKernel = new float[] { 1.0f / 16, 2.0f / 16, 1.0f / 16, 2.0f / 16, 4.0f / 16, 2.0f / 16, 1.0f / 16, 2.0f / 16, 1.0f / 16 };

	private static float[] ActiveKernel = IdentityKernel;

	private static VertexBufferObject FullScreenBox = new VertexBufferObject(new ArrayBuffer[] { new ArrayBuffer(BoxVertex, 2), new ArrayBuffer(BoxTexture, 2) }, BoxIndex);

	private static VertexBufferObject LineBox = new VertexBufferObject(new ArrayBuffer[] { new ArrayBuffer(BoxVertex, 2) });

	private static VertexBufferObject Line = new VertexBufferObject(new ArrayBuffer[] { new ArrayBuffer(LineVertex, 2) });

	private static VertexBufferObject PointBuffer = new VertexBufferObject(new ArrayBuffer[] { new ArrayBuffer(PointVertex, 2) });

	// All the Matrices needed for the shaders

	private static Matrix4f Projection;

	private static Matrix4f RawCameraModel = new Matrix4f();
	private static Matrix4f RSCameraModel;
	private static Matrix4f ReferencedCameraModel;

	static VertexArrayObject DrawImage = new VertexArrayObject()
	{

		@Override
		public void BindAttributes()
		{
			ArrayBuffer[] Buffers = FullScreenBox.GetBuffers();

			glBindBuffer(GL_ARRAY_BUFFER, Buffers[0].GetID());
			glEnableVertexAttribArray(0);
			glVertexAttribPointer(0, Buffers[0].GetGroupSize(), GL_FLOAT, false, 0, 0);

			glBindBuffer(GL_ARRAY_BUFFER, Buffers[1].GetID());
			glEnableVertexAttribArray(1);
			glVertexAttribPointer(1, Buffers[1].GetGroupSize(), GL_FLOAT, false, 0, 0);

			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, FullScreenBox.GetIndexID());

		}

		@Override
		public int GetSize()
		{
			return FullScreenBox.GetIndex().length;
		}
	};

	static VertexArrayObject DrawLine = new VertexArrayObject()
	{

		@Override
		public void BindAttributes()
		{
			ArrayBuffer[] Buffers = Line.GetBuffers();

			glBindBuffer(GL_ARRAY_BUFFER, Buffers[0].GetID());
			glEnableVertexAttribArray(0);
			glVertexAttribPointer(0, Buffers[0].GetGroupSize(), GL_FLOAT, false, 0, 0);

			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, Line.GetIndexID());

		}

		@Override
		public int GetSize()
		{
			return Line.GetIndex().length;
		}
	};

	static VertexArrayObject DrawPoint = new VertexArrayObject()
	{

		@Override
		public void BindAttributes()
		{
			ArrayBuffer[] Buffers = PointBuffer.GetBuffers();

			glBindBuffer(GL_ARRAY_BUFFER, Buffers[0].GetID());
			glEnableVertexAttribArray(0);
			glVertexAttribPointer(0, Buffers[0].GetGroupSize(), GL_FLOAT, false, 0, 0);

			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, PointBuffer.GetIndexID());

		}

		@Override
		public int GetSize()
		{
			return PointBuffer.GetIndex().length;
		}
	};

	static VertexArrayObject DrawBox = new VertexArrayObject()
	{

		@Override
		public void BindAttributes()
		{
			ArrayBuffer[] Buffers = LineBox.GetBuffers();

			glBindBuffer(GL_ARRAY_BUFFER, Buffers[0].GetID());
			glEnableVertexAttribArray(0);
			glVertexAttribPointer(0, Buffers[0].GetGroupSize(), GL_FLOAT, false, 0, 0);

			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, LineBox.GetIndexID());

		}

		@Override
		public int GetSize()
		{
			return LineBox.GetIndex().length;
		}
	};

	static VertexArrayObject DrawOval = new VertexArrayObject()
	{

		@Override
		public void BindAttributes()
		{
			ArrayBuffer[] Buffers = FullScreenBox.GetBuffers();

			glBindBuffer(GL_ARRAY_BUFFER, Buffers[0].GetID());
			glEnableVertexAttribArray(0);
			glVertexAttribPointer(0, Buffers[0].GetGroupSize(), GL_FLOAT, false, 0, 0);

			glBindBuffer(GL_ARRAY_BUFFER, Buffers[1].GetID());
			glEnableVertexAttribArray(1);
			glVertexAttribPointer(1, Buffers[1].GetGroupSize(), GL_FLOAT, false, 0, 0);

			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, FullScreenBox.GetIndexID());

		}

		@Override
		public int GetSize()
		{
			return FullScreenBox.GetIndex().length;
		}
	};

	// Prepares for rendering and applys the specified matrices
	public static void Start(Matrix4f RSCamModel, Matrix4f RefCamModel, Matrix4f CamProjection)
	{
		RSCameraModel = RSCamModel;
		ReferencedCameraModel = RefCamModel;

		Projection = CamProjection;
	}

	// All renderable types

	// Raw (The rendering location is not changed by the observer's size)
	// RS (The rendering location is shifted depending on the observer's size, NOT
	// YET IMPLEMENTED)
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

	public static void RenderRawImage(Texture Sprite, Vector2f Position, Vector2f Scale, float Rotation)
	{
		EnableRaw();

		DrawImage(Sprite, Position, Scale, Rotation);
	}

	public static void RenderRawImage(int Sprite, Vector2f SpriteSize, Vector2f Position, Vector2f Scale, float Rotation)
	{
		EnableRaw();

		DrawImage(Sprite, SpriteSize, Position, Scale, Rotation);
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

	public static void RenderRSImage(Texture Sprite, Vector2f Position, Vector2f Scale, float Rotation)
	{
		EnableRS();

		DrawImage(Sprite, Position, Scale, Rotation);
	}

	public static void RenderRSImage(int Sprite, Vector2f SpriteSize, Vector2f Position, Vector2f Scale, float Rotation)
	{
		EnableRS();

		DrawImage(Sprite, SpriteSize, Position, Scale, Rotation);
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

	public static void RenderReferencedImage(Texture Sprite, Vector2f Position, Vector2f Scale, float Rotation)
	{
		EnableReferenced();

		DrawImage(Sprite, Position, Scale, Rotation);
	}

	public static void RenderReferencedImage(int Sprite, Vector2f SpriteSize, Vector2f Position, Vector2f Scale, float Rotation)
	{
		EnableReferenced();

		DrawImage(Sprite, SpriteSize, Position, Scale, Rotation);
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

	// Pure drawing methods

	private static void DrawImage(Texture Sprite, Vector2f Position, Vector2f Scale, float Rotation)
	{
		DrawImage(Sprite.GetID(), Sprite.GetSize(), Position, Scale, Rotation);
	}

	private static void DrawImage(int Sprite, Vector2f SpriteSize, Vector2f Position, Vector2f Scale, float Rotation)
	{
		ApplyTexture(Sprite, 0);
		ApplyShader(DrawingShader.DrawImage);

		ActiveShader.SetUniform("CameraModel", ActiveCameraModel);

		ActiveShader.SetUniform("ImageSize", SpriteSize.GetX(), SpriteSize.GetY());

		ActiveShader.SetUniform("Kernel", ActiveKernel);

		ActiveShader.SetUniform("ObjectModel", GenerateModel(Position, Scale, Rotation));
		ActiveShader.SetUniform("Texture1", 0);
		ActiveShader.SetUniform("Projection", Projection);

		Draw(DrawImage, GL_TRIANGLES);

	}

	private static void DrawBox(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		ApplyShader(DrawingShader.DrawShape);

		glLineWidth(Thickness);

		ActiveShader.SetUniform("CameraModel", ActiveCameraModel);

		Vector2f ScaleVec = Point2.Derive();
		ScaleVec.Subtract(Point1);

		ActiveShader.SetUniform("ObjectModel", GenerateModel(Point1, ScaleVec, 0));
		ActiveShader.SetUniform("ShapeColor", Hue.getRed() / 255f, Hue.getGreen() / 255f, Hue.getBlue() / 255f, (Hue.getAlpha() / 255f));
		ActiveShader.SetUniform("Projection", Projection);

		Draw(DrawBox, GL_LINE_LOOP);
	}

	private static void DrawLine(Vector2f Point1, Vector2f Point2, float Thickness, Color Hue)
	{
		ApplyShader(DrawingShader.DrawLine);

		glLineWidth(Thickness);

		ActiveShader.SetUniform("CameraModel", ActiveCameraModel);

		ActiveShader.SetUniform("LinePosition", Point1.GetX(), Point1.GetY(), Point2.GetX(), Point2.GetY());

		ActiveShader.SetUniform("ShapeColor", Hue.getRed() / 255f, Hue.getGreen() / 255f, Hue.getBlue() / 255f, (Hue.getAlpha() / 255f));
		ActiveShader.SetUniform("Projection", Projection);

		Draw(DrawLine, GL_LINES);
	}

	private static void DrawOval(Vector2f Position, Vector2f Scale)
	{
		ApplyShader(DrawingShader.DrawOval);

		ActiveShader.SetUniform("CameraModel", ActiveCameraModel);
		ActiveShader.SetUniform("ObjectModel", GenerateOvalModel(Position, Scale));
		ActiveShader.SetUniform("Projection", Projection);

		ActiveShader.SetUniform("InnerColor", 0f, 0f, 0f, 1f);
		ActiveShader.SetUniform("OuterColor", 1f, 1f, 1f, 1f);

		Draw(DrawOval, GL_TRIANGLES);
	}

	private static void DrawPoint(Vector2f Point, Color Hue)
	{
		ApplyShader(DrawingShader.DrawPoint);

		ActiveShader.SetUniform("CameraModel", ActiveCameraModel);
		ActiveShader.SetUniform("ObjectModel", GeneratePointModel(Point));
		ActiveShader.SetUniform("Projection", Projection);

		ActiveShader.SetUniform("ShapeColor", Hue.getRed() / 255f, Hue.getGreen() / 255f, Hue.getBlue() / 255f, (Hue.getAlpha() / 255f));

		Draw(DrawPoint, GL_POINTS);

	}

	private static void DrawText(Vector2f Position, String Text, Font Style, Color Hue)
	{
		if (Text.equals(""))
		{
			return;
		}

		Texture TextImage = TextImageCreator.GenerateTextImage(Text, Hue, Style);

		DrawImage(TextImage, Position, TextImage.GetSize(), 0);
	}

	public enum RenderingType
	{
		Raw, RS, Referenced
	}

	private static Matrix4f GenerateModel(Vector2f Position, Vector2f Scale, float Rotation)
	{
		Matrix4f ObjectModel = new Matrix4f().translate(Position.GetX() + Scale.GetX() / 2, -(Position.GetY() + Scale.GetY() / 2), 0).rotateZ((float) Math.toRadians(-Rotation)).scale(Scale.GetX() / 2, -(Scale.GetY() / 2), 0);

		return ObjectModel;
	}

	private static Matrix4f GenerateOvalModel(Vector2f Position, Vector2f Scale)
	{
		Matrix4f ObjectModel = new Matrix4f().translate(Position.GetX(), -Position.GetY(), 0).scale(Scale.GetX() / 2, -(Scale.GetY() / 2), 0);

		return ObjectModel;
	}

	private static Matrix4f GeneratePointModel(Vector2f Position)
	{
		Matrix4f ObjectModel = new Matrix4f().translate(Position.GetX(), -Position.GetY(), 0);

		return ObjectModel;
	}

	public static void Draw(VertexArrayObject VAO, int Type)
	{
		glBindVertexArray(VAO.GetHandle());

		// Type for images and shapes should be GL_TRIANGLES
		// Type for lines should be GL_LINES
		// Type for points should be GL_POINTS

		glDrawElements(Type, VAO.GetSize(), GL_UNSIGNED_INT, 0);

	}

	public static void DrawCamera(int CamTexture)
	{
		ApplyTexture(CamTexture, 0);
		ApplyShader(DrawingShader.DrawCamera);

		Draw(DrawImage, GL_TRIANGLES);
	}

	// The current active camera model
	private static Matrix4f ActiveCameraModel;

	public static void EnableRaw()
	{
		ActiveCameraModel = RawCameraModel;
	}

	public static void EnableRS()
	{
		ActiveCameraModel = RSCameraModel;
	}

	public static void EnableReferenced()
	{
		ActiveCameraModel = ReferencedCameraModel;
	}

	public static void EnableBlur()
	{
		ActiveKernel = BlurKernel;
	}

	public static void DisableBlur()
	{
		ActiveKernel = IdentityKernel;
	}

	// Applys the specified texture to the specified sampler
	private static void ApplyTexture(Texture Apply, int Sampler)
	{
		if ((Sampler >= 0) && (Sampler <= 31))
		{
			glActiveTexture(GL_TEXTURE0 + Sampler);
			glBindTexture(GL_TEXTURE_2D, Apply.GetID());
		}
	}

	// Applys the specified texture to the specified sampler
	private static void ApplyTexture(int Apply, int Sampler)
	{
		if ((Sampler >= 0) && (Sampler <= 31))
		{
			glActiveTexture(GL_TEXTURE0 + Sampler);
			glBindTexture(GL_TEXTURE_2D, Apply);
		}
	}

	private static Shader ActiveShader;

	// Applys the specified shader
	private static void ApplyShader(Shader Apply)
	{
		glUseProgram(Apply.GetShaderID());
		ActiveShader = Apply;
	}

	private static void ApplyShader(DrawingShader Apply)
	{
		glUseProgram(Apply.GetShader().GetShaderID());
		ActiveShader = Apply.GetShader();
	}

	public enum DrawingShader
	{
		DrawImage("/vertexshaders/defaultshader.vert", "/fragmentshaders/drawimage.frag"), DrawCamera("/vertexshaders/drawcamera.vert", "/fragmentshaders/drawimage.frag"), DrawOval("/vertexshaders/drawoval.vert", "/fragmentshaders/drawoval.frag"), DrawShape("/vertexshaders/drawshape.vert", "/fragmentshaders/drawshape.frag"), DrawPoint("/vertexshaders/defaultshader.vert", "/fragmentshaders/drawshape.frag"), DrawLine("/vertexshaders/drawline.vert", "/fragmentshaders/drawshape.frag"), DrawSprite("/vertexshaders/defaultshader.vert", "/fragmentshaders/drawsprite.frag");

		Shader DrawShader;

		private DrawingShader(String VPath, String FPath)
		{
			DrawShader = new Shader(VPath, true, FPath, true);
		}

		public Shader GetShader()
		{
			return DrawShader;
		}
	}

}
