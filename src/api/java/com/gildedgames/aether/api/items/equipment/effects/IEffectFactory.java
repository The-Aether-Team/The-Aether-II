package com.gildedgames.aether.api.items.equipment.effects;

import net.minecraft.util.ResourceLocation;

/***
 * Creates an {@link EffectInstance} from the collection of {@link IEffectProvider} an entity has.
 *
 * When a {@link IEffectProvider} is added to or removed from an entity (via equipping or
 * removing an item, usually), a new {@link EffectInstance} will be created to represent the
 * updated effect, clearing the previous state of it.
 *
 * @param <T> The {@link IEffectProvider} for this factory
 */
public interface IEffectFactory<T extends IEffectProvider>
{
	/**
	 * Creates an {@link EffectInstance} of this effect from {@param instances}.
	 *
	 * @param pool The pool this instance will be created for
	 * @return The {@link EffectInstance} for the entity
	 */
	EffectInstance createInstance(IEffectPool<T> pool);

	/**
	 * The unique identifier used to link the factory a {@link IEffectProvider} uses. This
	 * should never change during runtime.
	 *
	 * @return The {@link ResourceLocation} representing this effect's unique identifier
	 */
	ResourceLocation getIdentifier();
}
