package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.gildedgames.aether.common.entities.effects.EffectRule;

public class InDirectMoonlightRule implements EffectRule
{

	@Override
	public boolean isMet(Entity source)
	{
		return source.worldObj.getLight(source.getPosition()) >= 15 && !source.worldObj.isDaytime();
	}
	
	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { "In Direct Moonlight" };
	}
	
	@Override
	public boolean blockLivingAttackAbility(Entity source, LivingHurtEvent event)
	{
		return false;
	}

}
