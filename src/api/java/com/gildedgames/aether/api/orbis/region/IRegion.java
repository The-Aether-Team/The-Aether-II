package com.gildedgames.aether.api.orbis.region;

import com.gildedgames.aether.api.orbis.shapes.IShape;
import net.minecraft.util.math.BlockPos;

public interface IRegion extends IDimensions, IShape
{

	BlockPos getMin();

	BlockPos getMax();

}
