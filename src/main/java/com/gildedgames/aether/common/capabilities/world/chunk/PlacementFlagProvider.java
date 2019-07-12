package com.gildedgames.aether.common.capabilities.world.chunk;

import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

public class PlacementFlagProvider implements ICapabilitySerializable<INBT>
{
	private final IPlacementFlagCapability capability;

	public PlacementFlagProvider(IPlacementFlagCapability capability)
	{
		this.capability = capability;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable Direction facing)
	{
		return capability == CapabilitiesAether.CHUNK_PLACEMENT_FLAG && this.capability != null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, @Nullable Direction facing)
	{
		return this.hasCapability(capability, facing) ? (T) this.capability : null;
	}

	@Override
	public INBT serializeNBT()
	{
		if (this.capability == null)
		{
			return null;
		}

		CompoundNBT tag = new CompoundNBT();

		this.capability.write(tag);

		return tag;
	}

	@Override
	public void deserializeNBT(INBT nbt)
	{
		if (this.capability == null)
		{
			return;
		}

		this.capability.read((CompoundNBT) nbt);
	}
}
