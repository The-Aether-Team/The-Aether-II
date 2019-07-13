package com.gildedgames.aether.client.renderer.tiles;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.client.models.entities.tile.ModelAltar;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.tiles.TileEntityAltar;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TileEntityAltarRenderer extends TileEntityRenderer<TileEntityAltar>
{
	private final ModelAltar model = new ModelAltar();

	private final ResourceLocation texture = AetherCore.getResource("textures/tile_entities/altar.png");

	private final double radius = 0.5D;

	private final double theta = 5.0D;

	@Override
	public void render(final TileEntityAltar altar, final double x, final double y, final double z, final float partialTicks, final int destroyStage)
	{
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();

		GlStateManager.translatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.rotatef(180f, 1f, 0f, 1f);

		if (altar != null)
		{
			switch (altar.getFacing())
			{
				case NORTH:
					GlStateManager.rotatef(270.0f, 0.0f, 1.0f, 0.0f);
					break;
				case WEST:
					GlStateManager.rotatef(0.0f, 0.0f, 1.0f, 0.0f);
					break;
				case SOUTH:
					GlStateManager.rotatef(90.0f, 0.0f, 1.0f, 0.0f);
					break;
				case EAST:
					GlStateManager.rotatef(180.0f, 0.0f, 1.0f, 0.0f);
					break;
			}
		}

		this.bindTexture(this.texture);

		this.model.render(0.0625F);

		if (altar != null)
		{
			final ItemStack stack = altar.getStackOnAltar();

			GlStateManager.rotatef(180f, 1f, 0f, 1f);

			GlStateManager.translatef(0.0f, -0.32f, 0.0f);
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

			this.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

			if (!stack.isEmpty())
			{
				GlStateManager.pushMatrix();

				if (stack.getItem() instanceof BlockItem)
				{
					GlStateManager.scalef(0.25F, 0.25F, 0.25F);
					GlStateManager.translatef(0.0f, -0.05f, 0.0f);
				}
				else
				{
					GlStateManager.scalef(0.5F, 0.5F, 0.5F);
					GlStateManager.translatef(0.0f, -0.13f, 0.0f);
					GlStateManager.rotatef(90.0f, 90.0f, 0.0f, 0.0f);
					GlStateManager.rotatef(90.0f, 0.0f, 0.0f, 90.0f);
				}

				this.renderItem(stack);

				GlStateManager.popMatrix();
			}

			this.renderOrbitingItems(altar.getAmbrosiumCount(),
					altar.prevAnimationTicks + (altar.animationTicks - altar.prevAnimationTicks) * partialTicks);
		}

		GlStateManager.disableRescaleNormal();

		GlStateManager.popMatrix();
	}

	private void renderItem(final ItemStack stack)
	{
		GlStateManager.pushMatrix();

		final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

		if (stack != null)
		{
			if (!itemRenderer.shouldRenderItemIn3D(stack))
			{
				GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
			}

			final IBakedModel model = itemRenderer.getItemModelMesher().getItemModel(stack);
			itemRenderer.renderItem(stack, model);
		}

		GlStateManager.popMatrix();
	}

	private void renderOrbitingItems(final int amount, final double ticks)
	{
		for (int i = 0; i < amount; i++)
		{
			GlStateManager.pushMatrix();

			final double alpha = ticks / (12.0D * (10.0D / (double) amount));

			final double dist = (Math.PI * i) / amount * 2;
			final double x = this.radius * Math.cos(this.theta + dist);
			final double z = this.radius * Math.sin(this.theta + dist);
			final float deltaX = (float) (z * Math.cos(alpha) - x * Math.sin(alpha));
			final float deltaZ = (float) (x * Math.cos(alpha) + z * Math.sin(alpha));

			GlStateManager.translatef(deltaX, 0.0f, deltaZ);

			GlStateManager.scalef(0.25f, 0.25f, 0.25f);
			GlStateManager.rotatef(90.0f, 0.0f, -90.0f, 0.0f);

			this.renderItem(new ItemStack(ItemsAether.ambrosium_shard));

			GlStateManager.popMatrix();
		}
	}
}
