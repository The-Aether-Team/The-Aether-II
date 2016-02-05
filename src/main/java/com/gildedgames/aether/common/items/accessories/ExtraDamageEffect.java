package com.gildedgames.aether.common.items.accessories;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.aether.common.util.PlayerUtil;


/**
 * @author Brandon Pearce
 */
public class ExtraDamageEffect implements AccessoryEffect
{
	
	private float extraDamage;
	
	public ExtraDamageEffect(float extraDamage)
	{
		this.extraDamage = extraDamage;
	}
	
	public float getPercentChance()
	{
		return this.extraDamage;
	}

	@Override
	public void onUpdate(PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
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

	@Override
	public void onKillEntity(LivingDropsEvent event, EntityLivingBase killedEntity, PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}

	@Override
	public void onAttackEntity(LivingHurtEvent event, PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		if (!PlayerUtil.isAccessoryInFirstSlot(aePlayer.getPlayer(), stack))
		{
			return;
		}
		
		EntityPlayer player = (EntityPlayer) event.source.getEntity();

		if (player.swingProgressInt == -1)
		{
			player.swingProgressInt = 0;
			
			int effectCount = PlayerUtil.getEffectCount(player, this);

			event.entityLiving.setHealth(Math.max(0, event.entityLiving.getHealth() - (effectCount * this.extraDamage)));
		}
	}
	
}
