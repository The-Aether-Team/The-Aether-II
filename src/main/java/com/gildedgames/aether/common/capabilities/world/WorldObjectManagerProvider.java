package com.gildedgames.aether.common.capabilities.world;

import com.gildedgames.aether.api.AetherCapabilities;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

public class WorldObjectManagerProvider implements ICapabilitySerializable<NBTBase>
{
	private final WorldObjectManager.Storage storage = new WorldObjectManager.Storage();

	private final WorldObjectManager capability;

	public WorldObjectManagerProvider(final WorldObjectManager capability)
	{
		this.capability = capability;
	}

	@Override
	public boolean hasCapability(final Capability<?> capability, @Nullable final EnumFacing facing)
	{
		return capability == AetherCapabilities.WORLD_OBJECT_MANAGER && this.capability != null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(final Capability<T> capability, @Nullable final EnumFacing facing)
	{
		return this.hasCapability(capability, facing) ? (T) this.capability : null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return this.storage.writeNBT(AetherCapabilities.WORLD_OBJECT_MANAGER, this.capability, null);
	}

	@Override
	public void deserializeNBT(final NBTBase nbt)
	{
		this.storage.readNBT(AetherCapabilities.WORLD_OBJECT_MANAGER, this.capability, null, nbt);
	}
}

