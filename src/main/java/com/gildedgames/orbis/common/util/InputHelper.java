package com.gildedgames.orbis.common.util;

import com.gildedgames.orbis.client.util.rect.Rect;
import com.gildedgames.orbis.client.util.rect.RectHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

public class InputHelper
{

	private static final Minecraft mc = Minecraft.getMinecraft();

	private static float screenWidth, screenHeight, scaleFactor, xOffset, yOffset;

	private static ScaledResolution resolution;

	public static float getMouseX()
	{
		return (Mouse.getX() * getScreenWidth() / mc.displayWidth);
	}

	public static float getMouseY()
	{
		return (getScreenHeight() - Mouse.getY() * getScreenHeight() / mc.displayHeight - 1);
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

	public static void setScreen(final float screenWidth, final float screenHeight)
	{
		InputHelper.screenWidth = screenWidth;
		InputHelper.screenHeight = screenHeight;
	}

	public static void setScaleFactor(final float scaleFactor)
	{
		InputHelper.scaleFactor = scaleFactor;
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

}
