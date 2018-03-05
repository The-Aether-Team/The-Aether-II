package com.gildedgames.aether.client.gui;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class GuiUtils
{

	public static void drawGradientRect(final int left, final int top, final int right, final int bottom, final int startColor, final int endColor)
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
		bufferbuilder.pos((double) right, (double) top, 0).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos((double) left, (double) top, 0).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos((double) left, (double) bottom, 0).color(f5, f6, f7, f4).endVertex();
		bufferbuilder.pos((double) right, (double) bottom, 0).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

}
