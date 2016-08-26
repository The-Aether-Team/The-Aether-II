package com.gildedgames.aether.common.capabilities.item.extra_data;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.items.extra_data.IItemExtraDataCapability;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ItemExtraDataProvider implements ICapabilitySerializable<NBTBase>
{

	private final ItemExtraDataImpl.Storage storage = new ItemExtraDataImpl.Storage();

	private IItemExtraDataCapability capability;

	public ItemExtraDataProvider()
	{

	}

	public ItemExtraDataProvider(IItemExtraDataCapability capability)
	{
		this.capability = capability;
	}

	private IItemExtraDataCapability fetchCapability()
	{
		if (this.capability == null)
		{
			this.capability = new ItemExtraDataImpl();
		}

		return this.capability;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == AetherCapabilities.ITEM_EXTRA_DATA;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (this.hasCapability(capability, facing))
		{
			return (T) this.fetchCapability();
		}

		return null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return this.storage.writeNBT(AetherCapabilities.ITEM_EXTRA_DATA, this.fetchCapability(), null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		this.storage.readNBT(AetherCapabilities.ITEM_EXTRA_DATA, this.fetchCapability(), null, nbt);
	}

}
