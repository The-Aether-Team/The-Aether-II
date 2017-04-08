package com.gildedgames.aether.client.renderer.items;

import com.gildedgames.aether.common.entities.item.EntityRewardItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;

public class RenderRewardItemStack extends RenderEntityItem
{

	public RenderRewardItemStack(RenderManager renderManager, RenderItem renderItem)
	{
		super(renderManager, renderItem);
	}

	@Override
	public void doRender(EntityItem item, double x, double y, double z, float f1, float f2)
	{
		if (item instanceof EntityRewardItemStack)
		{
			EntityRewardItemStack rewardStack = (EntityRewardItemStack) item;

			if (!rewardStack.getPlayerName().equalsIgnoreCase(Minecraft.getMinecraft().player.getCommandSenderEntity().getName()))
			{
				return;
			}
		}

		super.doRender(item, x, y, z, f1, f2);
	}

}
