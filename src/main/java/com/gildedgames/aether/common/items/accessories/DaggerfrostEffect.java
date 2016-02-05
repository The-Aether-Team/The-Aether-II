package com.gildedgames.aether.common.items.accessories;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.gildedgames.aether.common.entities.projectiles.EntityDaggerfrostSnowball;
import com.gildedgames.aether.common.player.PlayerAether;

public class DaggerfrostEffect implements AccessoryEffect
{

	@Override
	public void onEquipped(PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}

	@Override
	public void onUnequipped(PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}

	@Override
	public void onUpdate(PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}

	@Override
	public void onInteract(PlayerInteractEvent event, PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		ItemStack currentStack = event.entityPlayer.getCurrentEquippedItem();
		
		if (currentStack != null && currentStack.getItem() instanceof ItemSnowball)
		{
			if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)
			{
		        if (!event.world.isRemote)
		        {
		            event.world.spawnEntityInWorld(new EntityDaggerfrostSnowball(event.world, event.entityPlayer));
		            
		            event.setCanceled(true);
		            
		            if (!event.entityPlayer.capabilities.isCreativeMode)
					{
		            	--currentStack.stackSize;
					}
		        }
			}
		}
	}

	@Override
	public void onKillEntity(LivingDropsEvent event, EntityLivingBase killedEntity, PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}
	
	@Override
	public void onAttackEntity(LivingHurtEvent event, PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}

}
