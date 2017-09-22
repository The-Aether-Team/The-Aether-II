package com.gildedgames.aether.api.world.islands;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import java.util.Random;

public interface IVirtualChunk
{
	int getX();

	int getZ();

	int getVolume();

	IBlockState getState(int x, int y, int z);

	void setState(int x, int y, int z, IBlockState state);

	int getHeightValue(int x, int z);

	void prepareThisAndNearbyChunks(final World world, final IIslandData island, final Random rand);

	void prepare(final World world, final IIslandData island, final Random rand);
}
