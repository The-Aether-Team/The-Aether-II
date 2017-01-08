package com.gildedgames.aether.common.capabilities.item;

import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.items.equipment.effects.IEffect;
import com.gildedgames.aether.api.registry.IEquipmentRegistry;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class EquipmentRegistry implements IEquipmentRegistry
{
	private HashMap<ResourceLocation, IEffect<IEffectProvider>> factories = new HashMap<>();

	@Override
	@SuppressWarnings("unchecked")
	public void registerEffect(IEffect processor)
	{
		if (this.factories.containsKey(processor.getIdentifier()))
		{
			throw new IllegalArgumentException("Effect processor for " + processor.getIdentifier() + " already exists");
		}

		this.factories.put(processor.getIdentifier(), processor);
	}

	@Override
	public IEffect<IEffectProvider> getFactory(ResourceLocation key)
	{
		return this.factories.get(key);
	}
}
