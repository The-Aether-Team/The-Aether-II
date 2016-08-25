package com.gildedgames.aether.common.capabilities.item.properties;

import com.gildedgames.aether.api.capabilites.items.properties.ItemEquipmentType;
import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;
import com.gildedgames.aether.api.capabilites.items.properties.IItemPropertiesCapability;
import com.gildedgames.aether.api.registry.equipment.IEquipmentProperties;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class ItemPropertiesImpl implements IItemPropertiesCapability
{
	private IEquipmentProperties properties;

	public ItemPropertiesImpl(IEquipmentProperties properties)
	{
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
		return this.properties == null ? ItemRarity.BASIC : this.properties.getRarity();
	}

	@Override
	public ItemEquipmentType getEquipmentType()
	{
		return this.properties == null ? null : this.properties.getEquipmentType();
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

