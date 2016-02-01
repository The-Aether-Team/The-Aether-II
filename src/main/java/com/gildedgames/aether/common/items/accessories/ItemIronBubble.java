package com.gildedgames.aether.common.items.accessories;

import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.item.ItemStack;

public class ItemIronBubble extends ItemAccessory
{
	public ItemIronBubble()
	{
		super(AccessoryType.MISC);
	}

	@Override
	public void onAccessoryUpdate(PlayerAether aePlayer, ItemStack stack)
	{
		if (aePlayer.getPlayer().isInWater())
		{
			aePlayer.getPlayer().setAir(300);
		}
	}
}
