package com.gildedgames.aether.api.capabilites.instances;

import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public interface IInstanceFactory<T extends Instance>
{
	T createInstance(int dimId, IInstanceHandler instanceHandler);

	DimensionType dimensionType();

	Teleporter getTeleporter(WorldServer worldIn);
}
