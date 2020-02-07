package com.gildedgames.aether.client.renderer.tiles;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.client.models.entities.tile.ModelAltar;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.tiles.TileEntityAltar;
import com.gildedgames.aether.common.items.armor.ItemAetherShield;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TileEntityAltarRenderer extends TileEntitySpecialRenderer<TileEntityAltar>
{
	private final ModelAltar model = new ModelAltar();

	private final ResourceLocation texture = AetherCore.getResource("textures/tile_entities/altar.png");

	private final double radius = 0.4D;

	private final double theta = 5.0D;

	@Override
	public void render(
			final TileEntityAltar altar, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha)
	{
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();

		GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.rotate(180f, 1f, 0f, 1f);

		if (altar != null)
		{
			switch (altar.getFacing())
			{
				case NORTH:
					GlStateManager.rotate(270.0f, 0.0f, 1.0f, 0.0f);
					break;
				case WEST:
					GlStateManager.rotate(0.0f, 0.0f, 1.0f, 0.0f);
					break;
				case SOUTH:
					GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
					break;
				case EAST:
					GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
					break;
			}
		}

		this.bindTexture(this.texture);

		this.model.render(0.0625F);

		if (altar != null)
		{
			final ItemStack stack = altar.getStackOnAltar();

			GlStateManager.rotate(180f, 1f, 0f, 1f);

			GlStateManager.translate(0.0f, -0.32f, 0.0f);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

			this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

			if (stack != null)
			{
				GlStateManager.pushMatrix();
				GlStateManager.pushAttrib();

				if (stack.getItem() instanceof ItemBlock)
				{
					GlStateManager.scale(0.25F, 0.25F, 0.25F);
					GlStateManager.translate(0.0f, -0.05f, 0.0f);
				}
				else if (stack.getItem() instanceof ItemAetherShield)
				{
					GlStateManager.scale(0.5F, 0.5F, 0.5F);
					GlStateManager.translate(0.0f, -0.45f, 0.0f);
					GlStateManager.rotate(-90.0f, 90.0f, 0.0f, 0.0f);
					GlStateManager.rotate(0.0f, 0.0f, 0.0f, 90.0f);
				}
				else
				{
					GlStateManager.scale(0.5F, 0.5F, 0.5F);
					GlStateManager.translate(0.0f, -0.13f, 0.0f);
					GlStateManager.rotate(90.0f, 90.0f, 0.0f, 0.0f);
					GlStateManager.rotate(90.0f, 0.0f, 0.0f, 90.0f);
				}

				this.renderItem(stack);

				GlStateManager.popAttrib();
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

		final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

		if (stack != null)
		{
			if (!itemRenderer.shouldRenderItemIn3D(stack))
			{
				GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
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
			final double deltaX = z * Math.cos(alpha) - x * Math.sin(alpha);
			final double deltaZ = x * Math.cos(alpha) + z * Math.sin(alpha);

			GlStateManager.translate(deltaX, 0, deltaZ);

			GlStateManager.scale(0.25f, 0.25f, 0.25f);
			GlStateManager.rotate(90.0f, 0.0f, -90.0f, 0.0f);

			this.renderItem(new ItemStack(ItemsAether.ambrosium_shard));

			GlStateManager.popMatrix();
		}
	}
}
