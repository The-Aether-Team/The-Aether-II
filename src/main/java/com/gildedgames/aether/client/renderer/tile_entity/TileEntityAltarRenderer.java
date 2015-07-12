package com.gildedgames.aether.client.renderer.tile_entity;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.tile_entities.TileEntityAltar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAltarRenderer extends TileEntitySpecialRenderer
{
	private double radius = 0.5, theta = 5, alpha = 0;

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_180535_8_, int p_180535_9_)
	{
		TileEntityAltar altar = (TileEntityAltar) tileEntity;
		ItemStack stack = altar.getItemToEnchant();

		GlStateManager.pushMatrix();

		GlStateManager.translate(x + 0.5, y + 1.16, z + 0.5);

		if (stack != null)
		{
			this.renderItem(stack);
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
			Item item = entityItem.getEntityItem().getItem();

			entityItem.getEntityItem().stackSize = 1;
			entityItem.hoverStart = 0.0F;

			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();

			GlStateManager.scale(0.5F, 0.5F, 0.5F);

			if (!itemRenderer.shouldRenderItemIn3D(entityItem.getEntityItem()) || item instanceof ItemSkull)
			{
				GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			}

			GlStateManager.pushAttrib();

			RenderHelper.enableStandardItemLighting();
			itemRenderer.renderItemModel(entityItem.getEntityItem());
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
