package com.gildedgames.aether.api.items;

import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.IEffectPrecondition;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

public class ItemPropertiesBuilder
{
	private final Collection<IEffectProvider> effects = new ArrayList<>();

	private final Collection<IEffectPrecondition> preconditions = new ArrayList<>();

	private ItemEquipmentSlot slot = ItemEquipmentSlot.NONE;

	private ItemRarity rarity = ItemRarity.NONE;
	
	/**
	 * Sets the rarity of this item.
	 * @param rarity The rarity of this item
	 */
	public ItemPropertiesBuilder withRarity(@Nonnull ItemRarity rarity)
	{
		Validate.notNull(rarity, "Rarity cannot be null, use ItemRarity#NONE");

		this.rarity = rarity;

		return this;
	}

	/**
	 * Sets the slot this equipment item will use and turns this item into equipment.
	 * @param slot The equipment slot this item will use
	 */
	public ItemPropertiesBuilder withSlot(@Nonnull ItemEquipmentSlot slot)
	{
		Validate.notNull("Equipment slot cannot be null, use ItemEquipmentSlot#NONE");

		this.slot = slot;

		return this;
	}

	/**
	 * Adds an {@link IEffectProvider} this equipment item will provide.
	 * @param effect The effect provider to add
	 */
	public ItemPropertiesBuilder withEffect(IEffectProvider effect)
	{
		this.effects.add(effect);

		return this;
	}


	/**
	 * Adds an {@link IEffectPrecondition} to this item that will determine whether or
	 * not it can provide effects to an entity.
	 * @param precondition The precondition to add
	 */
	public ItemPropertiesBuilder withPrecondition(IEffectPrecondition precondition)
	{
		this.preconditions.add(precondition);

		return this;
	}

	public IItemProperties build()
	{
		return new ImplItemProperties(this.slot, this.effects, this.preconditions, this.rarity);
	}
}
