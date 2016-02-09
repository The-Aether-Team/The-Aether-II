package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.Entity;
import net.minecraftforge.common.DimensionManager;

import com.gildedgames.aether.common.entities.effects.AbilityRule;

public class InDimensionRule<S extends Entity> implements AbilityRule<S>
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
		return new String[] { "in the " + DimensionManager.getProvider(this.dimID).getDimensionName() };
	}

}