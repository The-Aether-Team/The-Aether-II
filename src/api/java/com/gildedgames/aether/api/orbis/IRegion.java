package com.gildedgames.aether.api.orbis;

import net.minecraft.util.math.BlockPos;

public interface IRegion extends IDimensions, IShape
{

	BlockPos getMin();

	BlockPos getMax();

}
