package com.gildedgames.aether.common.entities.effects.rules;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import net.minecraft.entity.Entity;
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
	public boolean blockLivingAttackAbility(Entity source, LivingHurtEvent event)
	{
		return false;
	}

}
