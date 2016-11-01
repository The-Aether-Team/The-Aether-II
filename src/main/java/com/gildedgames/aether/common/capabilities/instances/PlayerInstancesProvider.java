package com.gildedgames.aether.common.capabilities.instances;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerInstancesProvider implements ICapabilitySerializable<NBTBase>
{
	private final PlayerInstances.Storage storage = new PlayerInstances.Storage();

	private PlayerInstances capability;

	private EntityPlayer player;

	public PlayerInstancesProvider(EntityPlayer player)
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
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == AetherCapabilities.PLAYER_INSTANCES;
	}

	@Override
	@SuppressWarnings("unchecked" /* joy... */)
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
		return this.storage.writeNBT(AetherCapabilities.PLAYER_INSTANCES, this.fetchCapability(), null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		this.storage.readNBT(AetherCapabilities.PLAYER_INSTANCES, this.fetchCapability(), null, nbt);
	}

}
