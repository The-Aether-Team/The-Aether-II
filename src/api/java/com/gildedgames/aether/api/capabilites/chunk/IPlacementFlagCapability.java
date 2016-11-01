package com.gildedgames.aether.api.capabilites.chunk;

import com.gildedgames.util.io_manager.io.NBT;
import net.minecraft.util.math.BlockPos;

public interface IPlacementFlagCapability extends NBT
{
	void mark(BlockPos pos);

	void clear(BlockPos pos);

	void set(BlockPos pos, boolean value);

	boolean isMarked(BlockPos pos);
}
