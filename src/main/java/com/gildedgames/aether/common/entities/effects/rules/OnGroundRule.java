package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.Entity;

import com.gildedgames.aether.common.entities.effects.AbilityRule;

public class OnGroundRule<S extends Entity> implements AbilityRule<S>
{

	@Override
	public boolean isMet(S source)
	{
		return source.onGround;
	}
	
	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { "On-Ground" };
	}

}
