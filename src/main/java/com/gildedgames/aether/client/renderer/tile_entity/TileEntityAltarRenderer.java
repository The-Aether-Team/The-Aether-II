package com.gildedgames.aether.client.renderer.tile_entity;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.tile_entities.TileEntityAltar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class TileEntityAltarRenderer extends TileEntitySpecialRenderer<TileEntityAltar>
{
	private double radius = 0.5, theta = 5, alpha = 0;

	@Override
	public void renderTileEntityAt(TileEntityAltar altar, double x, double y, double z, float p_180535_8_, int p_180535_9_)
	{
		ItemStack stack = altar.getStackOnAltar();

		GlStateManager.pushMatrix();

		GlStateManager.translate(x + 0.5f, y + 1.18f, z + 0.5f);

		if (stack != null)
		{
			GlStateManager.pushMatrix();

			if (!(stack.getItem() instanceof ItemBlock))
			{
				GlStateManager.translate(0, -0.13f, 0);
				GlStateManager.rotate(90f, 90f, 0f, 0f);
			}

			this.renderItem(stack);

			GlStateManager.popMatrix();
		}

		this.renderOrbitingItems(altar.getAmbrosiumCount(), 0.15);

		GlStateManager.popMatrix();
	}

	private void renderItem(ItemStack stack)
	{
		final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

		if (stack != null)
		{
			EntityItem entityItem = new EntityItem(this.getWorld(), 0, 0, 0, stack);

			entityItem.getEntityItem().stackSize = 1;
			entityItem.hoverStart = 0.0F;

			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();

			GlStateManager.scale(0.6F, 0.6F, 0.6F);

			if (!itemRenderer.shouldRenderItemIn3D(entityItem.getEntityItem()))
			{
				GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			}

			GlStateManager.pushAttrib();

			RenderHelper.enableStandardItemLighting();

			IBakedModel model = itemRenderer.getItemModelMesher().getItemModel(entityItem.getEntityItem());
			itemRenderer.renderItem(entityItem.getEntityItem(), model);

			RenderHelper.disableStandardItemLighting();

			GlStateManager.popAttrib();

			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
	}

	private void renderOrbitingItems(int amount, double interval)
	{
		for (int i = 0; i < amount; i++)
		{
			GlStateManager.pushMatrix();

			double dist = (Math.PI * i) / amount * 2;
			double x = this.radius * Math.cos(this.theta + dist);
			double z = this.radius * Math.sin(this.theta + dist);
			double deltaX = z * Math.cos(this.alpha) - x * Math.sin(this.alpha);
			double deltaZ = x * Math.cos(this.alpha) + z * Math.sin(this.alpha);

			GlStateManager.translate(deltaX, 0, deltaZ);
			this.alpha += interval / 100;

			GlStateManager.scale(0.33f, 0.33f, 0.33f);

			this.renderItem(new ItemStack(ItemsAether.ambrosium_shard));

			GlStateManager.popMatrix();
		}
	}
}
