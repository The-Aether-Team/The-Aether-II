package com.gildedgames.aether.common.items.properties;

import com.gildedgames.aether.items.properties.ItemEquipmentType;
import com.gildedgames.aether.items.properties.ItemRarity;
import com.gildedgames.aether.items.IItemPropertiesCapability;
import com.gildedgames.aether.registry.equipment.IEquipmentProperties;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

public class ItemProperties implements IItemPropertiesCapability
{
	private final ItemStack stack;

	private IEquipmentProperties properties;

	public ItemProperties(ItemStack stack, IEquipmentProperties properties)
	{
		this.stack = stack;
		this.properties = properties;
	}

	@Override
	public IEquipmentProperties getProperties()
	{
		return this.properties;
	}

	@Override
	public ItemRarity getRarity()
	{
		return this.properties == null ? ItemRarity.NONE : this.properties.getRarity();
	}

	@Override
	public ItemEquipmentType getEquipmentType()
	{
		return this.properties == null ? null : this.properties.getEquipmentType();
	}

	@Override
	public ItemStack getStack()
	{
		return this.stack;
	}

	@Override
	public boolean isEquippable()
	{
		return this.getEquipmentType() != null;
	}

	public static class Storage implements Capability.IStorage<IItemPropertiesCapability>
	{
		@Override
		public NBTBase writeNBT(Capability<IItemPropertiesCapability> capability, IItemPropertiesCapability instance, EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(Capability<IItemPropertiesCapability> capability, IItemPropertiesCapability instance, EnumFacing side, NBTBase nbt)
		{

		}
	}
}

