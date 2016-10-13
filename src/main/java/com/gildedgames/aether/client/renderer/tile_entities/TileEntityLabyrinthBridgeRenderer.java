package com.gildedgames.aether.client.renderer.tile_entities;

import com.gildedgames.aether.client.models.entities.ModelBlock;
import com.gildedgames.aether.common.tile_entities.TileEntityLabyrinthBridge;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class TileEntityLabyrinthBridgeRenderer extends TileEntitySpecialRenderer<TileEntityLabyrinthBridge>
{

	private ModelBlock block = new ModelBlock();

	@Override
	public void renderTileEntityAt(TileEntityLabyrinthBridge te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		if (te.getDamage() < 0)
		{
			return;
		}

		this.bindTexture(TileEntitySpecialRenderer.DESTROY_STAGES[te.getDamage()]);

		GlStateManager.matrixMode(5890);
		GlStateManager.pushMatrix();
		GlStateManager.scale(2.0F, 2.0F, 1.0F);
		GlStateManager.matrixMode(5888);

		GlStateManager.pushMatrix();
		GlStateManager.disableCull();

		GlStateManager.translate(x + 0.5F, y + 0.25F, z + 0.5F);
		GlStateManager.scale(1.001F, 1.001F, 1.001F);

		GlStateManager.enableRescaleNormal();

		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.enableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
		GlStateManager.alphaFunc(516, 0.1F);
		GlStateManager.enableAlpha();

		this.block.renderAll(0.0625F);

		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();

		GlStateManager.popMatrix();

		GlStateManager.matrixMode(5890);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(5888);

	}

}
