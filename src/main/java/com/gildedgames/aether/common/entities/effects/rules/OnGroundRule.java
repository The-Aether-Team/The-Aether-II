package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.gildedgames.aether.common.entities.effects.EffectRule;

public class OnGroundRule implements EffectRule
{

	@Override
	public boolean isMet(Entity source)
	{
		return source.onGround;
	}
	
	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { "On-Ground" };
	}

	@Override
	public boolean blockLivingAttackAbility(Entity source, LivingHurtEvent event)
	{
		return false;
	}
	
}
