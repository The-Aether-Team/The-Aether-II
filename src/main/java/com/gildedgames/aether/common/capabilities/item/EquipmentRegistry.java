package com.gildedgames.aether.common.capabilities.item;

import com.gildedgames.aether.api.items.equipment.effects.IEffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProcessor;
import com.gildedgames.aether.api.items.equipment.effects.IEffectState;
import com.gildedgames.aether.api.registry.IEquipmentRegistry;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class EquipmentRegistry implements IEquipmentRegistry
{
	private HashMap<ResourceLocation, IEffectProcessor<IEffectInstance, IEffectState>> processors = new HashMap<>();

	@Override
	@SuppressWarnings("unchecked")
	public void registerEffect(IEffectProcessor processor)
	{
		if (this.processors.containsKey(processor.getIdentifier()))
		{
			throw new IllegalArgumentException("Effect processor for " + processor.getIdentifier() + " already exists");
		}

		this.processors.put(processor.getIdentifier(), processor);
	}

	@Override
	public IEffectProcessor<IEffectInstance, IEffectState> getEffect(ResourceLocation key)
	{
		return this.processors.get(key);
	}
}
