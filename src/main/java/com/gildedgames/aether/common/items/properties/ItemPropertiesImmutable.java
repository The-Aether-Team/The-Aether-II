package com.gildedgames.aether.common.items.properties;

import com.gildedgames.aether.api.items.EffectActivator;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.IEffectPrecondition;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.items.properties.IItemProperties;
import com.gildedgames.aether.api.items.properties.ItemRarity;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

/**
 * Stateless, immutable container for item information.
 */
public class ItemPropertiesImmutable implements IItemProperties
{
	private final ItemEquipmentSlot slot;

	private final Collection<IEffectProvider> effects;

	private final Collection<IEffectPrecondition> preconditions;

	private final Collection<EffectActivator> effectActivators;

	private final ItemRarity rarity;

	public ItemPropertiesImmutable()
	{
		this(ItemEquipmentSlot.NONE, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ItemRarity.NONE);
	}

	public ItemPropertiesImmutable(@Nonnull ItemEquipmentSlot slot,
			@Nonnull Collection<IEffectProvider> effects,
			@Nonnull Collection<IEffectPrecondition> preconditions,
			@Nonnull Collection<EffectActivator> effectActivators,
			@Nonnull ItemRarity rarity)
	{
		this.slot = slot;
		this.effects = effects;
		this.preconditions = preconditions;
		this.effectActivators = effectActivators;
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

	@Nonnull
	@Override
	public Collection<EffectActivator> getEffectActivators()
	{
		return this.effectActivators;
	}

	@Override
	public ItemRarity getRarity()
	{
		return this.rarity;
	}

	@Override
	public Collection<IEffectPrecondition> getEffectPreconditions()
	{
		return this.preconditions;
	}
}
