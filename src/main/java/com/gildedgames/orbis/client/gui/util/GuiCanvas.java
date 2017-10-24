package com.gildedgames.orbis.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * Used to bring gui children to the forefront of the interface.
 * Add this to your GuiFrame then add children to it.
 */
public class GuiCanvas extends GuiFrame
{

	private boolean disableDepth = true;

	private float depth;

	public GuiCanvas()
	{

	}

	public static GuiCanvas fetch()
	{
		return GuiCanvas.fetch(0.0F);
	}

	public static GuiCanvas fetch(final float depth)
	{
		if (!(Minecraft.getMinecraft().currentScreen instanceof GuiFrame))
		{
			throw new RuntimeException("Tried to fetch a GuiCanvas while a GuiFrame was not the current screen");
		}

		final GuiFrame currentFrame = (GuiFrame) Minecraft.getMinecraft().currentScreen;

		GuiCanvas canvas = null;

		for (final IGuiFrame child : currentFrame.seekAllContent())
		{
			if (child instanceof GuiCanvas)
			{
				canvas = (GuiCanvas) child;
				break;
			}
		}

		if (canvas == null)
		{
			canvas = new GuiCanvas();

			currentFrame.addChild(canvas);
		}

		if (depth != 0.0F)
		{
			canvas.disableDepth = false;
			canvas.depth = depth;
		}

		return canvas;
	}

	public void setDisableDepth(final boolean flag)
	{
		this.disableDepth = flag;
	}

	public void setDepth(final float depth)
	{
		this.depth = depth;
	}

	@Override
	public void init()
	{

	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		GlStateManager.pushMatrix();

		if (this.disableDepth)
		{
			GL11.glDisable(GL11.GL_DEPTH_TEST);
		}
		else
		{
			GlStateManager.translate(0, 0, this.depth);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);

		if (this.disableDepth)
		{
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
		else
		{
			GlStateManager.translate(0, 0, this.depth);
		}

		GlStateManager.popMatrix();
	}

}
