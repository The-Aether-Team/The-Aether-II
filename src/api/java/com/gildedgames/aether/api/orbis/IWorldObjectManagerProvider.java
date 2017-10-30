package com.gildedgames.aether.api.orbis;

import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public interface IWorldObjectManagerProvider
{

	IChunkRendererProvider getRendererForDimension(int dimension);

	IChunkRendererProvider getRendererForWorld(World world);

	IChunkRendererProvider getRendererForDimension(DimensionType type);

	IWorldObjectManager getForWorld(World world);

	IWorldObjectManager getForDimension(int dimension);

	IWorldObjectManager getForDimension(DimensionType type);

	void write();

	void read();

}
