package com.gildedgames.aether.api.items.equipment;

import com.gildedgames.aether.api.capabilites.items.properties.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.IEffectInstance;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Factory for {@link IEquipmentProperties}.
 */
public class EquipmentBuilder
{
	private final Collection<IEffectInstance> effects = new ArrayList<>();

	private ItemEquipmentSlot slot;

	/**
	 * Sets the slot this equipment item will use. This is required.
	 * @param slot The slot this equipment will use.
	 */
	public EquipmentBuilder setSlot(ItemEquipmentSlot slot)
	{
		this.slot = slot;

		return this;
	}

	/**
	 * Adds an {@link IEffectInstance} this equipment item will provide.
	 * @param effect The effect instance to add
	 */
	public EquipmentBuilder addEffect(IEffectInstance effect)
	{
		this.effects.add(effect);

		return this;
	}

	/**
	 * @return Builds an {@link EquipmentProperties} from this factory.
	 */
	public EquipmentProperties build()
	{
		if (this.slot == null)
		{
			throw new IllegalArgumentException("The equipment's item slot cannot be null");
		}

		return new EquipmentProperties(this.slot, this.effects);
	}
}
