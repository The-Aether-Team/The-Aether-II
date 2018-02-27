package com.gildedgames.aether.common.capabilities.world.instances;

import com.gildedgames.aether.api.AetherCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerInstancesProvider implements ICapabilitySerializable<NBTBase>
{
	private final PlayerInstances.Storage storage = new PlayerInstances.Storage();

	private final EntityPlayer player;

	private PlayerInstances capability;

	public PlayerInstancesProvider(final EntityPlayer player)
	{
		this.player = player;
	}

	private PlayerInstances fetchCapability()
	{
		if (this.capability == null)
		{
			this.capability = new PlayerInstances(this.player);
		}

		return this.capability;
	}

	@Override
	public boolean hasCapability(final Capability<?> capability, final EnumFacing facing)
	{
		return capability == AetherCapabilities.PLAYER_INSTANCES;
	}

	@Override
	@SuppressWarnings("unchecked" /* joy... */)
	public <T> T getCapability(final Capability<T> capability, final EnumFacing facing)
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
		return this.storage.writeNBT(AetherCapabilities.PLAYER_INSTANCES, this.fetchCapability(), null);
	}

	@Override
	public void deserializeNBT(final NBTBase nbt)
	{
		this.storage.readNBT(AetherCapabilities.PLAYER_INSTANCES, this.fetchCapability(), null, nbt);
	}

}
