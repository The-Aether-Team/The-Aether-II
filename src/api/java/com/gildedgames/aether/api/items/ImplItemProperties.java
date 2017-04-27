package com.gildedgames.aether.api.items;

import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Stateless, immutable container for item information.
 */
public class ImplItemProperties implements IItemProperties
{
	private final ItemEquipmentSlot slot;

	private final Collection<IEffectProvider> effects;

	private final ItemRarity rarity;

	public ImplItemProperties()
	{
		this(null, null, null);
	}

	public ImplItemProperties(ItemEquipmentSlot slot, Collection<IEffectProvider> effects, ItemRarity rarity)
	{
		this.slot = slot;
		this.effects = effects == null ? Collections.emptyList() : Collections.unmodifiableCollection(effects);
		this.rarity = rarity;
	}

	@Nonnull
	@Override
	public Optional<ItemEquipmentSlot> getEquipmentSlot()
	{
		return Optional.ofNullable(this.slot);
	}

	@Override
	public Collection<IEffectProvider> getEffectProviders()
	{
		return this.effects;
	}

	@Override
	public Optional<ItemRarity> getRarity()
	{
		return Optional.ofNullable(this.rarity);
	}
}
