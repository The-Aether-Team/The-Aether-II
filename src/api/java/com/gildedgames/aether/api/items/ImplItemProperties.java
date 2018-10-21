package com.gildedgames.aether.api.items;

import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.IEffectPrecondition;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

/**
 * Stateless, immutable container for item information.
 */
public class ImplItemProperties implements IItemProperties
{
	private final ItemEquipmentSlot slot;

	private final Collection<IEffectProvider> effects;

	private final Collection<IEffectPrecondition> preconditions;

	private final Collection<EffectActivator> effectActivators;

	private final ItemRarity rarity;

	public ImplItemProperties()
	{
		this(ItemEquipmentSlot.NONE, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), ItemRarity.NONE);
	}

	public ImplItemProperties(@Nonnull ItemEquipmentSlot slot,
			@Nonnull Collection<IEffectProvider> effects,
			@Nonnull Collection<IEffectPrecondition> preconditions,
			@Nonnull Collection<EffectActivator> effectActivators,
			@Nonnull ItemRarity rarity)
	{
		this.slot = slot;
		this.effects = effects.size() > 0 ? Collections.unmodifiableCollection(effects) : Collections.emptyList();
		this.preconditions = preconditions.size() > 0 ? Collections.unmodifiableCollection(preconditions) : Collections.emptyList();
		this.effectActivators = effectActivators.size() > 0 ? Collections.unmodifiableCollection(effectActivators) : Collections.emptyList();
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
