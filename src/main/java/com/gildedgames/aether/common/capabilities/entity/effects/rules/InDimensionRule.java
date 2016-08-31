package com.gildedgames.aether.common.capabilities.entity.effects.rules;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
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
