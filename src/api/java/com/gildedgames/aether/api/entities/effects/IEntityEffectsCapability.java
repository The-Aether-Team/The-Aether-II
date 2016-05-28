package com.gildedgames.aether.api.entities.effects;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public interface IEntityEffectsCapability
{
	void onUpdate(LivingUpdateEvent event);

	void onHurt(LivingHurtEvent event);

	/**
	 * @return The {@link EntityPlayer} this capability is attached to.
	 */
	EntityPlayer getPlayer();

	List<IEffectPool<?>> getEffectPools();

	<I extends EntityEffectInstance> void put(EntityEffectProcessor<I> processor, I instance);

	<I extends EntityEffectInstance> void removeEntry(EntityEffectProcessor<I> processor);

	<I extends EntityEffectInstance> void removeInstance(EntityEffectProcessor<I> processor, I instance);

	int getTicksSinceAttacked();
}
