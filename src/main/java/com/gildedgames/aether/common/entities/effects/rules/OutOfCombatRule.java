package com.gildedgames.aether.common.entities.effects.rules;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.api.capabilites.entity.effects.IEntityEffectsCapability;
import com.gildedgames.aether.common.entities.effects.EntityEffects;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class OutOfCombatRule implements EntityEffectRule
{

	private int minimumTime;

	public OutOfCombatRule(int minimumTime)
	{
		this.minimumTime = minimumTime;
	}

	@Override
	public boolean isMet(Entity source)
	{
		IEntityEffectsCapability effects = EntityEffects.get(source);

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
