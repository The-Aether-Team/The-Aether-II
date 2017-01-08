package com.gildedgames.aether.common.world.dungeon.instance;

import com.gildedgames.aether.api.capabilites.instances.IInstanceFactory;
import com.gildedgames.aether.api.capabilites.instances.IInstanceHandler;
import com.gildedgames.aether.common.world.util.TeleporterGeneric;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class DungeonInstanceFactory implements IInstanceFactory<DungeonInstance>
{

	private DimensionType dimensionType;

	public DungeonInstanceFactory(DimensionType dimensionType)
	{
		this.dimensionType = dimensionType;
	}

	@Override
	public DungeonInstance createInstance(int dimId, IInstanceHandler instanceHandler)
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
