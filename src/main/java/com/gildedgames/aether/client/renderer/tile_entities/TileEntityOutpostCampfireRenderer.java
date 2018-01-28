package com.gildedgames.aether.client.renderer.tile_entities;

import com.gildedgames.aether.client.models.entities.tile.ModelOutpostCampfire;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.tiles.TileEntityOutpostCampfire;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityOutpostCampfireRenderer extends TileEntitySpecialRenderer<TileEntityOutpostCampfire>
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/tile_entities/outpost_campfire.png");

	private static final ResourceLocation TEXTURE_LIT = AetherCore.getResource("textures/tile_entities/outpost_campfire_on.png");

	private final ModelOutpostCampfire model = new ModelOutpostCampfire();

	@Override
	public void render(final TileEntityOutpostCampfire te, final double x, final double y, final double z, final float partialTicks, final int destroyStage,
			final float alpha)
	{
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();

		GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.rotate(180f, 1f, 0f, 1f);

		// Rendering as item... ?
		if (te == null)
		{
			GlStateManager.scale(0.6f, 0.6f, 0.6f);
			GlStateManager.translate(-1.0f, 0.2f, 0.0f);
		}

		this.bindTexture(TEXTURE);

		this.model.render(0.0625F);

		GlStateManager.popMatrix();
	}
}
