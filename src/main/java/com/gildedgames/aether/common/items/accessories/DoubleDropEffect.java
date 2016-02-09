package com.gildedgames.aether.common.items.accessories;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.gildedgames.aether.common.items.weapons.swords.ItemSkyrootSword;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.aether.common.util.PlayerUtil;


/**
 * @author Brandon Pearce
 */
public class DoubleDropEffect implements AccessoryEffect
{
	
	private float percentChance;
	
	public DoubleDropEffect(float percentChance)
	{
		this.percentChance = percentChance;
	}
	
	public float getPercentChance()
	{
		return this.percentChance;
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
		if (!PlayerUtil.isAccessoryInFirstSlot(aePlayer.getPlayer(), stack))
		{
			return;
		}
		
		if (killedEntity instanceof EntityPlayer)
		{
			return;
		}

		if (killedEntity instanceof EntityHorse)
		{
			if (((EntityHorse) killedEntity).isChested())
			{
				return;
			}
		}

		int effectCount = PlayerUtil.getEffectCount(aePlayer.getPlayer(), this);
		
		if ((float)aePlayer.getPlayer().getRNG().nextInt(10) < ((float)effectCount * this.getPercentChance()))
		{
			List<ItemStack> stacks = new ArrayList<>();

			for (EntityItem item : event.drops)
			{
				if (!ItemSkyrootSword.blacklistedItems.contains(item.getEntityItem().getItem()))
				{
					stacks.add(item.getEntityItem());
				}
			}

			for (ItemStack droppedLoot : stacks)
			{
				EntityItem item = new EntityItem(event.entityLiving.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, droppedLoot);

				event.entityLiving.worldObj.spawnEntityInWorld(item);
			}
		}
	}

	@Override
	public void onAttackEntity(LivingHurtEvent event, PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}
	
}
