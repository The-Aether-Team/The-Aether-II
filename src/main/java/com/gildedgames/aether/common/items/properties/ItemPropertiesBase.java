package com.gildedgames.aether.common.items.properties;

import com.gildedgames.aether.common.items.ItemEquipmentType;
import com.gildedgames.aether.common.items.ItemRarity;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import java.util.List;

public interface ItemPropertiesBase
{


	class Storage implements IStorage<ItemPropertiesBase>
	{

		@Override
		public NBTBase writeNBT(Capability<ItemPropertiesBase> capability, ItemPropertiesBase instance, EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(Capability<ItemPropertiesBase> capability, ItemPropertiesBase instance, EnumFacing side, NBTBase nbt)
		{

		}

	}

	void setEquipmentType(ItemEquipmentType type);

	ItemEquipmentType getEquipmentType();

	ItemRarity getRarity();

	void setRarity(ItemRarity rarity);

	boolean isEquippable();

}
