package com.gildedgames.aether.client.renderer.tiles;

import com.gildedgames.aether.client.models.entities.tile.ModelPresent;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.tiles.TileEntityPresent;
import com.gildedgames.aether.common.items.blocks.ItemBlockPresent;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class TileEntityPresentRenderer extends TileEntityRenderer<TileEntityPresent>
{

	public static final ResourceLocation[] boxTextures = new ResourceLocation[16];

	public static final ResourceLocation[] bowTextures = new ResourceLocation[16];

	public static final String[] colors = new String[] { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray",
			"pink", "lime", "yellow", "light_blue", "magenta", "orange", "white" };

	static
	{
		for (int i = 0; i < 16; i++)
		{
			boxTextures[i] = AetherCore.getResource("textures/tile_entities/present/present_box_" + colors[i] + ".png");
			bowTextures[i] = AetherCore.getResource("textures/tile_entities/present/present_ribbon_" + colors[i] + ".png");
		}
	}

	private final ModelPresent model = new ModelPresent();

	private final Random random = new Random();

	@Override
	public void render(final TileEntityPresent present, final double x, final double y, final double z, final float partialTicks, final int destroyStage)
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

		GlStateManager.translatef((float) x + 0.5f, (float) y, (float) z + 0.5f);

		if (present != null)
		{
			this.random.setSeed((long) (present.getPos().getX() + present.getPos().getY() + present.getPos().getZ()) * 5L);

			final float offset = this.random.nextFloat() * 0.1f;
			final float scale = 1f + ((this.random.nextFloat() * 0.1f) - 0.1f);
			final float rotate = this.random.nextFloat() * 180f;

			GlStateManager.translatef(offset, 0, offset);
			GlStateManager.rotatef(180f, 1f, 0f, 1f);
			GlStateManager.rotatef(rotate, 0f, 1f, 0f);
			GlStateManager.scalef(scale, scale, scale);
		}
		else
		{
			GlStateManager.rotatef(180f, 1.0f, 0f, 0.4f);
			GlStateManager.scalef(1.5F, 1.5F, 1.5F);
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
