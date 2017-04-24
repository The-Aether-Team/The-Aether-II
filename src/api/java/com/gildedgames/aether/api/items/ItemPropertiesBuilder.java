package com.gildedgames.aether.api.items;

import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;

import java.util.ArrayList;
import java.util.Collection;

public class ItemPropertiesBuilder
{
	private final Collection<IEffectProvider> effects = new ArrayList<>();

	private ItemEquipmentSlot slot;

	private ItemRarity rarity;
	
	/**
	 * Sets the rarity of this item.
	 * @param rarity The rarity of this item
	 */
	public ItemPropertiesBuilder withRarity(ItemRarity rarity)
	{
		this.rarity = rarity;

		return this;
	}

	/**
	 * Sets the slot this equipment item will use and turns this item into equipment.
	 * @param slot The equipment slot this item will use
	 */
	public ItemPropertiesBuilder withSlot(ItemEquipmentSlot slot)
	{
		this.slot = slot;

		return this;
	}

	/**
	 * Adds an {@link IEffectProvider} this equipment item will provide.
	 * @param effect The effect instance to add
	 */
	public ItemPropertiesBuilder withEffect(IEffectProvider effect)
	{
		this.effects.add(effect);

		return this;
	}

	public IItemProperties build()
	{
		return new ItemProperties(this.slot, this.effects, this.rarity);
	}
}
