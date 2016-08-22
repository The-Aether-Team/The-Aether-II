package com.gildedgames.aether.api.capabilites.entity.effects;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public interface EntityEffectRule
{
	/**
	 * @param source The entity that is affected by this ability.
	 * @return Whether or not the attached ability should be active.
	 */
	boolean isMet(Entity source);

	boolean blockLivingAttackAbility(Entity source, LivingHurtEvent event);

	String[] getUnlocalizedDesc();
}
