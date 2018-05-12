package com.gildedgames.aether.client.renderer.tile_entities;

import com.gildedgames.aether.client.models.entities.tile.ModelTeleporter;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.tiles.TileEntityTeleporter;
import com.gildedgames.orbis_api.util.OpenGLHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityTeleporterRenderer extends TileEntitySpecialRenderer<TileEntityTeleporter>
{
	private final ModelTeleporter model = new ModelTeleporter();

	private final ResourceLocation texture = AetherCore.getResource("textures/tile_entities/teleporter/pedestal.png");

	@Override
	public void render(final TileEntityTeleporter teleporter, final double x, final double y, final double z, final float partialTicks, final int destroyStage,
			final float alpha)
	{
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();

		GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.rotate(180f, 1f, 0f, 1f);

		this.bindTexture(this.texture);

		final boolean inGuiContext = OpenGLHelper.isInGuiContext();

		if (teleporter != null)
		{
			switch (teleporter.getFacing())
			{
				case NORTH:
					GlStateManager.rotate(270.0f, 0.0f, 1.0f, 0.0f);
					break;
				case WEST:
					GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
					break;
				case SOUTH:
					GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
					break;
				case EAST:
					GlStateManager.rotate(0.0f, 0.0f, 1.0f, 0.0f);
					break;
			}

			this.model.render(0.0625F, teleporter.getBuildTime());
		}
		else
		{
			this.model.render(0.0625F, 0);
		}

		GlStateManager.disableRescaleNormal();

		GlStateManager.popMatrix();
	}

}
