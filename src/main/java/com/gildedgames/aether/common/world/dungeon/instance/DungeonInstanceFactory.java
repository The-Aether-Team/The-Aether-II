package com.gildedgames.aether.common.world.dungeon.instance;

import com.gildedgames.util.core.util.TeleporterGeneric;
import com.gildedgames.util.modules.instances.InstanceFactory;
import com.gildedgames.util.modules.instances.InstanceHandler;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

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
