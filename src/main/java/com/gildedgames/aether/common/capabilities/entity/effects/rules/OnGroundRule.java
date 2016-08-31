package com.gildedgames.aether.common.capabilities.entity.effects.rules;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class OnGroundRule implements EntityEffectRule
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
