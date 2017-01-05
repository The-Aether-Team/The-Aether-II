package com.gildedgames.aether.api.items.equipment.effects;

import net.minecraft.util.ResourceLocation;

public interface IEffectInstance
{
	/**
	 * Returns the unique ID of this instance's processor.
	 * @return The {@link ResourceLocation} which identifies the processor
	 */
	ResourceLocation getProcessor();
}
