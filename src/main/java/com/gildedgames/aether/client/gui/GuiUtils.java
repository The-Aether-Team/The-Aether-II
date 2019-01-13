package com.gildedgames.aether.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GuiUtils
{
	private static final Gui DUMMY_GUI = new Gui();

	public static void drawString(FontRenderer font, String line, int x, int y, int color)
	{
		DUMMY_GUI.drawString(font, line, x, y, color);
	}

	public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor)
	{
		final float f = (float) (startColor >> 24 & 255) / 255.0F;
		final float f1 = (float) (startColor >> 16 & 255) / 255.0F;
		final float f2 = (float) (startColor >> 8 & 255) / 255.0F;
		final float f3 = (float) (startColor & 255) / 255.0F;
		final float f4 = (float) (endColor >> 24 & 255) / 255.0F;
		final float f5 = (float) (endColor >> 16 & 255) / 255.0F;
		final float f6 = (float) (endColor >> 8 & 255) / 255.0F;
		final float f7 = (float) (endColor & 255) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager
				.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
						GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(right, top, 0).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos(left, top, 0).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos(left, bottom, 0).color(f5, f6, f7, f4).endVertex();
		bufferbuilder.pos(right, bottom, 0).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	public static void drawHoveringText(List<String> textLines, int x, int y, FontRenderer font)
	{
		GuiScreen gui = Minecraft.getMinecraft().currentScreen;

		if (gui == null)
		{
			return;
		}

		net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(textLines, x, y, gui.width, gui.height, -1, font);
	}

	public static void drawDescriptionBackground(float x, float y, float width, float height)
	{
		GL11.glPushMatrix();
		GuiUtils.drawTextBackground(x, y, width, height);
		GL11.glPopMatrix();
	}

	private static void drawTextBackground(float cornerX, float cornerY, float width, float height)
	{
		final int l1 = -267386864;
		GuiUtils.drawGradientRect(cornerX - 3, cornerY - 4, cornerX + width + 3, cornerY - 3, l1, l1);
		GuiUtils.drawGradientRect(cornerX - 3, cornerY + height + 3, cornerX + width + 3, cornerY + height + 4, l1, l1);
		GuiUtils.drawGradientRect(cornerX - 3, cornerY - 3, cornerX + width + 3, cornerY + height + 3, l1, l1);
		GuiUtils.drawGradientRect(cornerX - 4, cornerY - 3, cornerX - 3, cornerY + height + 3, l1, l1);
		GuiUtils.drawGradientRect(cornerX + width + 3, cornerY - 3, cornerX + width + 4, cornerY + height + 3, l1, l1);
		final int i2 = 1347420415;
		final int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
		GuiUtils.drawGradientRect(cornerX - 3, cornerY - 3 + 1, cornerX - 3 + 1, cornerY + height + 3 - 1, i2, j2);
		GuiUtils.drawGradientRect(cornerX + width + 2, cornerY - 3 + 1, cornerX + width + 3, cornerY + height + 3 - 1, i2, j2);
		GuiUtils.drawGradientRect(cornerX - 3, cornerY - 3, cornerX + width + 3, cornerY - 3 + 1, i2, i2);
		GuiUtils.drawGradientRect(cornerX - 3, cornerY + height + 2, cornerX + width + 3, cornerY + height + 3, j2, j2);
	}

}
