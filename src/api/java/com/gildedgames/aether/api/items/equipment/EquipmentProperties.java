package com.gildedgames.aether.api.items.equipment;

import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class EquipmentProperties implements IEquipmentProperties
{
	private final ItemEquipmentSlot slot;

	private final Collection<IEffectProvider> instances;

	protected EquipmentProperties(ItemEquipmentSlot slot, Collection<IEffectProvider> instances)
	{
		if (slot == null)
		{
			throw new IllegalArgumentException("The equipment slot cannot be null");
		}

		this.slot = slot;
		this.instances = new ArrayList<>(instances);
	}

	@Override
	@Nonnull
	public ItemEquipmentSlot getSlot()
	{
		return this.slot;
	}

	@Override
	public Collection<IEffectProvider> getEffectInstances()
	{
		return Collections.unmodifiableCollection(this.instances);
	}

	public static EquipmentBuilder builder()
	{
		return new EquipmentBuilder();
	}
}
