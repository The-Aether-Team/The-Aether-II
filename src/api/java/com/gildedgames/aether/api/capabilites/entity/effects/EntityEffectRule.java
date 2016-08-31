package com.gildedgames.aether.api.capabilites.entity.effects;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public interface EntityEffectRule
{
	/**
	 * @param source The entity that is affected by this ability.
	 * @return Whether or not the attached ability should be active.
	 */
	boolean isMet(Entity source);

	boolean blockLivingAttack(Entity source, LivingAttackEvent event);

	boolean blockLivingHurt(Entity source, LivingHurtEvent event);

	String[] getUnlocalizedDesc();
}
