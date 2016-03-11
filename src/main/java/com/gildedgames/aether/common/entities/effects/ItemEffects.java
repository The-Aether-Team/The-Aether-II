package com.gildedgames.aether.common.entities.effects;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import org.apache.commons.lang3.tuple.Pair;

import com.gildedgames.aether.common.items.ItemAccessory;
import com.gildedgames.util.core.UtilModule;
import com.google.common.collect.Lists;


public interface ItemEffects
{
	
	public static class DefaultImpl implements ItemEffects
	{
		
		private List<Pair<EffectProcessor, EffectInstance>> effectPairs = Lists.newArrayList();
		
		private ItemStack stack;
		
		public DefaultImpl(ItemStack stack)
		{
			this.stack = stack;
			
			if (stack.getItem() instanceof ItemAccessory)
			{
				ItemAccessory acc = (ItemAccessory)stack.getItem();
				
				for (Pair<EffectProcessor, EffectInstance> pair : acc.getEffects())
				{
					try
					{
						EffectProcessor processor = pair.getLeft();
						EffectInstance instance = pair.getRight().cloneInstance();
						
						this.effectPairs.add(Pair.of(processor, instance));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		
		public <I extends EffectInstance> void addEffect(EffectProcessor<I> processor, I instance)
		{
			Pair<EffectProcessor, EffectInstance> effectPair = Pair.of((EffectProcessor)processor, (EffectInstance)instance);
			
			this.effectPairs.add(effectPair);
		}

		@Override
		public List<Pair<EffectProcessor, EffectInstance>> getEffectPairs()
		{
			return this.effectPairs;
		}
		
	}
	
	public static class Storage implements IStorage<ItemEffects>
	{

		@Override
		public NBTBase writeNBT(Capability<ItemEffects> capability, ItemEffects instance, EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(Capability<ItemEffects> capability, ItemEffects instance, EnumFacing side, NBTBase nbt)
		{
			
		}

	}

	List<Pair<EffectProcessor, EffectInstance>> getEffectPairs();
	
}
