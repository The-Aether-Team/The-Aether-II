package com.gildedgames.aether.api.orbis.shapes;

import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.util.NBT;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

public interface IShape extends NBT
{

	/**
	 * This should be the base method for creating the shape's data with its internal input.
	 * This method should not affect the state of the shape itself, but rather acts as a pure factory method.
	 * @return
	 */
	Iterable<BlockPos.MutableBlockPos> createShapeData();

	/**
	 * This data should only be created once, and recreated or refreshed when the shape input has changed.
	 * @return
	 */
	Iterable<BlockPos.MutableBlockPos> getShapeData();

	IShape rotate(Rotation rotation, IRegion in);

	IShape translate(int x, int y, int z);

	IShape translate(BlockPos pos);

	IRegion getBoundingBox();

	boolean contains(int x, int y, int z);

	boolean contains(BlockPos pos);

}
