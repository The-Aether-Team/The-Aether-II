package com.gildedgames.aether.common.items.effects;

import com.gildedgames.aether.entities.effects.EntityEffectInstance;
import com.gildedgames.aether.entities.effects.EntityEffectProcessor;
import com.gildedgames.aether.items.IItemEffectsCapability;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class ItemEffects implements IItemEffectsCapability
{
	public interface ItemEffectsProvider
	{
		List<Pair<EntityEffectProcessor, EntityEffectInstance>> provide();
	}

	public static class RegistrationEntry
	{
		private Item item;

		private ItemEffectsProvider provider;

		public RegistrationEntry(Item item, ItemEffectsProvider provider)
		{
			this.item = item;
			this.provider = provider;
		}

		public Item getItem()
		{
			return this.item;
		}

		public ItemEffectsProvider getEffectsProvider()
		{
			return this.provider;
		}
	}

	private static List<RegistrationEntry> registeredEntries = Lists.newArrayList();

	private List<Pair<EntityEffectProcessor, EntityEffectInstance>> effectPairs = Lists.newArrayList();

	public ItemEffects(List<Pair<EntityEffectProcessor, EntityEffectInstance>> effectPairs)
	{
		this.effectPairs = effectPairs;
	}

	@Override
	public List<Pair<EntityEffectProcessor, EntityEffectInstance>> getEffectPairs()
	{
		return this.effectPairs;
	}

	public static List<RegistrationEntry> getRegistrationEntries()
	{
		return ItemEffects.registeredEntries;
	}

	public static void register(Item item, ItemEffectsProvider effectsProvider)
	{
		ItemEffects.registeredEntries.add(new RegistrationEntry(item, effectsProvider));
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
