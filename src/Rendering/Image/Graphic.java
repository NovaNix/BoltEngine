package Rendering.Image;

import Rendering.OpenGL.Shader;

public abstract class Graphic
{

	public abstract void BindGraphic();

	public abstract void BindShader();

	public abstract Shader GetDrawingShader();

}
