package com.gildedgames.aether.common.entities.effects.processors.player;

import akka.util.Reflect;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.entities.effects.AbstractEffectProcessorPlayer;
import com.gildedgames.aether.common.entities.effects.EffectProcessorPlayer;
import com.gildedgames.aether.common.entities.effects.processors.ModifyDamageEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;

public class ReduceHungerEffect extends AbstractEffectProcessorPlayer<EntityEffectInstance>
{

	public ReduceHungerEffect()
	{
		super("ability.reduceHunger.localizedName", "ability.reduceHunger.desc");
	}

	@Override
	public void apply(Entity source, EntityEffectInstance instance, List<EntityEffectInstance> all)
	{
		if (!(source instanceof EntityPlayer))
		{
			return;
		}

		EntityPlayer player = (EntityPlayer) source;

		instance.getAttributes().setInteger("foodLevel", player.getFoodStats().getFoodLevel());
	}

	@Override
	public void tick(Entity source, List<EntityEffectInstance> all)
	{
		if (!(source instanceof EntityPlayer))
		{
			return;
		}

		EntityPlayer player = (EntityPlayer) source;

		World world = player.worldObj;

		if (!world.isRemote)
		{
			for (EntityEffectInstance instance : all)
			{
				FoodStats stats = player.getFoodStats();

				if (stats.getFoodLevel() < instance.getAttributes().getFloat("foodLevel"))
				{
					instance.getAttributes().setInteger("foodLevel", stats.getFoodLevel());

					float exhaustion = ObfuscationReflectionHelper.getPrivateValue(FoodStats.class, stats, "field_75126_c", "foodExhaustionLevel");

					ObfuscationReflectionHelper.setPrivateValue(FoodStats.class, stats, Math.max(0, exhaustion - 0.7f), ReflectionAether.FOOD_EXHAUSTION_LEVEL.getMappings());
					ObfuscationReflectionHelper.setPrivateValue(FoodStats.class, player.getFoodStats(), stats.getSaturationLevel() + 2.0f, ReflectionAether.FOOD_SATURATION_LEVEL.getMappings());
				}
				else if (stats.getFoodLevel() > instance.getAttributes().getFloat("foodLevel"))
				{
					instance.getAttributes().setInteger("foodLevel", stats.getFoodLevel());
				}
			}
		}
	}

	@Override
	public void cancel(Entity source, EntityEffectInstance instance, List<EntityEffectInstance> all)
	{
		instance.getAttributes().setInteger("foodLevel", 0);
	}

}
