package com.gildedgames.aether.api.registry;

import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.items.equipment.effects.IEffect;
import net.minecraft.util.ResourceLocation;

public interface IEquipmentRegistry
{
	/**
	 * Registers an {@link IEffect} implementation. The identifier is determined by
	 * {@link IEffect#getIdentifier()}.
	 * @param processor The processor to register
	 */
	void registerEffect(IEffect processor);

	/**
	 * Returns the {@link IEffect} registered to identifier {@param key}.
	 * @param key The {@link ResourceLocation} identifier
	 * @return The {@link IEffect} for {@param key}, if it exists
	 */
	IEffect<IEffectProvider> getFactory(ResourceLocation key);
}
