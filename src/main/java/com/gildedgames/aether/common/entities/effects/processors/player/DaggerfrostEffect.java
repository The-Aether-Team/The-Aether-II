package com.gildedgames.aether.common.entities.effects.processors.player;

import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessorPlayer;
import com.gildedgames.aether.common.entities.projectiles.EntityDaggerfrostSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

import java.util.List;

public class DaggerfrostEffect implements EffectProcessorPlayer<EffectInstance>
{

	@Override
	public String getUnlocalizedName(Entity source, EffectInstance instance)
	{
		return "ability.daggerfrost.name";
	}

	@Override
	public String[] getUnlocalizedDesc(Entity source, EffectInstance instance)
	{
		return new String[] { "ability.daggerfrost.desc" };
	}

	@Override
	public void apply(Entity source, EffectInstance instance, List<EffectInstance> all)
	{
	}

	@Override
	public void onInteract(PlayerInteractEvent event, EntityPlayer source, List<EffectInstance> instances)
	{
		World world = source.worldObj;
		ItemStack currentStack = source.getCurrentEquippedItem();

		if (currentStack != null && currentStack.getItem() instanceof ItemSnowball)
		{
			if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)
			{
				if (!world.isRemote)
				{
					world.spawnEntityInWorld(new EntityDaggerfrostSnowball(world, source));

					event.setCanceled(true);

					if (!source.capabilities.isCreativeMode)
					{
						--currentStack.stackSize;
					}
				}
			}
		}
	}

	@Override
	public void tick(Entity source, List<EffectInstance> instances)
	{
	}

	@Override
	public void cancel(Entity source, EffectInstance instance, List<EffectInstance> all)
	{
	}

	@Override
	public void onKill(LivingDropsEvent event, Entity source, List<EffectInstance> instances)
	{
	}

	@Override
	public void onAttack(LivingHurtEvent event, Entity source, List<EffectInstance> instances)
	{
	}

	@Override
	public void onPickupXP(PlayerPickupXpEvent event, EntityPlayer source, List<EffectInstance> instances)
	{
	}

	@Override
	public String[] getFormatParameters(Entity source, EffectInstance instance)
	{
		return new String[] {};
	}

}
