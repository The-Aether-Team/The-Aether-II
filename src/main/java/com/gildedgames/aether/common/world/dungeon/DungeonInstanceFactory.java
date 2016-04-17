package com.gildedgames.aether.common.world.dungeon;

import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

import com.gildedgames.util.modules.instances.InstanceFactory;
import com.gildedgames.util.modules.instances.InstanceHandler;
import com.gildedgames.util.modules.universe.common.util.TeleporterGeneric;

public class DungeonInstanceFactory implements InstanceFactory<DungeonInstance>
{
	
	private int providerId;
	
	private Class<? extends WorldProvider> provider;
	
	public DungeonInstanceFactory(int providerId, Class<? extends WorldProvider> provider)
	{
		this.providerId = providerId;
		this.provider = provider;
	}

	@Override
	public DungeonInstance createInstance(int dimId, InstanceHandler<DungeonInstance> instanceHandler)
	{
		return new DungeonInstance(dimId, instanceHandler);
	}

	@Override
	public int providerId()
	{
		return this.providerId;
	}

	@Override
	public Class<? extends WorldProvider> getProviderType()
	{
		return this.provider;
	}

	@Override
	public Teleporter getTeleporter(WorldServer worldIn)
	{
		return new TeleporterGeneric(worldIn);
	}

}