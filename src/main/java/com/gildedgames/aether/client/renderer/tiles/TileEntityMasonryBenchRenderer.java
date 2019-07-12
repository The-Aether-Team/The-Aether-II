package com.gildedgames.aether.client.renderer.tiles;

import com.gildedgames.aether.client.models.entities.tile.ModelMasonryBench;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.tiles.TileEntityMasonryBench;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityMasonryBenchRenderer extends TileEntitySpecialRenderer<TileEntityMasonryBench>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/tile_entities/masonry_bench.png");

	private final ModelMasonryBench model = new ModelMasonryBench();

	@Override
	public void render(
			final TileEntityMasonryBench te, final double x, final double y, final double z, final float partialTicks, final int destroyStage,
			final float alpha)
	{
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();

		GlStateManager.translatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.rotatef(180f, 1f, 0f, 1f);

		if (te != null)
		{
			switch (te.getFacing())
			{
				case NORTH:
					GlStateManager.rotatef(270.0f, 0.0f, 1.0f, 0.0f);
					break;
				case WEST:
					GlStateManager.rotatef(180.0f, 0.0f, 1.0f, 0.0f);
					break;
				case SOUTH:
					GlStateManager.rotatef(90.0f, 0.0f, 1.0f, 0.0f);
					break;
				case EAST:
					GlStateManager.rotatef(0.0f, 0.0f, 1.0f, 0.0f);
					break;
			}
		}
		else
		{
			GlStateManager.rotatef(180.0f, 0.0f, 1.0f, 0.0f);
		}

		if (destroyStage >= 0)
		{
			this.bindTexture(DESTROY_STAGES[destroyStage]);

			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scalef(4.0F, 4.0F, 1.0F);
			GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		}
		else
		{
			this.bindTexture(TEXTURE);
		}

		this.model.render(0.0625F);

		GlStateManager.disableRescaleNormal();

		GlStateManager.popMatrix();

		if (destroyStage >= 0)
		{
			GlStateManager.matrixMode(GL11.GL_TEXTURE);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		}
	}
}
