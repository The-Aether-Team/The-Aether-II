package com.gildedgames.aether.common.world.necromancer_tower;

import com.gildedgames.orbis_api.util.TeleporterGeneric;
import com.gildedgames.orbis_api.world.instances.IInstanceFactory;
import com.gildedgames.orbis_api.world.instances.IInstanceHandler;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class NecromancerTowerInstanceFactory implements IInstanceFactory<NecromancerTowerInstance>
{

	private final DimensionType dimensionType;

	public NecromancerTowerInstanceFactory(final DimensionType dimensionType)
	{
		this.dimensionType = dimensionType;
	}

	@Override
	public NecromancerTowerInstance createInstance(final int dimId, final IInstanceHandler instanceHandler)
	{
		return new NecromancerTowerInstance(dimId);
	}

	@Override
	public DimensionType dimensionType()
	{
		return this.dimensionType;
	}

	@Override
	public Teleporter getTeleporter(final WorldServer worldIn)
	{
		return new TeleporterGeneric(worldIn);
	}

}
