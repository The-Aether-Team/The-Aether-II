package com.gildedgames.aether.common.capabilities.instances;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.instances.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InstanceRegistryImpl implements IInstanceRegistry
{

	private List<IInstanceHandler> instances;

	public InstanceRegistryImpl()
	{

	}

	@Override
	public List<IInstanceHandler> getInstanceHandlers()
	{
		if (this.instances == null)
		{
			this.instances = new ArrayList<>();
		}

		return this.instances;
	}

	@Override
	public void registerInstanceHandler(IInstanceHandler handler)
	{
		this.getInstanceHandlers().add(handler);
	}

	@Override
	public <T extends Instance> InstanceHandler<T> createAndRegisterInstanceHandler(IInstanceFactory<T> factory)
	{
		InstanceHandler<T> handler = new InstanceHandler<>(factory);
		this.registerInstanceHandler(handler);

		return handler;
	}

	@Override
	public IPlayerInstances getPlayer(EntityPlayer player)
	{
		return player.getCapability(AetherCapabilities.PLAYER_INSTANCES, null);
	}

	@Override
	public IPlayerInstances getPlayer(UUID uuid)
	{
		EntityPlayer player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(uuid);

		if (player == null)
		{
			return null;
		}

		return this.getPlayer(player);
	}

}
