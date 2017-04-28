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
		this(ItemEquipmentSlot.NONE, Collections.emptyList(), ItemRarity.NONE);
	}

	public ImplItemProperties(@Nonnull ItemEquipmentSlot slot, @Nonnull Collection<IEffectProvider> effects, @Nonnull ItemRarity rarity)
	{
		this.slot = slot;
		this.effects = Collections.unmodifiableCollection(effects);
		this.rarity = rarity;
	}

	@Nonnull
	@Override
	public ItemEquipmentSlot getEquipmentSlot()
	{
		return this.slot;
	}

	@Override
	public Collection<IEffectProvider> getEffectProviders()
	{
		return this.effects;
	}

	@Override
	public ItemRarity getRarity()
	{
		return this.rarity;
	}
}
