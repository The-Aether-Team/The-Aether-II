package com.gildedgames.aether.api.items.equipment;

import com.gildedgames.aether.api.capabilites.items.properties.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.IEffectInstance;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class EquipmentProperties implements IEquipmentProperties
{
	private final ItemEquipmentSlot slot;

	private final Collection<IEffectInstance> instances;

	protected EquipmentProperties(ItemEquipmentSlot slot, Collection<IEffectInstance> instances)
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
	public Collection<IEffectInstance> getEffectInstances()
	{
		return Collections.unmodifiableCollection(this.instances);
	}

	public static EquipmentBuilder builder()
	{
		return new EquipmentBuilder();
	}
}
