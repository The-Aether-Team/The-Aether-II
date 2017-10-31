package com.gildedgames.orbis.client.renderers;

import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis_core.util.RegionHelp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class RenderUtil
{

	private final static Minecraft mc = Minecraft.getMinecraft();

	public static void renderTextAbove(final IRegion region, final String string, final double yOffset, final float partialTicks)
	{
		final double offsetPlayerX = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * partialTicks;
		final double offsetPlayerY = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * partialTicks;
		final double offsetPlayerZ = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * partialTicks;

		final BlockPos center = RegionHelp.getBottomCenter(region);
		RenderUtil.renderLabel(string, -offsetPlayerX + center.getX(), -offsetPlayerY + region.getMax().getY() + yOffset, -offsetPlayerZ + center.getZ());
	}

	public static void renderDimensionsAbove(final IRegion region, final float partialTicks)
	{
		final String coordinates = String.format("%sx%sx%s", region.getWidth(), region.getHeight(), region.getLength());

		renderTextAbove(region, coordinates, 1.5D, partialTicks);
	}

	public static void renderLabel(final String name, final double x, final double y, final double z)
	{
		final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		final double distance = Minecraft.getMinecraft().player.getDistanceSq(x, y, z);

		if (/*distance < ??*/true) //skip distance? broken?
		{
			final FontRenderer fontrenderer = renderManager.getFontRenderer();
			if (fontrenderer == null)
			{
				return;
			}
			final float f = 1.6F;
			final float scale = 0.016666668F * f;

			GlStateManager.pushMatrix();

			GlStateManager.translate(x, y, z);

			GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);

			GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

			GlStateManager.scale(-scale, -scale, scale);

			GlStateManager.disableLighting();
			GlStateManager.depthMask(false);
			GlStateManager.disableDepth();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			final VertexBuffer buffer = Tessellator.getInstance().getBuffer();

			GlStateManager.disableTexture2D();

			buffer.begin(7, DefaultVertexFormats.POSITION);

			final int j = fontrenderer.getStringWidth(name) / 2;

			buffer.color(0.0F, 0.0F, 0.0F, 0.25F);

			buffer.pos(-j - 1, -1, 0.0D).endVertex();
			buffer.pos(-j - 1, 8, 0.0D).endVertex();
			buffer.pos(j + 1, 8, 0.0D).endVertex();
			buffer.pos(j + 1, -1, 0.0D).endVertex();

			Tessellator.getInstance().draw();

			GlStateManager.enableTexture2D();

			fontrenderer.drawString(name, -fontrenderer.getStringWidth(name) / 2, 0, 553648127);
			fontrenderer.drawString(name, -fontrenderer.getStringWidth(name) / 2, 0, -1);

			GlStateManager.enableLighting();

			GlStateManager.enableDepth();
			GlStateManager.depthMask(true);

			GlStateManager.disableBlend();
			GlStateManager.popMatrix();

		}
	}

}
