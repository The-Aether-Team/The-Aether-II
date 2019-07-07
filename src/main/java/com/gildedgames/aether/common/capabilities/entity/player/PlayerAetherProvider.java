package com.gildedgames.aether.common.capabilities.entity.player;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerAetherProvider implements ICapabilitySerializable<NBTBase>
{
	private final PlayerAether.Storage storage = new PlayerAether.Storage();

	private final IPlayerAether aePlayer;

	public PlayerAetherProvider(IPlayerAether aePlayer)
	{
		this.aePlayer = aePlayer;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == CapabilitiesAether.PLAYER_DATA && this.aePlayer != null;
	}

	@Override
	@SuppressWarnings("unchecked" /* joy... */)
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (this.hasCapability(capability, facing))
		{
			return (T) this.aePlayer;
		}

		return null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return this.storage.writeNBT(CapabilitiesAether.PLAYER_DATA, this.aePlayer, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		this.storage.readNBT(CapabilitiesAether.PLAYER_DATA, this.aePlayer, null, nbt);
	}
}
