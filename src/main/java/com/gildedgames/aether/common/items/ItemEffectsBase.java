package com.gildedgames.aether.common.items;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import org.apache.commons.lang3.tuple.Pair;

import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessor;
import com.google.common.collect.Lists;


public interface ItemEffectsBase
{
	
	public static class ItemEffects implements ItemEffectsBase
	{
		
		public static interface ItemEffectsProvider
		{
			
			List<Pair<EffectProcessor, EffectInstance>> provide();
			
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
		
		private List<Pair<EffectProcessor, EffectInstance>> effectPairs = Lists.newArrayList();
		
		public ItemEffects(List<Pair<EffectProcessor, EffectInstance>> effectPairs)
		{
			this.effectPairs = effectPairs;
		}

		@Override
		public List<Pair<EffectProcessor, EffectInstance>> getEffectPairs()
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
		
	}
	
	public static class Storage implements IStorage<ItemEffectsBase>
	{

		@Override
		public NBTBase writeNBT(Capability<ItemEffectsBase> capability, ItemEffectsBase instance, EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(Capability<ItemEffectsBase> capability, ItemEffectsBase instance, EnumFacing side, NBTBase nbt)
		{
			
		}

	}

	List<Pair<EffectProcessor, EffectInstance>> getEffectPairs();
	
}
