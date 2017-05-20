package com.gildedgames.aether.client.renderer.tile_entities;

import com.gildedgames.aether.common.entities.tiles.builder.TileEntityStructureBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;

public class TileEntityStructureBuilderRenderer extends TileEntitySpecialRenderer<TileEntityStructureBuilder>
{
	@Override
	public void renderTileEntityAt(TileEntityStructureBuilder te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);

		BlockPos fromBB = te.getStructureData().offset;
		BlockPos toBB = fromBB.add(te.getStructureData().size).add(1, 1, 1);

		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer buffer = tessellator.getBuffer();

		GlStateManager.disableFog();
		GlStateManager.disableLighting();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
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

	private void renderBox(Tessellator tessellator, VertexBuffer buffer, double x1, double y1, double z1, double x2, double y2, double z2, int color1, int color2, int color3)
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
	public boolean isGlobalRenderer(TileEntityStructureBuilder te)
	{
		return true;
	}

}
