package com.gildedgames.aether.api.orbis_core.api;

import com.gildedgames.aether.api.orbis_core.block.BlockData;
import com.gildedgames.aether.api.orbis_core.block.BlockDataContainer;
import com.gildedgames.aether.api.orbis_core.data.BlueprintData;
import com.gildedgames.aether.api.world.generation.IBlockAccessExtended;
import net.minecraft.util.math.BlockPos;

public interface PlacementCondition
{

	boolean canPlace(BlueprintData data, IBlockAccessExtended world, BlockPos placedAt, BlockData block, BlockPos pos);

	/** Should return true by default **/
	boolean canPlaceCheckAll(BlueprintData data, IBlockAccessExtended world, BlockPos placedAt, BlockDataContainer blocks);

}