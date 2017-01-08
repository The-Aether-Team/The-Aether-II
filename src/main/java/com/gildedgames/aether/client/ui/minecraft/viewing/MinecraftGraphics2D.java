package com.gildedgames.aether.client.ui.minecraft.viewing;

import com.gildedgames.aether.client.ui.data.AssetLocation;
import com.gildedgames.aether.client.ui.data.DrawingData;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.graphics.Graphics2D;
import com.gildedgames.aether.client.ui.graphics.Sprite;
import com.gildedgames.aether.client.ui.graphics.UVBehavior;
import com.gildedgames.aether.client.ui.util.rect.RectCollection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class MinecraftGraphics2D implements Graphics2D
{

	protected Minecraft minecraft;

	protected Gui gui = new Gui();

	protected FontRenderer fontRenderer;

	protected float zLevel;

	public MinecraftGraphics2D(Minecraft minecraft)
	{
		this.minecraft = minecraft;
		this.fontRenderer = this.minecraft.fontRendererObj;
	}

	private ResourceLocation convert(AssetLocation resource)
	{
		return new ResourceLocation(resource.getDomain(), resource.getPath());
	}

	private void draw(Rect dim, DrawingData data, DrawInner inner)
	{
		GlStateManager.pushMatrix();

		double currentX = dim.x();
		double currentY = dim.y();

		double x = currentX;//currentX + (currentX - prevX) * partialTicks;
		double y = currentY;//currentY + (currentY - prevY) * partialTicks;

		/** TO-DO: Figure out why the prevPos and currentPos are the same wtf?! :D **/

		GlStateManager.translate(x, y, 0);

		GlStateManager.scale(dim.scale(), dim.scale(), 0);

		GlStateManager.translate((dim.width() / 2) + dim.originX(), (dim.height() / 2) + dim.originY(), -2000);

		GlStateManager.rotate(dim.degrees(), 0.0F, 0.0F, 1.0F);

		GlStateManager.translate(-(dim.width() / 2) - dim.originX(), -(dim.height() / 2) - dim.originY(), -2000);

		GlStateManager.enableBlend();

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GlStateManager.color(1.0F, 1.0F, 1.0F, data.getAlpha());

		GL11.glEnable(GL11.GL_ALPHA_TEST);

		GlStateManager.enableAlpha();

		inner.draw();

		GL11.glDisable(GL11.GL_ALPHA_TEST);

		GlStateManager.disableAlpha();

		GlStateManager.popMatrix();
	}

	@Override
	public void drawSprite(Sprite sprite, Rect dim, DrawingData data)
	{
		this.draw(dim, data, new DrawSprite(this, sprite, data, dim));
	}

	@Override
	public void drawText(String text, Rect dim, DrawingData data)
	{
		this.draw(dim, data, new DrawText(this.fontRenderer, text, dim, data));
	}

	@Override
	public void drawLine(int startX, int startY, int endX, int endY, DrawingData drawingData)
	{

	}

	@Override
	public void drawRectangle(Rect dim, DrawingData data)
	{
		this.draw(dim, data, new DrawRectangle(this, dim, data));
	}

	@Override
	public void drawGradientRectangle(Rect dim, DrawingData startColor, DrawingData endColor)
	{
		this.draw(dim, startColor, new DrawGradientRectangle(this, dim, startColor, endColor));
	}

	protected void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor)
	{
		float f = (startColor >> 24 & 255) / 255.0F;
		float f1 = (startColor >> 16 & 255) / 255.0F;
		float f2 = (startColor >> 8 & 255) / 255.0F;
		float f3 = (startColor & 255) / 255.0F;
		float f4 = (endColor >> 24 & 255) / 255.0F;
		float f5 = (endColor >> 16 & 255) / 255.0F;
		float f6 = (endColor >> 8 & 255) / 255.0F;
		float f7 = (endColor & 255) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer worldrenderer = tessellator.getBuffer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(right, top, this.zLevel).color(f1, f2, f3, f).endVertex();
		worldrenderer.pos(left, top, this.zLevel).color(f1, f2, f3, f).endVertex();
		worldrenderer.pos(left, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
		worldrenderer.pos(right, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	private void drawModalRectWithCustomSizedTexture(double x, double y, double u, double v, double width, double height,
			double textureWidth, double textureHeight)
	{
		double f4 = 1.0D / textureWidth;
		double f5 = 1.0D / textureHeight;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer worldrenderer = tessellator.getBuffer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		worldrenderer.pos(x, y + height, 0.0D).tex(u * f4, (v + (float) height) * f5).endVertex();
		worldrenderer.pos(x + width, y + height, 0.0D).tex((u + (float) width) * f4, (v + (float) height) * f5).endVertex();
		worldrenderer.pos(x + width, y, 0.0D).tex((u + (float) width) * f4, v * f5).endVertex();
		worldrenderer.pos(x, y, 0.0D).tex(u * f4, v * f5).endVertex();
		tessellator.draw();
	}

	private void drawRect(double left, double top, double right, double bottom, int color)
	{
		double j1;

		if (left < right)
		{
			j1 = left;
			left = right;
			right = j1;
		}

		if (top < bottom)
		{
			j1 = top;
			top = bottom;
			bottom = j1;
		}

		float f3 = (color >> 24 & 255) / 255.0F;
		float f = (color >> 16 & 255) / 255.0F;
		float f1 = (color >> 8 & 255) / 255.0F;
		float f2 = (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer worldrenderer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(f, f1, f2, f3);
		worldrenderer.begin(7, DefaultVertexFormats.POSITION);
		worldrenderer.pos(left, bottom, 0.0D);
		worldrenderer.pos(right, bottom, 0.0D);
		worldrenderer.pos(right, top, 0.0D);
		worldrenderer.pos(left, top, 0.0D);
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	private interface DrawInner
	{
		void draw();
	}

	private static class DrawSprite implements DrawInner
	{

		private final MinecraftGraphics2D graphics;

		private final Sprite sprite;

		private final Rect dim;

		private final DrawingData data;

		private final RectCollection collection;

		public DrawSprite(MinecraftGraphics2D graphics, Sprite sprite, DrawingData data, Rect dim)
		{
			this.graphics = graphics;
			this.sprite = sprite;
			this.dim = dim;
			this.data = data;
			this.collection = RectCollection.flush(this.dim);
		}

		@Override
		public void draw()
		{
			this.graphics.minecraft.renderEngine.bindTexture(this.graphics.convert(this.sprite.getAsset()));

			if (this.sprite.getBehavior().shouldRecalculateUVs(this.sprite, this.collection))
			{
				this.sprite.getBehavior().recalculateUVs(this.sprite, this.collection);
			}

			List<UVBehavior.UVDimPair> uvDimPairs = this.sprite.getBehavior().getDrawnUVsFor(this.sprite, this.collection);

			for (UVBehavior.UVDimPair uvDimPair : uvDimPairs)
			{
				Sprite.UV uv = uvDimPair.getUV();
				Rect dim = uvDimPair.getRect();

				this.graphics.drawModalRectWithCustomSizedTexture(dim.x(), dim.y(), uv.minU(), uv.minV(), uv.width(), uv.height(), this.sprite.getAssetWidth(), this.sprite.getAssetHeight());
			}
		}

	}

	private static class DrawText implements DrawInner
	{

		private final FontRenderer fontRenderer;

		private final String text;

		private final Rect dim;

		private final DrawingData data;

		public DrawText(FontRenderer fontRenderer, String text, Rect dim, DrawingData data)
		{
			this.fontRenderer = fontRenderer;
			this.text = text;
			this.dim = dim;
			this.data = data;
		}

		@Override
		public void draw()
		{
			this.fontRenderer.drawStringWithShadow(this.text, 0, 0, this.data.getColor().getRGB());
		}

	}

	private static class DrawRectangle implements DrawInner
	{

		private final MinecraftGraphics2D graphics;

		private final Rect dim;

		private final DrawingData data;

		public DrawRectangle(MinecraftGraphics2D graphics, Rect dim, DrawingData data)
		{
			this.graphics = graphics;
			this.dim = dim;
			this.data = data;
		}

		@Override
		public void draw()
		{
			this.graphics.drawRect(0, 0, this.dim.width(), this.dim.height(), this.data.getColor().getRGB());
		}

	}

	private static class DrawGradientRectangle implements DrawInner
	{

		private final MinecraftGraphics2D graphics;

		private final Rect dim;

		private final DrawingData startColor, endColor;

		public DrawGradientRectangle(MinecraftGraphics2D graphics, Rect dim, DrawingData startColor, DrawingData endColor)
		{
			this.graphics = graphics;
			this.dim = dim;
			this.startColor = startColor;
			this.endColor = endColor;
		}

		@Override
		public void draw()
		{
			this.graphics.drawGradientRect(0, 0, this.dim.width(), this.dim.height(), this.startColor.getColor().getRGB(), this.endColor.getColor().getRGB());
		}

	}

}
