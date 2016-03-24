package com.gildedgames.aether.common.entities.effects.rules;

import com.gildedgames.aether.common.entities.effects.EffectRule;
import com.gildedgames.aether.common.entities.effects.EntityEffects;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class OutOfCombatRule implements EffectRule
{

	private int minimumTime;

	public OutOfCombatRule(int minimumTime)
	{
		this.minimumTime = minimumTime;
	}

	@Override
	public boolean isMet(Entity source)
	{
		EntityEffects effects = EntityEffects.get(source);

		return effects.getTicksSinceAttacked() > this.minimumTime;
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { "Out of Combat" };
	}

	@Override
	public boolean blockLivingAttackAbility(Entity source, LivingHurtEvent event)
	{
		return false;
	}

}