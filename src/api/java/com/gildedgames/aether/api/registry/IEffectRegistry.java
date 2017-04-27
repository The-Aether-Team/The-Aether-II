package com.gildedgames.aether.api.registry;

import com.gildedgames.aether.api.items.equipment.effects.IEffectFactory;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import net.minecraft.util.ResourceLocation;

public interface IEffectRegistry
{
	/**
	 * Registers an {@link IEffectFactory} implementation. The identifier is determined by
	 * {@link IEffectFactory#getIdentifier()}.
	 * @param processor The processor to register
	 */
	void registerEffect(IEffectFactory processor);

	/**
	 * Returns the {@link IEffectFactory} registered to identifier {@param key}.
	 * @param key The {@link ResourceLocation} identifier
	 * @return The {@link IEffectFactory} for {@param key}, if it exists
	 */
	IEffectFactory<IEffectProvider> getFactory(ResourceLocation key);
}
