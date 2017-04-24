package com.gildedgames.aether.api.items.equipment.effects;

import net.minecraft.util.ResourceLocation;

import java.util.Collection;

/***
 * Creates an {@link EffectInstance} from the collection of {@link IEffectProvider} an entity has.
 *
 * In layman's terms:
 * - {@link IEffect} creates an instanced effect for the entity from a set of {@link IEffectProvider}
 * - {@link IEffectProvider} provides an effect to an entity
 * - {@link EffectInstance} is the instanced effect for an entity
 *
 * When a {@link IEffectProvider} is added to or removed from an entity (via equipping or
 * removing an item, usually), a new {@link EffectInstance} will be created to represent the
 * updated effect.
 *
 * @param <T> The {@link IEffectProvider} for this factory
 */
public interface IEffect<T extends IEffectProvider>
{
	/**
	 * Creates an {@link EffectInstance} of this effect from {@param instances}.
	 * @param providers The instances of this effect the entity currently has
	 * @return The {@link EffectInstance} for the entity
	 */
	EffectInstance createInstance(Collection<T> providers);

	/**
	 * The unique identifier used to link the factory a {@link IEffectProvider} uses. This
	 * should never change during runtime.
	 * @return The {@link ResourceLocation} representing this effect's unique identifier
	 */
	ResourceLocation getIdentifier();
}
