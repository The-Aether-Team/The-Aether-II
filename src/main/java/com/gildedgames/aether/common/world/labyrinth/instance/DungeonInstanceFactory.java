package com.gildedgames.aether.common.world.labyrinth.instance;

import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import com.gildedgames.util.modules.instances.InstanceFactory;
import com.gildedgames.util.modules.instances.InstanceHandler;
import com.gildedgames.util.core.util.TeleporterGeneric;

public class DungeonInstanceFactory implements InstanceFactory<DungeonInstance>
{
	
	private DimensionType dimensionType;

	public DungeonInstanceFactory(DimensionType dimensionType)
	{
		this.dimensionType = dimensionType;
	}

	@Override
	public DungeonInstance createInstance(int dimId, InstanceHandler<DungeonInstance> instanceHandler)
	{
		return new DungeonInstance(dimId, instanceHandler);
	}

	@Override
	public DimensionType dimensionType()
	{
		return this.dimensionType;
	}

	@Override
	public Teleporter getTeleporter(WorldServer worldIn)
	{
		return new TeleporterGeneric(worldIn);
	}

}
