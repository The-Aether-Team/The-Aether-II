package com.gildedgames.aether.api.orbis.region;

import net.minecraft.util.math.BlockPos;

public interface IMutableRegion extends IRegion
{
	/**
	 * Set the bounds of the region.
	 */
	void setBounds(IRegion region);

	/**
	 * Set the bounds of the region.
	 */
	void setBounds(BlockPos corner1, BlockPos corner2);
}
