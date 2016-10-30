package com.gildedgames.aether.client.renderer.tile_entities;

import com.gildedgames.aether.client.models.entities.tile.ModelPresent;
import com.gildedgames.aether.common.items.blocks.ItemBlockPresent;
import com.gildedgames.aether.common.tiles.TileEntityPresent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class TileEntityPresentRenderer extends TileEntitySpecialRenderer<TileEntityPresent>
{

	public static final ResourceLocation[] boxTextures = new ResourceLocation[16];

	public static final ResourceLocation[] bowTextures = new ResourceLocation[16];

	public static final String[] colors = new String[] { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white" };

	static
	{
		for (int i = 0; i < 16; i++)
		{
			boxTextures[i] = new ResourceLocation("aether", "textures/tile_entities/present/present_box_" + colors[i] + ".png");
			bowTextures[i] = new ResourceLocation("aether", "textures/tile_entities/present/present_ribbon_" + colors[i] + ".png");
		}
	}

	private final ModelPresent model = new ModelPresent();

	private final Random random = new Random();

	@Override
	public void renderTileEntityAt(TileEntityPresent present, double x, double y, double z, float partialTicks, int destroyStage)
	{
		GlStateManager.pushMatrix();

		ItemBlockPresent.PresentData data = null;

		if (present != null)
		{
			data = present.getPresentData();
		}

		byte boxColor = data == null ? 15 : data.getDye().getBoxColor();
		byte bowColor = data == null ? 1 : data.getDye().getBowColor();

		// Sanitize dye colors to prevent rogue presents!
		boxColor = (boxColor >= 15 ? 15 : (boxColor < 0 ? 0 : boxColor));
		bowColor = (bowColor >= 15 ? 15 : (bowColor < 0 ? 0 : bowColor));

		GlStateManager.enableRescaleNormal();

		GlStateManager.translate((float) x + 0.5f, (float) y, (float) z + 0.5f);

		if (present != null)
		{
			this.random.setSeed((long) (present.getPos().getX() + present.getPos().getY() + present.getPos().getZ()) * 5L);

			float offset = this.random.nextFloat() * 0.1f;
			float scale = 1f + ((this.random.nextFloat() * 0.1f) - 0.1f);
			float rotate = this.random.nextFloat() * 180f;

			GlStateManager.translate(offset, 0, offset);
			GlStateManager.rotate(180f, 1f, 0f, 1f);
			GlStateManager.rotate(rotate, 0f, 1f, 0f);
			GlStateManager.scale(scale, scale, scale);
		}
		else
		{
			GlStateManager.rotate(180f, 1.0f, 0f, 0.4f);
			GlStateManager.scale(1.5F, 1.5F, 1.5F);
		}

		this.bindTexture(boxTextures[boxColor]);
		this.model.renderBox(0.0625F);

		this.bindTexture(bowTextures[bowColor]);
		this.model.renderBow(0.0625F);
		this.model.renderBox(0.0625F);

		GlStateManager.disableRescaleNormal();

		GlStateManager.popMatrix();
	}
}
