package com.gildedgames.aether.common.capabilities.entity.effects.rules;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class InDirectMoonlightRule implements EntityEffectRule
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
	public boolean blockLivingAttack(Entity source, LivingAttackEvent event)
	{
		return false;
	}

	@Override
	public boolean blockLivingHurt(Entity source, LivingHurtEvent event)
	{
		return false;
	}

}
