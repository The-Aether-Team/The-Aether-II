package com.gildedgames.aether.common.capabilities.item.effects;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.capabilites.items.effects.IItemEffectsCapability;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemEffects implements IItemEffectsCapability
{
	public interface ItemEffectsProvider
	{
		List<Pair<EntityEffectProcessor, EntityEffectInstance>> provide();
	}

	private static Map<Item, ItemEffectsProvider> registeredEntries = new HashMap<>();

	private final List<Pair<EntityEffectProcessor, EntityEffectInstance>> effectPairs = Lists.newArrayList();

	public ItemEffects(List<Pair<EntityEffectProcessor, EntityEffectInstance>> effectPairs)
	{
		if (effectPairs != null)
		{
			this.effectPairs.addAll(effectPairs);
		}
	}

	@Override
	public List<Pair<EntityEffectProcessor, EntityEffectInstance>> getEffectPairs()
	{
		return this.effectPairs;
	}

	public static ItemEffectsProvider getProvider(Item item)
	{
		return ItemEffects.registeredEntries.get(item);
	}

	public static void register(Item item, ItemEffectsProvider effectsProvider)
	{
		ItemEffects.registeredEntries.put(item, effectsProvider);
	}

	/**
	 * Registers an item as "Cosmetic"
	 * @param item
	 */
	public static void register(Item item)
	{
		ItemEffects.register(item, null);
	}

	public static class Storage implements Capability.IStorage<IItemEffectsCapability>
	{
		@Override
		public NBTBase writeNBT(Capability<IItemEffectsCapability> capability, IItemEffectsCapability instance, EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(Capability<IItemEffectsCapability> capability, IItemEffectsCapability instance, EnumFacing side, NBTBase nbt) { }
	}
}
