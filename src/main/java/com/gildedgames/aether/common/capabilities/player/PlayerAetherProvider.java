package com.gildedgames.aether.common.capabilities.player;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.IPlayerAetherCapability;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerAetherProvider implements ICapabilitySerializable<NBTBase>
{
	private final PlayerAetherImpl.Storage storage = new PlayerAetherImpl.Storage();

	private final IPlayerAetherCapability aePlayer;

	public PlayerAetherProvider(IPlayerAetherCapability aePlayer)
	{
		this.aePlayer = aePlayer;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == AetherCapabilities.PLAYER_DATA && this.aePlayer != null;
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
		return this.storage.writeNBT(AetherCapabilities.PLAYER_DATA, this.aePlayer, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		this.storage.readNBT(AetherCapabilities.PLAYER_DATA, this.aePlayer, null, nbt);
	}
}
