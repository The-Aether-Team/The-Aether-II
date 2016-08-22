package com.gildedgames.aether.api.capabilites.entity.effects;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public interface IEntityEffectsCapability
{
	void onUpdate(LivingUpdateEvent event);

	void onHurt(LivingHurtEvent event);

	Entity getEntity();

	List<IEffectPool<?>> getEffectPools();

	<I extends EntityEffectInstance> void addInstance(EntityEffectProcessor<I> processor, I instance);

	<I extends EntityEffectInstance> void removeEntry(EntityEffectProcessor<I> processor);

	<I extends EntityEffectInstance> void removeInstance(EntityEffectProcessor<I> processor, I instance);

	int getTicksSinceAttacked();
}
