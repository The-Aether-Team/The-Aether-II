package com.gildedgames.aether.client.ui.minecraft.util.wrappers;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.input.InputProvider;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class MinecraftTextBackground extends GuiFrame
{

	public MinecraftTextBackground(Rect rect)
	{
		super(rect);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		GL11.glPushMatrix();
		this.drawTextBackground(this.dim().x(), this.dim().y(), this.dim().width(), this.dim().height());
		GL11.glPopMatrix();
	}

	private void drawTextBackground(float cornerX, float cornerY, float width, float height)
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

	public void drawGradientRect(float minX, float minY, float maxX, float maxY, int par5, int par6)
	{
		int zLevel = 0;

		GL11.glPushMatrix();
		final float f = (par5 >> 24 & 255) / 255.0F;
		final float f1 = (par5 >> 16 & 255) / 255.0F;
		final float f2 = (par5 >> 8 & 255) / 255.0F;
		final float f3 = (par5 & 255) / 255.0F;
		final float f4 = (par6 >> 24 & 255) / 255.0F;
		final float f5 = (par6 >> 16 & 255) / 255.0F;
		final float f6 = (par6 >> 8 & 255) / 255.0F;
		final float f7 = (par6 & 255) / 255.0F;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		//GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		final VertexBuffer tessellator = Tessellator.getInstance().getBuffer();
		tessellator.begin(7, DefaultVertexFormats.POSITION_COLOR);
		tessellator.pos(maxX, minY, zLevel).color(f1, f2, f3, f).endVertex();
		tessellator.pos(minX, minY, zLevel).color(f1, f2, f3, f).endVertex();
		tessellator.pos(minX, maxY, zLevel).color(f5, f6, f7, f4).endVertex();
		tessellator.pos(maxX, maxY, zLevel).color(f5, f6, f7, f4).endVertex();
		Tessellator.getInstance().draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		//GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}

}
