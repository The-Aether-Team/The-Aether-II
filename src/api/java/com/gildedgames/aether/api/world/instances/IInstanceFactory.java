package com.gildedgames.aether.api.world.instances;

import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public interface IInstanceFactory<T extends IInstance>
{
	T createInstance(int dimId, IInstanceHandler instanceHandler);

	DimensionType dimensionType();

	Teleporter getTeleporter(WorldServer worldIn);
}
