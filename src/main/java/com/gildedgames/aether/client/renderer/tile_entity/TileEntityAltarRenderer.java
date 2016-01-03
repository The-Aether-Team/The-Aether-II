package com.gildedgames.aether.client.renderer.tile_entity;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.tile_entities.TileEntityAltar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntityAltarRenderer extends TileEntitySpecialRenderer<TileEntityAltar>
{
	private double radius = 0.5D, theta = 5.0D;

	@Override
	public void renderTileEntityAt(TileEntityAltar altar, double x, double y, double z, float partialTicks, int destroyStage)
	{
		ItemStack stack = altar.getStackOnAltar();

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();

		GlStateManager.translate(x + 0.5f, y + 1.18f, z + 0.5f);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

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

		this.bindTexture(TextureMap.locationBlocksTexture);

		if (stack != null)
		{
			GlStateManager.pushMatrix();
			GlStateManager.pushAttrib();

			if (stack.getItem() instanceof ItemBlock)
			{
				GlStateManager.scale(0.5F, 0.5F, 0.5F);
				GlStateManager.translate(0.0f, -0.05f, 0.0f);
			}
			else
			{
				GlStateManager.scale(1.0F, 1.0F, 1.0F);
				GlStateManager.translate(0.0f, -0.13f, 0.0f);
				GlStateManager.rotate(90.0f, 90.0f, 0.0f, 0.0f);
				GlStateManager.rotate(90.0f, 0.0f, 0.0f, 90.0f);
			}

			this.renderItem(stack);

			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}

		this.renderOrbitingItems(altar.getAmbrosiumCount(), altar.prevAnimationTicks + (altar.animationTicks - altar.prevAnimationTicks) * partialTicks);

		GlStateManager.disableRescaleNormal();

		GlStateManager.popMatrix();
	}

	private void renderItem(ItemStack stack)
	{
		GlStateManager.pushMatrix();

		final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

		if (stack != null)
		{
			if (!itemRenderer.shouldRenderItemIn3D(stack))
			{
				GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			}

			IBakedModel model = itemRenderer.getItemModelMesher().getItemModel(stack);
			itemRenderer.renderItem(stack, model);
		}

		GlStateManager.popMatrix();
	}

	private void renderOrbitingItems(int amount, double ticks)
	{
		for (int i = 0; i < amount; i++)
		{
			GlStateManager.pushMatrix();

			double alpha = ticks / (12.0D * (10.0D / (double) amount));

			double dist = (Math.PI * i) / amount * 2;
			double x = this.radius * Math.cos(this.theta + dist);
			double z = this.radius * Math.sin(this.theta + dist);
			double deltaX = z * Math.cos(alpha) - x * Math.sin(alpha);
			double deltaZ = x * Math.cos(alpha) + z * Math.sin(alpha);

			GlStateManager.translate(deltaX, 0, deltaZ);

			GlStateManager.scale(0.5f, 0.5f, 0.5f);
			GlStateManager.rotate(90.0f, 0.0f, -90.0f, 0.0f);

			this.renderItem(new ItemStack(ItemsAether.ambrosium_shard));

			GlStateManager.popMatrix();
		}
	}
}
