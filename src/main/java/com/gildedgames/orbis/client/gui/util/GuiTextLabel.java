package com.gildedgames.orbis.client.gui.util;

import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Rect;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.text.ITextComponent;

public class GuiTextLabel extends GuiAdvanced
{
	private final GuiText text;

	public GuiTextLabel(final Rect rect, final ITextComponent component)
	{
		super(rect);

		this.text = new GuiText(Dim2D.flush(), component);
	}

	@Override
	public void init()
	{
		this.addChild(this.text);
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		GlStateManager.pushMatrix();

		this.drawTextBackground(this.dim().x(), this.dim().y(), this.dim().width(), this.dim().height());

		GlStateManager.popMatrix();

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private void drawTextBackground(final float cornerX, final float cornerY, final float width, final float height)
	{
		final int l1 = -267386864;
		this.drawGradientRect(cornerX - 3, cornerY - 4, cornerX + width + 3, cornerY - 3, l1, l1);
		this.drawGradientRect(cornerX - 3, cornerY + height + 3, cornerX + width + 3, cornerY + height + 4, l1, l1);
		this.drawGradientRect(cornerX - 3, cornerY - 3, cornerX + width + 3, cornerY + height + 3, l1, l1);
		this.drawGradientRect(cornerX - 4, cornerY - 3, cornerX - 3, cornerY + height + 3, l1, l1);
		this.drawGradientRect(cornerX + width + 3, cornerY - 3, cornerX + width + 4, cornerY + height + 3, l1, l1);
		final int i2 = 1347420415;
		final int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
		this.drawGradientRect(cornerX - 3, cornerY - 3 + 1, cornerX - 3 + 1, cornerY + height + 3 - 1, i2, j2);
		this.drawGradientRect(cornerX + width + 2, cornerY - 3 + 1, cornerX + width + 3, cornerY + height + 3 - 1, i2, j2);
		this.drawGradientRect(cornerX - 3, cornerY - 3, cornerX + width + 3, cornerY - 3 + 1, i2, i2);
		this.drawGradientRect(cornerX - 3, cornerY + height + 2, cornerX + width + 3, cornerY + height + 3, j2, j2);
	}

	protected void drawGradientRect(final float left, final float top, final float right, final float bottom, final int startColor, final int endColor)
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
		final VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		vertexbuffer.pos((double) right, (double) top, (double) this.zLevel).color(f1, f2, f3, f).endVertex();
		vertexbuffer.pos((double) left, (double) top, (double) this.zLevel).color(f1, f2, f3, f).endVertex();
		vertexbuffer.pos((double) left, (double) bottom, (double) this.zLevel).color(f5, f6, f7, f4).endVertex();
		vertexbuffer.pos((double) right, (double) bottom, (double) this.zLevel).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}
}
