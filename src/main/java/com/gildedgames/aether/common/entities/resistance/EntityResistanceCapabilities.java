package com.gildedgames.aether.common.entities.resistance;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityResistanceCapabilities implements ICapabilitySerializable<INBT>
{
	//public static final Capability<>
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable Direction facing)
	{
		return false;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing)
	{
		return null;
	}

	@Override
	public INBT serializeNBT()
	{
		return null;
	}

	@Override
	public void deserializeNBT(INBT nbt)
	{

	}
}
