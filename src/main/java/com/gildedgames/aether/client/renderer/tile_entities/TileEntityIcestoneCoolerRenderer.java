package com.gildedgames.aether.client.renderer.tile_entities;

import com.gildedgames.aether.client.models.entities.tile.ModelIcestoneCooler;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.tiles.TileEntityIcestoneCooler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityIcestoneCoolerRenderer extends TileEntitySpecialRenderer<TileEntityIcestoneCooler>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/tile_entities/icestone_cooler.png");

	private ModelIcestoneCooler model = new ModelIcestoneCooler();

	@Override
	public void renderTileEntityAt(TileEntityIcestoneCooler te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();

		GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.rotate(180f, 1f, 0f, 1f);

		if (te != null)
		{
			switch (te.getFacing())
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
		}
		else
		{
			GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
		}

		if (destroyStage >= 0)
		{
			this.bindTexture(DESTROY_STAGES[destroyStage]);

			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scale(4.0F, 4.0F, 1.0F);
			GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
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
