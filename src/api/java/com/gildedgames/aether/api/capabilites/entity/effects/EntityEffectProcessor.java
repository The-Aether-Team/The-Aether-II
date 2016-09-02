package com.gildedgames.aether.api.capabilites.entity.effects;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public interface EntityEffectProcessor<I extends EntityEffectInstance>
{

	String getUnlocalizedName(Entity source, I instance);

	String[] getUnlocalizedDesc(Entity source, I instance);

	String[] getFormatParameters(Entity source, I instance);



	/**
	 * Called once when this ability is added to an entity.
	 * @param source The entity that is affected by this ability. 
	 * @param all The instances of this ability that are attached to the source.
	 */
	void apply(Entity source, I instance, List<I> all);

	void tick(Entity source, List<I> all);

	/**
	 * Called once when this ability is removed from an entity.
	 * @param source The entity that is affected by this ability. 
	 * @param all The instances of this ability that are attached to the source.
	 */
	void cancel(Entity source, I instance, List<I> all);

	/**
	 * Called when the source kills a living entity while they have this ability.
	 * @param event The event that is fired.
	 * @param source The entity that is affected by this ability. 
	 * @param all The instances of this ability that are attached to the source.
	 */
	void onKill(LivingDropsEvent event, Entity source, List<I> all);

	/**
	 * Called when the source is hurt while they have this ability.
	 * @param event The event that is fired.
	 * @param source The entity that is affected by this ability. 
	 * @param all The instances of this ability that are attached to the source.
	 */
	void onHurt(LivingHurtEvent event, Entity source, List<I> all);

	/**
	 * Called when the source is attacked while they have this ability.
	 * @param source The entity that is affected by this ability.
	 * @param all The instances of this ability that are attached to the source.
	 */
	void onAttacked(float amount, Entity attacker, Entity source, List<I> all);

	/**
	 * Called when the source attacks an entity while they have this ability.
	 * @param source The entity that is affected by this ability.
	 * @param all The instances of this ability that are attached to the source.
	 */
	void onAttack(float amount, Entity target, Entity source, List<I> all);

}
