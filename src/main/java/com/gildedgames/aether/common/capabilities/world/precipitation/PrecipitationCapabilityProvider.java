package com.gildedgames.aether.common.capabilities.world.precipitation;

import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PrecipitationCapabilityProvider implements ICapabilitySerializable<INBT>
{
	private final PrecipitationManagerImpl.Storage storage = new PrecipitationManagerImpl.Storage();

	private final IPrecipitationManager capability;

	public PrecipitationCapabilityProvider(IPrecipitationManager manager)
	{
		this.capability = manager;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable Direction facing)
	{
		return this.capability != null && capability == CapabilitiesAether.PRECIPITATION_MANAGER;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing)
	{
		if (this.hasCapability(capability, facing))
		{
			return (T) this.capability;
		}

		return null;
	}

	@Override
	public INBT serializeNBT()
	{
		return this.storage.writeNBT(CapabilitiesAether.PRECIPITATION_MANAGER, this.capability, null);
	}

	@Override
	public void deserializeNBT(INBT nbt)
	{
		this.storage.readNBT(CapabilitiesAether.PRECIPITATION_MANAGER, this.capability, null, nbt);
	}
}
