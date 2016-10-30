package com.gildedgames.aether.common.capabilities.item;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.items.IItemBreakable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ItemBreakableProvider implements ICapabilitySerializable<NBTBase>
{

	private final ItemBreakable.Storage storage = new ItemBreakable.Storage();

	private IItemBreakable capability;

	public ItemBreakableProvider()
	{

	}

	private IItemBreakable fetchCapability()
	{
		if (this.capability == null)
		{
			this.capability = new ItemBreakable();
		}

		return this.capability;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == AetherCapabilities.ITEM_BREAKABLE;
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
		return this.storage.writeNBT(AetherCapabilities.ITEM_BREAKABLE, this.fetchCapability(), null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		this.storage.readNBT(AetherCapabilities.ITEM_BREAKABLE, this.fetchCapability(), null, nbt);
	}

}
