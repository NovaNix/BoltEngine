package IO;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

import java.util.HashMap;

import org.lwjgl.glfw.GLFWKeyCallbackI;

public class Keyboard implements GLFWKeyCallbackI
{

	static Keyboard CallbackHandler = new Keyboard();

	static HashMap<Integer, Boolean> Keys = new HashMap<Integer, Boolean>();
	static HashMap<Integer, Boolean> Typed = new HashMap<Integer, Boolean>();

	// Keycodes pulled from internet
	// Keycodes are from javascript, so they may be different

	public static final int KEY_BACKSPACE = 8;
	public static final int KEY_TAB = 9;
	public static final int KEY_ENTER = 13;
	public static final int KEY_SHIFT = 16;
	public static final int KEY_CTRL = 17;
	public static final int KEY_ALT = 18;
	public static final int KEY_PAUSE = 19;
	public static final int KEY_CAPSLOCK = 20;
	public static final int KEY_ESCAPE = 27;
	public static final int KEY_PAGEUP = 33;
	public static final int KEY_PAGEDOWN = 34;
	public static final int KEY_END = 35;
	public static final int KEY_HOME = 36;
	public static final int KEY_LEFTARROW = 37;
	public static final int KEY_UPARROW = 38;
	public static final int KEY_RIGHTARROW = 39;
	public static final int KEY_DOWNARROW = 40;
	public static final int KEY_INSERT = 45;
	public static final int KEY_DELETE = 46;
	public static final int KEY_0 = 48;
	public static final int KEY_1 = 49;
	public static final int KEY_2 = 50;
	public static final int KEY_3 = 51;
	public static final int KEY_4 = 52;
	public static final int KEY_5 = 53;
	public static final int KEY_6 = 54;
	public static final int KEY_7 = 55;
	public static final int KEY_8 = 56;
	public static final int KEY_9 = 57;
	public static final int KEY_A = 65;
	public static final int KEY_B = 66;
	public static final int KEY_C = 67;
	public static final int KEY_D = 68;
	public static final int KEY_E = 69;
	public static final int KEY_F = 70;
	public static final int KEY_G = 71;
	public static final int KEY_H = 72;
	public static final int KEY_I = 73;
	public static final int KEY_J = 74;
	public static final int KEY_K = 75;
	public static final int KEY_L = 76;
	public static final int KEY_M = 77;
	public static final int KEY_N = 78;
	public static final int KEY_O = 79;
	public static final int KEY_P = 80;
	public static final int KEY_Q = 81;
	public static final int KEY_R = 82;
	public static final int KEY_S = 83;
	public static final int KEY_T = 84;
	public static final int KEY_U = 85;
	public static final int KEY_V = 86;
	public static final int KEY_W = 87;
	public static final int KEY_X = 88;
	public static final int KEY_Y = 89;
	public static final int KEY_Z = 90;
	public static final int KEY_LEFTWINDOWKEY = 91;
	public static final int KEY_RIGHTWINDOWKEY = 92;
	public static final int KEY_SELECTKEY = 93;
	public static final int KEY_NUMPAD0 = 96;
	public static final int KEY_NUMPAD1 = 97;
	public static final int KEY_NUMPAD2 = 98;
	public static final int KEY_NUMPAD3 = 99;
	public static final int KEY_NUMPAD4 = 100;
	public static final int KEY_NUMPAD5 = 101;
	public static final int KEY_NUMPAD6 = 102;
	public static final int KEY_NUMPAD7 = 103;
	public static final int KEY_NUMPAD8 = 104;
	public static final int KEY_NUMPAD9 = 105;
	public static final int KEY_MULTIPLY = 106;
	public static final int KEY_ADD = 107;
	public static final int KEY_SUBTRACT = 109;
	public static final int KEY_DECIMALPOINT = 110;
	public static final int KEY_DIVIDE = 111;
	public static final int KEY_F1 = 112;
	public static final int KEY_F2 = 113;
	public static final int KEY_F3 = 114;
	public static final int KEY_F4 = 115;
	public static final int KEY_F5 = 116;
	public static final int KEY_F6 = 117;
	public static final int KEY_F7 = 118;
	public static final int KEY_F8 = 119;
	public static final int KEY_F9 = 120;
	public static final int KEY_F10 = 121;
	public static final int KEY_F11 = 122;
	public static final int KEY_F12 = 123;
	public static final int KEY_NUMLOCK = 144;
	public static final int KEY_SCROLLLOCK = 145;
	public static final int KEY_SEMICOLON = 186;
	public static final int KEY_EQUALSIGN = 187;
	public static final int KEY_COMMA = 188;
	public static final int KEY_DASH = 189;
	public static final int KEY_PERIOD = 190;
	public static final int KEY_FORWARDSLASH = 191;
	public static final int KEY_GRAVEACCENT = 192;
	public static final int KEY_OPENBRACKET = 219;
	public static final int KEY_BACKSLASH = 220;
	public static final int KEY_CLOSEBRAKET = 221;
	public static final int KEY_SINGLEQUOTE = 222;

	public static void InitKeyboard(long WindowHandle)
	{
		glfwSetKeyCallback(WindowHandle, CallbackHandler);
	}

	public static boolean KeyDown(int Key)
	{
		if (Keys.containsKey(Key))
		{
			return Keys.get(Key);
		}

		return false;
	}

	public static boolean KeyTyped(int Key)
	{
		if (Typed.containsKey(Key))
		{
			boolean TypedValue = Typed.get(Key);
			
			Typed.replace(Key, false);
			
			return TypedValue;
		}
		
		return false;
	}
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods)
	{

		if (action == GLFW_RELEASE)
		{
			Keys.put(key, false);
			Typed.put(key, true);
		}

		else if (action == GLFW_PRESS)
		{
			Keys.put(key, true);
		}
	}

}
