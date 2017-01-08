package com.gildedgames.aether.client.ui.minecraft.viewing;

import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.data.rect.RectHolder;
import com.gildedgames.aether.client.ui.input.InputProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MinecraftInputProvider implements InputProvider
{

	protected Minecraft mc;

	protected float screenWidth, screenHeight, scaleFactor, xOffset, yOffset;

	protected ScaledResolution resolution;

	public MinecraftInputProvider(Minecraft mc)
	{
		this.mc = mc;
		this.refreshResolution();
	}

	public void setScreen(float screenWidth, float screenHeight)
	{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	public void setScaleFactor(float scaleFactor)
	{
		this.scaleFactor = scaleFactor;
	}

	@Override
	public void refreshResolution()
	{
		this.resolution = new ScaledResolution(this.mc);
	}

	@Override
	public float getScreenWidth()
	{
		this.refreshResolution();

		return this.resolution.getScaledWidth();
	}

	@Override
	public float getScreenHeight()
	{
		this.refreshResolution();

		return this.resolution.getScaledHeight();
	}

	@Override
	public float getMouseX()
	{
		return (Mouse.getX() * this.getScreenWidth() / this.mc.displayWidth) - this.xOffset;
	}

	@Override
	public float getMouseY()
	{
		return (this.getScreenHeight() - Mouse.getY() * this.getScreenHeight() / this.mc.displayHeight - 1) - this.yOffset;
	}

	@Override
	public boolean isHovered(Rect dim)
	{
		if (dim == null)
		{
			return false;
		}

		return this.getMouseX() >= dim.x() && this.getMouseY() >= dim.y() && this.getMouseX() < dim.x() + dim.width()
				&& this.getMouseY() < dim.y() + dim.height();
	}

	@Override
	public float getScaleFactor()
	{
		return this.resolution.getScaleFactor();
	}

	@Override
	public InputProvider copyWithMouseXOffset(float xOffset)
	{
		MinecraftInputProvider input = (MinecraftInputProvider) this.clone();

		input.xOffset = xOffset;

		return input;
	}

	@Override
	public InputProvider copyWithMouseYOffset(float yOffset)
	{
		MinecraftInputProvider input = (MinecraftInputProvider) this.clone();

		input.yOffset = yOffset;

		return input;
	}

	@Override
	public InputProvider clone()
	{
		MinecraftInputProvider input = new MinecraftInputProvider(this.mc);

		input.screenWidth = this.screenWidth;
		input.screenHeight = this.screenHeight;
		input.scaleFactor = this.scaleFactor;
		input.xOffset = this.xOffset;
		input.yOffset = this.yOffset;

		return input;
	}

	@Override
	public boolean isHovered(RectHolder holder)
	{
		if (holder == null)
		{
			return false;
		}

		return this.isHovered(holder.dim());
	}

	@Override
	public void setMouseX(float x)
	{
		Mouse.setCursorPosition(MathHelper.floor_float((x / this.getScreenWidth() * this.mc.displayWidth)), Mouse.getY());
	}

	@Override
	public void setMouseY(float y)
	{
		Mouse.setCursorPosition(Mouse.getX(),
				Display.getHeight() - MathHelper.floor_float((y / this.getScreenHeight() * this.mc.displayHeight + 1)));
	}

	@Override
	public void setMouse(float x, float y)
	{
		this.setMouseX(x);
		this.setMouseY(y);
	}

}
