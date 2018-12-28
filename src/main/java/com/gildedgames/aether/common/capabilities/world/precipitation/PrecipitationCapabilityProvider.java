package com.gildedgames.aether.common.capabilities.world.precipitation;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PrecipitationCapabilityProvider implements ICapabilitySerializable<NBTBase>
{
	private final PrecipitationManagerImpl.Storage storage = new PrecipitationManagerImpl.Storage();

	private final IPrecipitationManager capability;

	public PrecipitationCapabilityProvider(IPrecipitationManager manager)
	{
		this.capability = manager;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		return this.capability != null && capability == AetherCapabilities.PRECIPITATION_MANAGER;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		if (this.hasCapability(capability, facing))
		{
			return (T) this.capability;
		}

		return null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return this.storage.writeNBT(AetherCapabilities.PRECIPITATION_MANAGER, this.capability, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		this.storage.readNBT(AetherCapabilities.PRECIPITATION_MANAGER, this.capability, null, nbt);
	}
}
