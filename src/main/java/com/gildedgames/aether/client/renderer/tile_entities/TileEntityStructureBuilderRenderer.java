package com.gildedgames.aether.client.renderer.tile_entities;

import com.gildedgames.aether.common.entities.tiles.builder.TileEntityStructureBuilder;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;

public class TileEntityStructureBuilderRenderer extends TileEntitySpecialRenderer<TileEntityStructureBuilder>
{
	@Override
	public void render(final TileEntityStructureBuilder te, final double x, final double y, final double z, final float partialTicks,
			final int destroyStage, final float alpha)
	{
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);

		final BlockPos fromBB = te.getStructureData().offset;
		final BlockPos toBB = fromBB.add(te.getStructureData().size).add(1, 1, 1);

		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder buffer = tessellator.getBuffer();

		GlStateManager.disableFog();
		GlStateManager.disableLighting();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager
				.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
						GlStateManager.DestFactor.ZERO);
		GlStateManager.glLineWidth(2.0F);

		this.setLightmapDisabled(true);

		this.renderBox(tessellator, buffer, x + fromBB.getX() + 0.98, y + fromBB.getY() + 0.98, z + fromBB.getZ() + 0.98,
				x + toBB.getX() + 0.02, y + toBB.getY() + 0.02, z + toBB.getZ() + 0.02,
				255, 223, 127);

		this.setLightmapDisabled(false);

		GlStateManager.glLineWidth(1.0F);
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.enableDepth();
		GlStateManager.depthMask(true);
		GlStateManager.enableFog();
	}

	private void renderBox(
			final Tessellator tessellator, final BufferBuilder buffer, final double x1, final double y1, final double z1, final double x2, final double y2,
			final double z2, final int color1, final int color2, final int color3)
	{
		buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		buffer.pos(x1, y1, z1).color(color2, color2, color2, 0.0F).endVertex();
		buffer.pos(x1, y1, z1).color(color2, color2, color2, color1).endVertex();
		buffer.pos(x2, y1, z1).color(color2, color3, color3, color1).endVertex();
		buffer.pos(x2, y1, z2).color(color2, color2, color2, color1).endVertex();
		buffer.pos(x1, y1, z2).color(color2, color2, color2, color1).endVertex();
		buffer.pos(x1, y1, z1).color(color3, color3, color2, color1).endVertex();
		buffer.pos(x1, y2, z1).color(color3, color2, color3, color1).endVertex();
		buffer.pos(x2, y2, z1).color(color2, color2, color2, color1).endVertex();
		buffer.pos(x2, y2, z2).color(color2, color2, color2, color1).endVertex();
		buffer.pos(x1, y2, z2).color(color2, color2, color2, color1).endVertex();
		buffer.pos(x1, y2, z1).color(color2, color2, color2, color1).endVertex();
		buffer.pos(x1, y2, z2).color(color2, color2, color2, color1).endVertex();
		buffer.pos(x1, y1, z2).color(color2, color2, color2, color1).endVertex();
		buffer.pos(x2, y1, z2).color(color2, color2, color2, color1).endVertex();
		buffer.pos(x2, y2, z2).color(color2, color2, color2, color1).endVertex();
		buffer.pos(x2, y2, z1).color(color2, color2, color2, color1).endVertex();
		buffer.pos(x2, y1, z1).color(color2, color2, color2, color1).endVertex();
		buffer.pos(x2, y1, z1).color(color2, color2, color2, 0.0F).endVertex();

		tessellator.draw();
	}

	@Override
	public boolean isGlobalRenderer(final TileEntityStructureBuilder te)
	{
		return true;
	}

}
