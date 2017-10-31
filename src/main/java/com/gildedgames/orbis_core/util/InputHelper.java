package com.gildedgames.orbis_core.util;

import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis.client.util.rect.Rect;
import com.gildedgames.orbis.client.util.rect.RectHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class InputHelper
{

	private static final Minecraft mc = Minecraft.getMinecraft();

	private static ScaledResolution resolution;

	public static float getMouseX()
	{
		return (Mouse.getX() * getScreenWidth() / mc.displayWidth);
	}

	public static void setMouseX(final float x)
	{
		Mouse.setCursorPosition(MathHelper.floor((x / getScreenWidth() * mc.displayWidth)), Mouse.getY());
	}

	public static float getMouseY()
	{
		return (getScreenHeight() - Mouse.getY() * getScreenHeight() / mc.displayHeight - 1);
	}

	public static void setMouseY(final float y)
	{
		Mouse.setCursorPosition(Mouse.getX(), Display.getHeight() - MathHelper.floor((y / getScreenHeight() * mc.displayHeight + 1)));
	}

	public static boolean isHovered(final Rect dim)
	{
		if (dim == null)
		{
			return false;
		}

		return getMouseX() >= dim.x() && getMouseY() >= dim.y() && getMouseX() < dim.x() + dim.width() && getMouseY() < dim.y() + dim
				.height();
	}

	public static void refreshResolution()
	{
		resolution = new ScaledResolution(mc);
	}

	public static float getScreenWidth()
	{
		refreshResolution();

		return resolution.getScaledWidth();
	}

	public static float getScreenHeight()
	{
		refreshResolution();

		return resolution.getScaledHeight();
	}

	public static boolean isHovered(final RectHolder holder)
	{
		if (holder == null)
		{
			return false;
		}

		return isHovered(holder.dim());
	}

	public static float getScaleFactor()
	{
		return resolution.getScaleFactor();
	}

	public static Pos2D getCenter()
	{
		return Pos2D.flush(InputHelper.getScreenWidth() / 2, InputHelper.getScreenHeight() / 2);
	}

	public static Pos2D getBottomCenter()
	{
		return InputHelper.getCenter().clone().addY(InputHelper.getScreenHeight() / 2).flush();
	}

	public static Pos2D getBottomRight()
	{
		return InputHelper.getBottomCenter().clone().addX(InputHelper.getScreenWidth() / 2).flush();
	}

	public static Pos2D getBottomLeft()
	{
		return InputHelper.getBottomRight().clone().addX(-InputHelper.getScreenWidth()).flush();
	}

	public static Pos2D getCenterLeft()
	{
		return InputHelper.getCenter().clone().addX(-InputHelper.getScreenWidth() / 2).flush();
	}

	public static Pos2D getCenterRight()
	{
		return InputHelper.getCenterLeft().clone().addX(InputHelper.getScreenWidth()).flush();
	}

	public static Pos2D getTopCenter()
	{
		return InputHelper.getCenter().clone().addY(-InputHelper.getScreenHeight() / 2).flush();
	}

	public static Pos2D getTopLeft()
	{
		return InputHelper.getTopCenter().clone().addX(-InputHelper.getScreenWidth() / 2).flush();
	}

	public static Pos2D getTopRight()
	{
		return InputHelper.getTopLeft().clone().addX(InputHelper.getScreenWidth()).flush();
	}

}
