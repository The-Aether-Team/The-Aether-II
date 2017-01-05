package com.gildedgames.aether.api.registry;

import com.gildedgames.aether.api.items.equipment.effects.IEffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProcessor;
import com.gildedgames.aether.api.items.equipment.effects.IEffectState;
import net.minecraft.util.ResourceLocation;

public interface IEquipmentRegistry
{
	/**
	 * Registers an {@link IEffectProcessor} implementation. The identifier is determined by
	 * {@link IEffectProcessor#getIdentifier()}.
	 * @param processor The processor to register
	 */
	void registerEffect(IEffectProcessor processor);

	/**
	 * Returns the {@link IEffectProcessor} registered to identifier {@param key}.
	 * @param key The {@link ResourceLocation} identifier
	 * @return The {@link IEffectProcessor} for {@param key}, if it exists
	 */
	IEffectProcessor<IEffectInstance, IEffectState> getEffect(ResourceLocation key);
}
