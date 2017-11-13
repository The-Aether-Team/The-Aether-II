package com.gildedgames.orbis.common.util;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.FloatBuffer;

public class OpenGLHelper
{

	/**
	 * Hacky method to check if we're in a gui
	 * rendering context right now. Open to other
	 * solutions, just the current one that works
	 * for me.
	 * @return Whether or not the OpenGL state is
	 * render in a GUI context.
	 */
	public static boolean isInGuiContext()
	{
		// Create FloatBuffer that can hold 16 values.
		final FloatBuffer buf = BufferUtils.createFloatBuffer(16);

		// Get current modelview matrix:
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, buf);

		// Rewind buffer. Not sure if this is needed, but it can't hurt.
		buf.rewind();

		// Create a Matrix4f.
		final Matrix4f mat = new Matrix4f();

		// Load matrix from buf into the Matrix4f.
		mat.load(buf);

		return mat.m30 > 2.0;
	}

}
