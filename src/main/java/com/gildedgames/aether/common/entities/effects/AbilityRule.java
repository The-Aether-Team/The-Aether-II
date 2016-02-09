package com.gildedgames.aether.common.entities.effects;

import net.minecraft.entity.Entity;

public interface AbilityRule<S extends Entity>
{

	/**
	 * @param source The entity that is affected by this ability.
	 * @return Whether or not the attached ability should be active.
	 */
	boolean isMet(S source);

	String[] getUnlocalizedDesc();
	
}