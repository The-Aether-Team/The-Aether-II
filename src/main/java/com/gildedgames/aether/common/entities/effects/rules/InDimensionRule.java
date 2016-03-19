package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.Entity;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.gildedgames.aether.common.entities.effects.EffectRule;

public class InDimensionRule implements EffectRule
{
	
	private int dimID;
	
	public InDimensionRule(int dimID)
	{
		this.dimID = dimID;
	}

	@Override
	public boolean isMet(Entity source)
	{
		return source.dimension == this.dimID;
	}
	
	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { "In the " + DimensionManager.getProvider(this.dimID).getDimensionName() };
	}
	
	@Override
	public boolean blockLivingAttackAbility(Entity source, LivingHurtEvent event)
	{
		return false;
	}
	
}