package com.gildedgames.aether.common.capabilities.world.chunk;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.chunk.IChunkRendererCapability;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

public class ChunkRendererProvider implements ICapabilitySerializable<NBTBase>
{
	private final IChunkRendererCapability capability;

	public ChunkRendererProvider(final IChunkRendererCapability capability)
	{
		this.capability = capability;
	}

	@Override
	public boolean hasCapability(final Capability<?> capability, @Nullable final EnumFacing facing)
	{
		return capability == AetherCapabilities.CHUNK_RENDERER && this.capability != null;
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
		if (this.capability == null)
		{
			return null;
		}

		final NBTTagCompound tag = new NBTTagCompound();

		this.capability.write(tag);

		return tag;
	}

	@Override
	public void deserializeNBT(final NBTBase nbt)
	{
		if (this.capability == null)
		{
			return;
		}

		this.capability.read((NBTTagCompound) nbt);
	}
}
