package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.EntityLivingBase;

import com.gildedgames.aether.common.entities.effects.AbilityRule;

public class OutOfCombatRule<S extends EntityLivingBase> implements AbilityRule<S>
{

	@Override
	public boolean isMet(EntityLivingBase source)
	{
		return false;
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { "Out of Combat" };
	}

}