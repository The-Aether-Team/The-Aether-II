package com.gildedgames.aether.common.items.accessories;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class RegenStoneEffect implements AccessoryEffect
{
	private static final int MINIMUM_TIME = 160;

	@Override
	public void onEquipped(PlayerAether aePlayer, ItemStack stack, AccessoryType type) { }

	@Override
	public void onUnequipped(PlayerAether aePlayer, ItemStack stack, AccessoryType type) { }

	@Override
	public void onUpdate(PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		int count = aePlayer.getInventoryAccessories().getCountOfEquippedAccessory(ItemsAether.regeneration_stone);

		if (count > 0)
		{
			aePlayer.ticksSinceAttacked++;

			if (aePlayer.getPlayer().getFoodStats().getFoodLevel() > 2)
			{
				int inbetween = 20 - (count * 4);

				if (aePlayer.ticksSinceAttacked > MINIMUM_TIME && aePlayer.ticksSinceAttacked % inbetween == 0)
				{
					aePlayer.getPlayer().heal(0.5F);
				}
			}
		}
	}

	@Override
	public void onInteract(PlayerInteractEvent event, PlayerAether aePlayer, ItemStack stack, AccessoryType type) { }

	@Override
	public void onKillEntity(LivingDropsEvent event, EntityLivingBase killedEntity, PlayerAether aePlayer, ItemStack stack, AccessoryType type) { }

	@Override
	public void onAttackEntity(LivingHurtEvent event, PlayerAether aePlayer, ItemStack stack, AccessoryType type) { }
}
