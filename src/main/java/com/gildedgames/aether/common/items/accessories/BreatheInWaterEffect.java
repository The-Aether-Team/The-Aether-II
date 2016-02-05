package com.gildedgames.aether.common.items.accessories;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.gildedgames.aether.common.player.PlayerAether;


/**
 * Sets player's "air" level to 300, which is one bubble worth of air. This means allows
 * them to breathe under water.
 * @author Brandon Pearce
 */
public class BreatheInWaterEffect implements AccessoryEffect
{
	
	public BreatheInWaterEffect()
	{
		
	}

	@Override
	public void onUpdate(PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		if (aePlayer.getPlayer().isInWater())
		{
			aePlayer.getPlayer().setAir(300);
		}
	}

	@Override
	public void onEquipped(PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}

	@Override
	public void onUnequipped(PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}

	@Override
	public void onInteract(PlayerInteractEvent event, PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}
	
}
