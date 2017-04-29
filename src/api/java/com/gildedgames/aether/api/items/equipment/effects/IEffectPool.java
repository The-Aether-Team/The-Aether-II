package com.gildedgames.aether.api.items.equipment.effects;

import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.Optional;

/**
 * Manages the providers for a specific {@link IEffectFactory}.
 */
public interface IEffectPool<T extends IEffectProvider>
{
	/**
	 * Returns the {@link ItemStack} providing {@param instance} to the player.
	 *
	 * @param instance The effect instance
	 * @return The {@link ItemStack}, empty if none
	 */
	ItemStack getProvider(T instance);

	/**
	 * Returns the collection of active providers for this pool.
	 * @return A collection of {@link IEffectProvider}
	 */
	Collection<T> getActiveProviders();

	/**
	 * Returns the {@link EffectInstance} that's currently owned by this pool.
	 * @return The {@link EffectInstance} of this pool, empty if it has no instance.
	 */
	Optional<EffectInstance> getInstance();

	/**
	 * Returns whether or not the effect pool has providers.
	 * @return True if the pool has providers, otherwise false.
	 */
	boolean isEmpty();
}
