package com.gildedgames.aether.common.entities.effects.rules;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class InDimensionRule implements EntityEffectRule
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
		return new String[] { "In the " + DimensionManager.getProvider(this.dimID).getDimensionType().getName() };
	}

	@Override
	public boolean blockLivingAttackAbility(Entity source, LivingHurtEvent event)
	{
		return false;
	}

}
