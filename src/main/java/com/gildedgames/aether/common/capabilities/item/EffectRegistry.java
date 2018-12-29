package com.gildedgames.aether.common.capabilities.item;

import com.gildedgames.aether.api.items.equipment.effects.IEffectFactory;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.registry.IEffectRegistry;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class EffectRegistry implements IEffectRegistry
{
	private final HashMap<ResourceLocation, IEffectFactory<IEffectProvider>> factories = new HashMap<>();

	@Override
	@SuppressWarnings("unchecked")
	public void registerEffect(IEffectFactory processor)
	{
		if (this.factories.containsKey(processor.getIdentifier()))
		{
			throw new IllegalArgumentException("Effect processor for " + processor.getIdentifier() + " already exists");
		}

		this.factories.put(processor.getIdentifier(), processor);
	}

	@Override
	public IEffectFactory<IEffectProvider> getFactory(ResourceLocation key)
	{
		return this.factories.get(key);
	}
}
