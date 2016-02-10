package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.EntityLivingBase;

import com.gildedgames.aether.common.entities.effects.AbilityRule;
import com.gildedgames.aether.common.entities.effects.EntityEffects;

public class OutOfCombatRule<S extends EntityLivingBase> implements AbilityRule<S>
{
	
	private int minimumTime;
	
	public OutOfCombatRule(int minimumTime)
	{
		this.minimumTime = minimumTime;
	}

	@Override
	public boolean isMet(S source)
	{
		EntityEffects<S> effects = EntityEffects.get(source);
		
		return effects.getTicksSinceAttacked() > this.minimumTime;
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { "Out of Combat" };
	}

}