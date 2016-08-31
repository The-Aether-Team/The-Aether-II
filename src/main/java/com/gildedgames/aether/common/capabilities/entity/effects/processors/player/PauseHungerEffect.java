package com.gildedgames.aether.common.capabilities.entity.effects.processors.player;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessorPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;

public class PauseHungerEffect extends AbstractEffectProcessorPlayer<EntityEffectInstance>
{

	public PauseHungerEffect()
	{
		super("ability.pauseHunger.localizedName", "ability.pauseHunger.desc");
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

				if (stats.getFoodLevel() > instance.getAttributes().getInteger("foodLevel") || stats.getSaturationLevel() > instance.getAttributes().getFloat("foodSaturation"))
				{
					instance.getAttributes().setInteger("foodLevel", stats.getFoodLevel());
					instance.getAttributes().setFloat("foodSaturation", stats.getSaturationLevel());
				}

				ObfuscationReflectionHelper.setPrivateValue(FoodStats.class, stats, instance.getAttributes().getInteger("foodLevel"), ReflectionAether.FOOD_LEVEL.getMappings());
				ObfuscationReflectionHelper.setPrivateValue(FoodStats.class, stats, instance.getAttributes().getFloat("foodSaturation"), ReflectionAether.FOOD_SATURATION_LEVEL.getMappings());
			}
		}
	}

	@Override
	public void cancel(Entity source, EntityEffectInstance instance, List<EntityEffectInstance> all)
	{
		instance.getAttributes().setInteger("foodLevel", 0);
	}

}
