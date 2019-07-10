package com.gildedgames.aether.common.entities.resistance;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityResistanceCapabilities implements ICapabilitySerializable<NBTBase>
{
	//public static final Capability<>
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		return false;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		return null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return null;
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{

	}
}
