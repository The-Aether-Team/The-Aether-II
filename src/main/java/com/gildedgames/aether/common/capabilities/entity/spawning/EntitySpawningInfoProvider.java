package com.gildedgames.aether.common.capabilities.entity.spawning;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class EntitySpawningInfoProvider implements ICapabilitySerializable<NBTBase>
{

	private final EntitySpawningInfo.Storage storage = new EntitySpawningInfo.Storage();

	private ISpawningInfo capability;

	public EntitySpawningInfoProvider()
	{

	}

	private ISpawningInfo fetchCapability()
	{
		if (this.capability == null)
		{
			this.capability = new EntitySpawningInfo();
		}

		return this.capability;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == AetherCapabilities.ENTITY_SPAWNING_INFO;
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
		return this.storage.writeNBT(AetherCapabilities.ENTITY_SPAWNING_INFO, this.fetchCapability(), null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		this.storage.readNBT(AetherCapabilities.ENTITY_SPAWNING_INFO, this.fetchCapability(), null, nbt);
	}

}
