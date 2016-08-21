package com.gildedgames.aether.common.entities.effects.processors.player;

import com.gildedgames.aether.api.entities.effects.EntityEffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessorPlayer;
import com.gildedgames.aether.common.entities.projectiles.EntityDaggerfrostSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

import java.util.List;

public class DaggerfrostEffect implements EffectProcessorPlayer<EntityEffectInstance>
{

	@Override
	public String getUnlocalizedName(Entity source, EntityEffectInstance instance)
	{
		return "ability.daggerfrost.localizedName";
	}

	@Override
	public String[] getUnlocalizedDesc(Entity source, EntityEffectInstance instance)
	{
		return new String[] { "ability.daggerfrost.desc" };
	}

	@Override
	public void apply(Entity source, EntityEffectInstance instance, List<EntityEffectInstance> all)
	{
	}

	@Override
	public void onInteract(PlayerInteractEvent event, EntityPlayer source, List<EntityEffectInstance> instances)
	{
		World world = source.worldObj;

		ItemStack currentStack = source.getHeldItem(EnumHand.MAIN_HAND);

		if (currentStack != null && currentStack.getItem() instanceof ItemSnowball)
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

	@Override
	public void tick(Entity source, List<EntityEffectInstance> instances)
	{
	}

	@Override
	public void cancel(Entity source, EntityEffectInstance instance, List<EntityEffectInstance> all)
	{
	}

	@Override
	public void onKill(LivingDropsEvent event, Entity source, List<EntityEffectInstance> instances)
	{
	}

	@Override
	public void onAttack(LivingHurtEvent event, Entity source, List<EntityEffectInstance> instances)
	{
	}

	@Override
	public void onPickupXP(PlayerPickupXpEvent event, EntityPlayer source, List<EntityEffectInstance> instances)
	{
	}

	@Override
	public String[] getFormatParameters(Entity source, EntityEffectInstance instance)
	{
		return new String[] {};
	}

}
